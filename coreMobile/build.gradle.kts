import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinParcelize)
}

version = Configs.CoreMobile.VERSION

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.value(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-P",
                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=kmp.core.mobile.utils.CommonParcelize"
            )
        }
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = Configs.CoreMobile.FRAMEWORK_NAME
            isStatic = true

            // Add XCFramework
            //xcf.add(this)

            // Add bundle ID to fix the warning
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=${Configs.CoreMobile.NAMESPACE}"
            )
            // Add these for better SPM integration
            /*export("org.jetbrains.compose.runtime:runtime")
            export("org.jetbrains.compose.foundation:foundation")
            export("org.jetbrains.compose.material3:material3")*/
        }

        // ✅ Apply to all binaries of this target (debug/release)
        /*iosTarget.binaries.all {
            linkerOpts("-framework FirebaseRemoteConfig")
            linkerOpts("-framework FirebaseCore")
            linkerOpts("-framework FirebaseABTesting")
        }*/
    }


    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                //implementation(libs.kotlin.stdlib)

                // Add KMP dependencies here

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.ui)
                implementation(compose.components.resources)
                //api(libs.androidx.lifecycle.viewmodelCompose)
                //api(libs.androidx.lifecycle.runtimeCompose)
                //api(libs.material3)
                api(libs.kotlinx.datetime) // latest stable


                // Koin
                implementation(libs.koin.core)
                implementation(libs.koin.compose)

                //Decompose
                api(libs.decompose.core)
                api(libs.decompose.compose)
                api(libs.essenty.lifecycle)
                api(libs.essenty.lifecycle.coroutines)
                api(libs.essenty.state.keeper)
                api(libs.essenty.instance.keeper)
                api(libs.essenty.back.handler)

                // Ktor
                api(libs.ktor.core)
                api(libs.ktor.json)
                api(libs.ktor.logging)
                api(libs.ktor.serialization)
                api(libs.ktor.content.negotiation)
                api(libs.ktor.json.serialization)

                // KVault
                api(libs.kvault)
                api(libs.settings)

                // GitLive
                //api(libs.firebase.config)
                //api(libs.firebase.crashlytics)

                // Notifier
                api(libs.kmp.notifier)
                api(libs.uuid)
                api(libs.stately.common)

                // Lottie
                api(libs.lottie.compose)
                api(libs.compottie)

                // Coil dependency
                api(libs.coil.core)
                api(libs.coil.compose)
                api(libs.coil.network.ktor3)
            }
        }

        commonTest {
            dependencies {
                api(libs.kotlin.test)
            }
        }

        androidMain {
            resources.srcDirs("src/androidMain/res")

            dependencies {
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain.

                api(compose.preview)
                api(libs.androidx.activity.compose)
                api(compose.components.uiToolingPreview)

                // Image picker and crop
                implementation(libs.android.image.picker)
                implementation(libs.android.image.cropper)

                // Koin
                api(libs.koin.android)
                api(libs.koin.androidx.compose)

                // Ktor
                api(libs.ktor.android)

                // Firebase
                api(project.dependencies.platform(libs.firebase.bom))

                // Tab browser
                //api(libs.androidx.browser)

                // GitLive
                api(libs.gitlive.firebase.app)
                api(libs.gitlive.firebase.firestore)
                api(libs.gitlive.firebase.crashlytics)
                api(libs.gitlive.firebase.config)

            }
        }

        /*getByName("androidDeviceTest") {
            dependencies {
                api(libs.androidx.runner)
                api(libs.androidx.core)
                api(libs.androidx.testExt.junit)
            }
        }*/

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.


                // Ktor
                api(libs.ktor.ios)
            }
        }
    }
}

android {
    namespace = Configs.CoreMobile.NAMESPACE
    compileSdk = Configs.Android.COMPILE_SDK_VERSION

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")


    defaultConfig {
        minSdk = Configs.Android.MIN_SDK_VERSION
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(JavaVersion.VERSION_17.toString().toInt())
    }
}


compose.resources {
    publicResClass = true
    packageOfResClass = "${Configs.CoreMobile.NAMESPACE}.resources"
    generateResClass = always
}
