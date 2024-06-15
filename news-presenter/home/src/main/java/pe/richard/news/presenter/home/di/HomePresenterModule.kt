package pe.richard.news.presenter.home.di

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelLazy
import pe.richard.news.presenter.home.HomePresenter
import pe.richard.news.presenter.home.HomePresenterImplement

fun ComponentActivity.homePresenter(): Lazy<HomePresenter> =
    ViewModelLazy(
        HomePresenterImplement::class,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )
