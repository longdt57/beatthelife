// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath(kotlin(module = "gradle-plugin", version = "1.3.72"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.30.1-alpha")
        classpath ("com.google.gms:google-services:4.3.4")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
