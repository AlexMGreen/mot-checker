plugins {
    id("motchecker.android.library")
    id("motchecker.android.library.compose")
    id("motchecker.detekt")
}

dependencies {
    implementation(project(":core-common"))
}
