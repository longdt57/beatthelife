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

        // ---> Add the next line
        manifestPlaceholders(
            mutableMapOf(
                "auth0Domain" to "@string/com_auth0_domain",
                "auth0Scheme" to "demo"
            )
        )
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
    addAppCompat()
    addMaterialDesign()
    addNavigation()

    addHilt()
    addWorker()
    addAuthenticationUI()
    addLifeCycle()
    addTimber()

    leeImplementation(project(":core"))
    leeImplementation(project(":chat-sdk"))
    leeImplementation(project(":tracking"))
    leeImplementation(project(":auth"))

    addFireBase()
    implementation(AppDependencies.firebasePerf)
    implementation(AppDependencies.firebaseCrashytics)
    implementation(AppDependencies.firebaseAnalytics)
}
