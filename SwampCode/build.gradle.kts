plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application.mainClass = "com.groda.discordbot.Bot"
group = "com.groda"
version = "1.0"

val jdaVersion = "5.0.0-beta.19"
val gsonVersion = "2.10.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true

    // Set this to the version of java you want to use,
    // the minimum required for JDA is 1.8
    sourceCompatibility = "21"
}