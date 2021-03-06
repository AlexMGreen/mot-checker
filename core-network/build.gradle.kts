plugins {
    id("motchecker.android.library")
    id("motchecker.detekt")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    defaultConfig {
        buildConfigField("String", "MOT_API_KEY", "\"${System.getenv("MOT_API_KEY")}\"")
    }
}

dependencies {
    implementation(project(":core-common"))

    implementation(libs.hilt.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    implementation(libs.okhttp.logging.interceptor)

    kapt(libs.hilt.compiler)
    kapt(libs.moshi.codegen)
}
