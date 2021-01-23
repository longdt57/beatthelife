plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
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

    androidExtensions {
        isExperimental = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    addKotlin()
    addHilt()
    addReactiveX()
    addsRetrofit()
    addMaterialDesign()
    addConstraintLayout()
    addLifeCycle()
    addTimber()

    addFireBase()
    implementation(AppDependencies.firebaseFireStore)
    implementation(AppDependencies.firebaseAuth)

    implementation(project(":core"))

    implementation(AppDependencies.glide)
}
