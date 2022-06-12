plugins {
    id("motchecker.android.library")
    id("motchecker.detekt")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":core-test"))
}
