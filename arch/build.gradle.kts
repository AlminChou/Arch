plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
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


publishing {
    publications {
        // 创建 Maven 发布配置
        create<MavenPublication>("release") {
            groupId = "com.github.AlminChou"
            artifactId = "Arch"
            version = "1.0.0"

            // 发布库的内容，发布 release 版本
            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            // GitHub Packages URL
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/AlminChou/Arch")
            credentials {
                // 从环境变量或 local.properties 中读取 GitHub 用户名和 token
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}


dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)

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
    api("com.squareup.retrofit2:converter-moshi:+")
    api("com.squareup.moshi:moshi-kotlin:+")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:+")
    api("com.squareup.moshi:moshi:+")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
}