plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.register<JavaExec>("runDemo") {
    group = "verification"
    description = "Run the Flow comparison demo"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("learning.gof.flowComparison.FlowComparisonDemoKt")
}

