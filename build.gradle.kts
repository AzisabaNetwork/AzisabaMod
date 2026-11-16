plugins {
    java
    `java-library`
    id("com.gradleup.shadow") version "9.4.1" apply false
}

allprojects {
    apply {
        plugin("java")
        plugin("java-library")
    }

    group = "net.azisaba.azisabamod"
    version = "2.3.0-SNAPSHOT"

    repositories {
        // mavenLocal()
        mavenCentral()
        maven("https://repo.azisaba.net/repository/maven-public/")
        maven("https://libraries.minecraft.net/")
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
    }
}
