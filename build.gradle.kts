plugins {
    java
    `java-library`
}

allprojects {
    apply {
        plugin("java")
        plugin("java-library")
    }

    group = "net.azisaba.azisabamod"
    version = "2.1.0-SNAPSHOT"

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
