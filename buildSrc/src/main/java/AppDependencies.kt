object Versions {

    const val gradle = "4.1.1"

    const val kotlin = "1.4.20"
    const val pinterest = "0.37.2"

    const val coreKtx = "1.3.2"
    const val appCompat = "1.2.0"

    const val material = "1.2.1"
    const val constraintLayout = "2.0.4"
    const val navigation = "2.3.2"
    const val lifeCycle = "2.2.0"
    const val junit = "4.13.1"
    const val extJunit = "1.1.2"
    const val espresso = "3.3.0"
    const val hilt = "2.31.1-alpha"
    const val hiltViewModel = "1.0.0-alpha02"

    const val work_version = "2.4.0"
}

object AppDependencies {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinCore = "androidx.core:core-ktx:1.3.2"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Hilt
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}"
    const val hiltViewModelCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltViewModel}"
    const val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltViewModel}"

    // Worker
    const val workRuntime = "androidx.work:work-runtime-ktx:${Versions.work_version}"
    const val workRxJava = "androidx.work:work-rxjava2:${Versions.work_version}"

    // App Start up
    const val appStartUp = "androidx.startup:startup-runtime:1.0.0"

    // AppCompat
    const val appCompat = "androidx.appcompat:appcompat:1.2.0"

    // Material Design
    const val material = "com.google.android.material:material:1.2.1"

    // Firebase
    const val firebaseFireStore = "com.google.firebase:firebase-firestore-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth"
    const val firebasePerf = "com.google.firebase:firebase-perf"
}

object Network {

    private const val retrofitVersion = "2.9.0"
    private const val okHttpVersion = "3.14.9"

    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val retrofit_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"

    const val okhttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
}
