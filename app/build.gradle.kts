plugins {
    id("motchecker.android.application")
    id("motchecker.android.application.compose")
    id("motchecker.detekt")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        applicationId = "io.agapps.motchecker"
        versionCode = 5
        versionName = "0.4.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":core-navigation"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))
    implementation(project(":core-data"))
    implementation(project(":core-database"))
    implementation(project(":core-network"))
    implementation(project(":feature-home"))
    implementation(project(":feature-search"))
    implementation(project(":feature-vehicledetails"))
    implementation(project(":feature-recentvehicles"))
    implementation(project(":feature-favouritevehicles"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.timberkt)
    implementation(libs.camera.core)
    implementation(libs.camera2)
    implementation(libs.room.runtime)

    kapt(libs.hilt.compiler)

    coreLibraryDesugaring(libs.android.desugarjdklibs)
}

kapt {
    correctErrorTypes = true
}
