import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("androidx.room") version "2.8.4"
}

android {
    namespace = "com.retr0.projectretr0"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.retr0.projectretr0"
        minSdk = 26
        targetSdk = 35
        versionCode = 12
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
                arguments("-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON")
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    signingConfigs {
        create("release") {
            val storeFilePath = providers.environmentVariable("RELEASE_STORE_FILE").orNull
            storeFile = storeFilePath?.let(::file)
            storePassword = providers.environmentVariable("RELEASE_STORE_PASSWORD").orNull
            keyAlias = providers.environmentVariable("RELEASE_KEY_ALIAS").orNull
            keyPassword = providers.environmentVariable("RELEASE_KEY_PASSWORD").orNull
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            // Debug builds use the live Worker for local upload/publish testing; no credentials are embedded.
            buildConfigField("String", "COMMUNITY_API_BASE_URL", "\"https://save-files.armaandeep08-ap.workers.dev\"")
            buildConfigField("String", "COMMUNITY_API_KEY", "\"\"")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "COMMUNITY_API_BASE_URL", "\"https://save-files.armaandeep08-ap.workers.dev\"")
            buildConfigField("String", "COMMUNITY_API_KEY", "\"\"")
            signingConfig = signingConfigs.findByName("release")
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
        aidl = true
        buildConfig = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
            keepDebugSymbols += "**/libsonic-extractor.so"
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.documentfile)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.shizuku.api)
    implementation(libs.shizuku.provider)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.cronet.api)
    implementation(libs.cronet.embedded)
    implementation(libs.coil.compose)
    implementation(libs.androidx.exifinterface)
    testImplementation(libs.junit)
    testImplementation(libs.json)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.room.testing)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
