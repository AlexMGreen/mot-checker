plugins {
    id("motchecker.android.library.test")
    id("motchecker.detekt")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-test"))
    implementation(project(":core-common"))
    implementation(project(":core-network"))

    implementation(libs.moshi)

    kapt(libs.hilt.compiler)
}
