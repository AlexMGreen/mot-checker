plugins {
    id("motchecker.android.library")
    id("motchecker.android.feature")
    id("motchecker.android.library.compose")
    id("motchecker.detekt")
}

dependencies {
    implementation(libs.timberkt)
    implementation(libs.mlkit.text.recognition)
    implementation(libs.camera.core)
    implementation(libs.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
}
