import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    @Suppress("UnstableApiUsage")
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core-model"))
                add("implementation", project(":core-ui"))
                add("implementation", project(":core-navigation"))
                add("implementation", project(":core-data"))
                add("implementation", project(":core-common"))

                add("implementation", libs.findLibrary("timberkt").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())

                add("androidTestImplementation", libs.findLibrary("junit.ext").get())
                add("androidTestImplementation", libs.findLibrary("compose.ui.test").get())
                add("androidTestImplementation", libs.findLibrary("compose.ui.test.manifest").get())
                add("androidTestImplementation", libs.findLibrary("androidx.test.core").get())

                // https://developer.android.com/studio/known-issues#error_when_rendering_compose_preview
                add("debugImplementation", libs.findLibrary("androidx.customview.poolingcontainer").get())
            }
        }
    }
}
