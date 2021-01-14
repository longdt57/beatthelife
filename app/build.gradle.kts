plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("lee.group.beat")
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
        create("testSigned") {
            storeFile = rootProject.file("data/keystore/leegroup.keystore")
            storePassword = "yeuem1508"
            keyAlias = "leegroup"
            keyPassword = "yeuem1508"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
            signingConfig = signingConfigs.getByName("testSigned")
            manifestPlaceholders["partner"] = "beatthelife-uat"
        }

        create("prod") {
            signingConfig = signingConfigs.getByName("testSigned")
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

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.2")

    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")
    // When using Kotlin.
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha02")

    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
