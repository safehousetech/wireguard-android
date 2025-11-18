@file:Suppress("UnstableApiUsage")

import org.gradle.api.tasks.testing.logging.TestLogEvent

val pkg: String = providers.gradleProperty("wireguardPackageName").get()

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    `maven-publish`
    signing
}

android {
    compileSdk = 36
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    namespace = "${pkg}.tunnel"
    defaultConfig {
        minSdk = 21
    }
    externalNativeBuild {
        cmake {
            path("tools/CMakeLists.txt")
        }
    }
    testOptions.unitTests.all {
        it.testLogging { events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED) }
    }
    buildTypes {
        all {
            externalNativeBuild {
                cmake {
                    targets("libwg-go.so", "libwg.so", "libwg-quick.so")
                    arguments("-DGRADLE_USER_HOME=${project.gradle.gradleUserHomeDir}")
                    arguments("-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON")
                }
            }
        }
        release {
            externalNativeBuild {
                cmake {
                    arguments("-DANDROID_PACKAGE_NAME=${pkg}")
                }
            }
        }
        debug {
            externalNativeBuild {
                cmake {
                    arguments("-DANDROID_PACKAGE_NAME=${pkg}.debug")
                }
            }
        }
    }
    lint {
        disable += "LongLogTag"
        disable += "NewApi"
    }
    buildFeatures {
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.collection)
    implementation(libs.core.ktx)
    implementation(libs.androidx.databinding.runtime)
    compileOnly(libs.jsr305)
    testImplementation(libs.junit)

    implementation(libs.androidx.preference.ktx)

//    implementation(libs.androidx.junit.ktx)
    implementation(libs.chucker)

    implementation(libs.gson)

    implementation(libs.dagger)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)

    implementation(libs.squareup.okhttp.logging.interceptor)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.gson)

    //datastore
    implementation(libs.androidx.datastore.preferences)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = pkg
            artifactId = "tunnel"
            version = providers.gradleProperty("wireguardVersionName").get()
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name = "WireGuard Tunnel Library"
                description = "Embeddable tunnel library for WireGuard for Android"
                url = "https://www.wireguard.com/"

                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                        distribution = "repo"
                    }
                }
                scm {
                    connection = "scm:git:https://git.zx2c4.com/wireguard-android"
                    developerConnection = "scm:git:https://git.zx2c4.com/wireguard-android"
                    url = "https://git.zx2c4.com/wireguard-android"
                }
                developers {
                    organization {
                        name = "WireGuard"
                        url = "https://www.wireguard.com/"
                    }
                    developer {
                        name = "WireGuard"
                        email = "team@wireguard.com"
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/safehousetech/wireguard-android")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
