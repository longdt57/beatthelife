plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("lee.group.beat")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

plugin_config {
    ktlint = true
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.lee.group.beatthelife"
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("testSigning") {
            storeFile = rootProject.file("data/signing/beou-testing.keystore")
            storePassword = "beou123"
            keyAlias = "beou"
            keyPassword = "beou123"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    flavorDimensions(AppConfig.dimension)
    productFlavors {
        create("uat") {
            applicationIdSuffix = ".uat"
            versionNameSuffix = "-uat"
            signingConfig = signingConfigs.getByName("testSigning")
            manifestPlaceholders["partner"] = "beatthelife-uat"
        }

        create("prod") {
            manifestPlaceholders["partner"] = "beatthelife"
        }
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
    addConstraintLayout()
    addHilt()
    addWorker()
    addAuthenticationUI()
    addAppCompat()
    addMaterialDesign()
    addLifeCycle()
    addNavigation()
    addTimber()

    implementation(project(":core"))
    implementation(project(":chat-sdk"))
    implementation(project(":tracking"))

    addFireBase()
    implementation(AppDependencies.firebaseAuth)
    implementation(AppDependencies.firebasePerf)
    implementation(AppDependencies.firebaseCrashytics)
    implementation(AppDependencies.firebaseAnalytics)
}
