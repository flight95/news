package pe.richard.news.view.core.view

import android.os.Looper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun View.clicks(
    owner: LifecycleOwner? = null,
    callback: (View) -> Unit
) {
    when (owner) {
        null -> context as? LifecycleOwner
        else -> owner
    }?.let { lifecycle ->
        callbackFlow {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                setOnClickListener {
                    if (isActive) {
                        runCatching { trySend(Unit).isSuccess }
                            .getOrDefault(false)
                    }
                }
                awaitClose { setOnClickListener(null) }
            }
        }.throttleFirst()
            .onEach { callback(this) }
            .launchIn(lifecycle.lifecycleScope)
    }
}

private fun <T> Flow<T>.throttleFirst(windowDuration: Long = 1000): Flow<T> {
    var job: Job = Job().apply { complete() }
    return onCompletion { job.cancel() }.run {
        flow {
            coroutineScope {
                collect { value ->
                    if (!job.isActive) {
                        emit(value)
                        job = launch { delay(windowDuration) }
                    }
                }
            }
        }
    }
}
