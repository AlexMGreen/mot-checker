plugins {
    id("motchecker.android.library.test")
    id("motchecker.detekt")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-database-test"))
    implementation(project(":feature-recentvehicles"))

    kapt(libs.hilt.compiler)
}
