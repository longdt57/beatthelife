/*
 * Created by do thanh long on 1/27/21 12:17 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 12:17 PM
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("lee.group.beat")
    id("dagger.hilt.android.plugin")
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

        // ---> Add the next line
        manifestPlaceholders(
            mutableMapOf(
                "auth0Domain" to "@string/com_auth0_domain",
                "auth0Scheme" to "demo"
            )
        )

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
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
    addHilt()
    addAppCompat()
    addNavigation()
    addConstraintLayout()
    addRoom()
    addCipher()
    implementation(Network.retrofit_gson)

    implementation(project(":core"))

    implementation("com.auth0.android:auth0:1.30.0")
    addFireBase()
    implementation(AppDependencies.firebaseAuth)
    implementation(AppDependencies.firebasePerf)
    addAuthenticationUI()
}
