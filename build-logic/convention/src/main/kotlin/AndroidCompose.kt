import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("UnstableApiUsage")
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
        add("implementation", libs.findLibrary("hilt.navigation.compose").get())
        add("implementation", libs.findLibrary("navigation.compose").get())
        add("implementation", libs.findLibrary("activity.compose").get())
        add("implementation", libs.findLibrary("viewmodel.compose").get())
        add("implementation", libs.findLibrary("accompanist.insets").get())
        add("implementation", libs.findLibrary("accompanist.flowlayout").get())

        add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())

        // https://developer.android.com/studio/known-issues#error_when_rendering_compose_preview
        add("debugImplementation", libs.findLibrary("viewmodel.compose").get())
        add("debugImplementation", libs.findLibrary("androidx.customview.poolingcontainer").get())
        add("debugImplementation", libs.findLibrary("lifecycle.runtime.ktx").get())
        add("debugImplementation", libs.findLibrary("androidx.savedstate.ktx").get())
    }
}
