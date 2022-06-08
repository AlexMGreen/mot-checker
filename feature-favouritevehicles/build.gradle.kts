plugins {
    id("motchecker.android.library")
    id("motchecker.android.feature")
    id("motchecker.android.library.compose")
    id("motchecker.detekt")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core-database"))

    implementation(libs.room.ktx)

    kapt(libs.room.compiler)
}
