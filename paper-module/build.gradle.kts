plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("com.gradleup.shadow") version "9.4.1"
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

group = "net.azisaba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common")) {
        exclude("com.google.code.gson", "gson")
    }
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    compileOnly("org.spongepowered:mixin:0.8.7")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    jar {
        manifest {
            attributes(mapOf(
                "MixinConfigs" to "azisabamod-integration.mixins.json",
            ))
        }
    }

    shadowJar {
        manifest {
            attributes(mapOf(
                "MixinConfigs" to "azisabamod-integration.mixins.json",
            ))
        }
    }
}
