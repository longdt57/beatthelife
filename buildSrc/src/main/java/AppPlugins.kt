import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

class AppPlugins : Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create<BeatExtension>(BEAT_PLUGIN_CONFIG)

        // Read MyModuleExtension values in afterEvaluate block.
        target.afterEvaluate {
            target.extensions.getByType(BeatExtension::class.java).run {
                if (ktlint) {
                    setupKtlint(target)
                }
            }
        }
    }

    private fun setupKtlint(project: Project) {
        val ktlint = project.configurations.create(KTLINT)
        project.dependencies {
            add(KTLINT, "com.pinterest:ktlint:${Versions.pinterest}")
        }
        project.tasks.register<JavaExec>(KTLINT) {
            group = "verification"
            description = "Check Kotlin code style."
            classpath = ktlint
            main = "com.pinterest.ktlint.Main"
            args("--android", "src/**/*.kt")
        }

        project.tasks.named("check") {
            dependsOn(ktlint)
        }

        project.tasks.register<JavaExec>("ktlintFormat") {
            group = "formatting"
            description = "Fix Kotlin code style deviations."
            classpath = ktlint
            main = "com.pinterest.ktlint.Main"
            args("--android", "-F", "src/**/*.kt")
        }
    }

    open class BeatExtension {
        var ktlint: Boolean = true
    }

    companion object {
        const val KTLINT = "ktlint"
        const val BEAT_PLUGIN_CONFIG = "plugin_config"
    }
}
