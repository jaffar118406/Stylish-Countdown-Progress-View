// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id("maven-publish")
}

afterEvaluate {
    publishing{
        publications{
            create<MavenPublication>("maven") {
                from (components.findByName("release"))
                groupId = "com.github.jaffar118406"
                artifactId = "countdown-progress-view"
                version = "0.1.0"
            }
        }
    }
}
