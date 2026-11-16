java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

val adventureVersion by project.properties

dependencies {
    api("net.kyori:adventure-api:$adventureVersion")
    api("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
    api("net.kyori:adventure-text-serializer-gson:$adventureVersion")
}
