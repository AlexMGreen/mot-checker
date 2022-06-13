plugins {
    id("motchecker.android.library")
    id("motchecker.android.feature")
    id("motchecker.android.library.compose")
    id("motchecker.detekt")
}

dependencies {
    implementation(project(":feature-camerasearch"))
    api(project(":feature-favouritevehicles"))

    testImplementation(project(":core-test"))
    testImplementation(project(":core-database"))
    testImplementation(project(":core-database-test"))
    testImplementation(project(":feature-favouritevehicles-test"))

    testImplementation(libs.turbine)

}
