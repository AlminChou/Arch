plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.mavenPublish)
    id("kotlin-kapt")
}

android {
    namespace = "com.almin.arch"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.almin.arch"
                artifactId = "arch"
                version = "1.0.0"  // 确保使用新版本号
            }
        }

        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/AlminChou/Arch")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = project.findProperty("ghp.token") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}


dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1")

    // OkDownload
    implementation("com.liulishuo.okdownload:okdownload:1.0.7")
    implementation("com.liulishuo.okdownload:sqlite:1.0.7")
    implementation("com.liulishuo.okdownload:okhttp:1.0.7")

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    api("com.squareup.retrofit2:converter-moshi:2.9.0") 
    api("com.squareup.moshi:moshi-kotlin:1.13.0") 
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.13.0") 
    api("com.squareup.moshi:moshi:1.13.0") 

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
}