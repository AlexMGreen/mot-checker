plugins {
    id("motchecker.android.library")
    id("motchecker.detekt")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-network"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    implementation(libs.retrofit)

    testImplementation(project(":core-test"))
    testImplementation(project(":core-network-test"))
    testImplementation(project(":core-database-test"))

    kapt(libs.hilt.compiler)
}
