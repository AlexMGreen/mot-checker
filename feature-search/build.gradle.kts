plugins {
    id("motchecker.android.library")
    id("motchecker.android.feature")
    id("motchecker.android.library.compose")
    id("motchecker.detekt")
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":feature-recentvehicles"))

    implementation(libs.timberkt)

    testImplementation(project(":core-test"))
    testImplementation(project(":core-database"))
    testImplementation(project(":core-database-test"))
    testImplementation(project(":core-network"))
    testImplementation(project(":core-network-test"))
    testImplementation(project(":feature-recentvehicles-test"))

    testImplementation(libs.turbine)
}
