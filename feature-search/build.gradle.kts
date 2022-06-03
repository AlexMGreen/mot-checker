plugins {
    id("motchecker.android.library")
    id("motchecker.android.feature")
    id("motchecker.android.library.compose")
    id("motchecker.detekt")
}

dependencies {
    implementation(libs.timberkt)
    implementation(libs.mpandroidchart)
}
