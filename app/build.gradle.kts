plugins {
    id("com.android.application")
}

android {
    namespace = "algonquin.cst2335.tang0243"
    compileSdk = 34
    buildFeatures{
        viewBinding=true
    }
    defaultConfig {
        applicationId = "algonquin.cst2335.tang0243"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    val room_version = "2.4.2"
    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //implementation(fileTree(mapOf("dir" to "C:\\Users\\RickT\\AppData\\Local\\Android\\Sdk\\platforms\\android-33", "include" to listOf("*.aar", "*.jar"), "exclude" to listOf("*.abc"))))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
