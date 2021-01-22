import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.leeImplementation(dependencyNotation: Any): Dependency? {
    return add("implementation", dependencyNotation)
}

fun DependencyHandler.addKotlin() {
    leeImplementation(AppDependencies.kotlin)
    leeImplementation(AppDependencies.kotlinCore)
}

fun DependencyHandler.addAppCompat() {
    leeImplementation(AppDependencies.appCompat)
}

fun DependencyHandler.addMaterialDesign() {
    leeImplementation(AppDependencies.material)
}

fun DependencyHandler.addLifeCycle() {
    leeImplementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    leeImplementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
}

fun DependencyHandler.addNavigation() {
    leeImplementation("androidx.navigation:navigation-fragment-ktx:2.3.2")
    leeImplementation("androidx.navigation:navigation-ui-ktx:2.3.2")
}

fun DependencyHandler.addTimber() {
    leeImplementation("com.jakewharton.timber:timber:4.7.1")
}

fun DependencyHandler.addReactiveX() {
    leeImplementation("io.reactivex.rxjava2:rxjava:2.2.19")
    leeImplementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    leeImplementation("io.reactivex.rxjava2:rxkotlin:2.1.0")
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

fun DependencyHandler.addAuthenticationUI() {
    leeImplementation("com.firebaseui:firebase-ui-auth:6.4.0")
}

fun DependencyHandler.addFireBase() {
    leeImplementation(platform("com.google.firebase:firebase-bom:26.2.0"))
}

fun DependencyHandler.addsRetrofit(configurationName: String = "api") {
    add(configurationName, Network.retrofit)
    add(configurationName, Network.retrofit_gson)
    add(configurationName, Network.retrofit_adapter_rxjava2)
}
