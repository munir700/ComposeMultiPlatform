import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinParcelize)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll("-P", "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=kmp.core.mobile.utils.CommonParcelize")
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = Configs.SharedSunreefYachts.FRAMEWORK_NAME
            isStatic = true
            xcf.add(this)

            // Add bundle ID to fix the warning
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=${Configs.SharedSunreefYachts.NAMESPACE}"
            )

            export(project(Modules.CORE_MOBILE))


            // Add these for better SPM integration
            /*export("org.jetbrains.compose.runtime:runtime")
            export("org.jetbrains.compose.foundation:foundation")
            export("org.jetbrains.compose.material3:material3")*/
        }
    }

    sourceSets {
        androidMain {
            dependencies {

                implementation(compose.components.uiToolingPreview)

                implementation(libs.play.services.location)
                api(libs.play.services.maps)
                implementation(libs.maps.ktx)
                implementation(libs.maps.compose)
            }
        }


        commonMain.dependencies {

            api(project(Modules.CORE_MOBILE))

            implementation(compose.components.resources) // Keep this in shareSunreefYachts

            // put your Multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = Configs.SharedSunreefYachts.NAMESPACE
    compileSdk = Configs.Android.COMPILE_SDK_VERSION
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = Configs.Android.MIN_SDK_VERSION
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "${Configs.SharedSunreefYachts.NAMESPACE}.resources"
    generateResClass = always
}

