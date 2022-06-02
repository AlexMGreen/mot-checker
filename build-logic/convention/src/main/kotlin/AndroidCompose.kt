import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose").get().toString()
        }
    }

    dependencies {
        add("implementation", libs.findLibrary("compose.ui").get())
        add("implementation", libs.findLibrary("compose.material").get())
        add("implementation", libs.findLibrary("compose.tooling.preview").get())
        add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())

        // https://developer.android.com/studio/known-issues#error_when_rendering_compose_preview
        add("debugImplementation", libs.findLibrary("viewmodel.compose").get())
        add("debugImplementation", libs.findLibrary("androidx.customview.poolingcontainer").get())
        add("debugImplementation", libs.findLibrary("lifecycle.runtime.ktx").get())
        add("debugImplementation", libs.findLibrary("androidx.savedstate.ktx").get())
    }
}
