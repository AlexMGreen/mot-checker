plugins {
    id("motchecker.android.library.test")
    id("motchecker.detekt")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-network"))

    kapt(libs.hilt.compiler)
}
