plugins {
    java
    application
    id("org.danilopianini.gradle-java-qa") version "1.83.0"
}

repositories {
    mavenCentral()
}

tasks.javadoc {
    isFailOnError = false
}

val mainClass: String by project

application {
    // The following allows to run with: ./gradlew -PmainClass=it.unibo.oop.MyMainClass run
    mainClass.set(project.properties["mainClass"].toString())
}