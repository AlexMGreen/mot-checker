plugins {
    id("motchecker.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(libs.hilt.android)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    kapt(libs.hilt.compiler)
    kapt(libs.room.compiler)
}