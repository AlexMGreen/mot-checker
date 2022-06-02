plugins {
    id("motchecker.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-network"))

    implementation(libs.hilt.android)
    implementation(libs.retrofit)

    kapt(libs.hilt.compiler)
}
