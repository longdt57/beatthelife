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
}
