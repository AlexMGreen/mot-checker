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

    testImplementation(project(":core-test"))
    testImplementation(project(":core-database-test"))
    testImplementation(project(":feature-favouritevehicles-test"))

    testImplementation(libs.turbine)

    kapt(libs.room.compiler)
}
