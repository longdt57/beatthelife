plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("lee.group.beat")
}

plugin_config {
    ktlint = true
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    addKotlin()
    addAppCompat()
    addMaterialDesign()
    addNavigation()
    addConstraintLayout()

    addHilt()
    addsRetrofit()
    addLifeCycle()
    implementation(AppDependencies.glide)
    addTimber()

    addFireBase()
    implementation(AppDependencies.firebaseFireStore)
    implementation(AppDependencies.firebaseAuth)

    implementation(AppDependencies.swiperefreshlayout)
    implementation(project(":core"))
}
