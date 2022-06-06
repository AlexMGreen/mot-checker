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
    implementation(libs.mpandroidchart)
}
