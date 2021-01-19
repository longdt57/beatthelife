import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.leeImplementation(dependencyNotation: Any): Dependency? {
    return add("implementation", dependencyNotation)
}

fun DependencyHandler.addKotlin() {
    leeImplementation(AppDependencies.kotlin)
}

fun DependencyHandler.addConstraintLayout() {
    leeImplementation(AppDependencies.constraintLayout)
}

fun DependencyHandler.addHilt() {
    leeImplementation(AppDependencies.hilt)
    add("kapt", AppDependencies.hiltCompiler)

    leeImplementation(AppDependencies.hiltViewModel)
    // When using Kotlin.
    add("kapt", AppDependencies.hiltViewModelCompiler)

    leeImplementation(AppDependencies.hiltWork)
    add("annotationProcessor", AppDependencies.hiltViewModelCompiler)
}

fun DependencyHandler.addWorker() {
    leeImplementation(AppDependencies.workRuntime)
    leeImplementation(AppDependencies.workRxJava)
}

fun DependencyHandler.addAppStartUp() {
    leeImplementation(AppDependencies.appStartUp)
}

fun DependencyHandler.addFireBase() {
    leeImplementation(platform("com.google.firebase:firebase-bom:26.2.0"))
    leeImplementation("com.google.firebase:firebase-analytics-ktx")
    leeImplementation("com.google.firebase:firebase-crashlytics-ktx")
}
