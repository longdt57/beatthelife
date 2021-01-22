plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    addKotlin()
    addHilt()
    addAppCompat()
    addLifeCycle()
    addNavigation()
    addReactiveX()
    addTimber()
}
