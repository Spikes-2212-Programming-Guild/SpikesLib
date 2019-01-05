/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds/
 */

group = "com.spikes2212"
version = "2.0.1"

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    maven {
        url = uri("http://first.wpi.edu/FRC/roborio/maven/release/")
    }
}

val wpilibVersion by extra("2019.1.1")
val cscoreVersion by extra("1.3.0")
val opencvVersion by extra("3.4.3")

dependencies {
    implementation("edu.wpi.first.wpilibj:wpilibj-java:$wpilibVersion")
    implementation("edu.wpi.first.cscore:cscore-java:$cscoreVersion")
    implementation("edu.wpi.first.thirdparty.frc2019.opencv:opencv-java:$opencvVersion")
    implementation("edu.wpi.first.cameraserver:cameraserver-java:$wpilibVersion")
}


sourceSets {
    main {
        java {
            srcDir("src/")
        }
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.spikes2212"
            artifactId = "sl"
            version = "2.0.1"

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "publish"
            url = uri("")
        }
    }
}