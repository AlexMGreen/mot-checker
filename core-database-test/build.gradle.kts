plugins {
    id("motchecker.android.library.test")
    id("motchecker.detekt")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-test"))
    implementation(project(":core-database"))

    kapt(libs.hilt.compiler)
}
