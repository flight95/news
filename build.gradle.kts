// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.ksp) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
}

subprojects {
    val ktlintClasspath by configurations.creating
    dependencies {
        ktlintClasspath("com.pinterest.ktlint:ktlint-cli:1.3.0") {
            attributes {
                attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
            }
        }
    }

    val outputDir = "${project.layout.buildDirectory.get().asFile}/reports/ktlint/"
    val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

    val ktlint by tasks.creating(JavaExec::class) {
        inputs.files(inputFiles)
        outputs.dir(outputDir)

        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Check Kotlin code style"
        classpath = ktlintClasspath
        mainClass.set("com.pinterest.ktlint.Main")
        args = listOf(
            "**/src/**/*.kt",
            "**.kts",
            "!**/build/**"
        )
        jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
    }

    val ktlintFormat by tasks.creating(JavaExec::class) {
        inputs.files(inputFiles)
        outputs.dir(outputDir)

        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Check Kotlin code style and format"
        classpath = ktlintClasspath
        mainClass.set("com.pinterest.ktlint.Main")
        args = listOf(
            "-F",
            "**/src/**/*.kt",
            "**.kts",
            "!**/build/**"
        )
        jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
    }
}
