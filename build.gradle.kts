// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kspVersion = "1.9.20-Beta2-1.0.13"

    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20-Beta2" apply false
    id("com.google.devtools.ksp") version kspVersion
}
