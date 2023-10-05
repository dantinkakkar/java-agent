plugins {
    id("java")
}

group = "com.sahaj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.javassist:javassist:3.21.0-GA")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes["Premain-Class"] = "com.sahajagent.PerfAgent"
        attributes["Can-Retransform-Classes"] = "true"
        attributes["Can-Redefine-Classes"] = "true"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}