import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinParcelize)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-P",
                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=kmp.core.mobile.utils.CommonParcelize"
            )
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(projects.shareSunreefYachts)
            implementation(project.dependencies.platform(libs.firebase.bom))
            //implementation(libs.firebase.analytics)
            //implementation(libs.firebase.crashlyticsKtx)


        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = Configs.SunreefYachtsApp.NAMESPACE
    compileSdk = Configs.Android.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = Configs.SunreefYachtsApp.APP_ID
        minSdk = Configs.Android.MIN_SDK_VERSION
        targetSdk = Configs.Android.TARGET_SDK_VERSION
        versionCode = Configs.SunreefYachtsApp.VERSION_CODE
        versionName = Configs.SunreefYachtsApp.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    debugImplementation(libs.androidx.compose.ui.tooling)
}