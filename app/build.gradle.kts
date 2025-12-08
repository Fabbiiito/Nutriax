plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "edu.pe.epis.imagenes2"
    compileSdk = 36 // Usaremos una versión de compileSdk compatible con las dependencias

    defaultConfig {
        applicationId = "edu.pe.epis.imagenes2"
        minSdk = 27
        targetSdk = 36 // TargetSdk debe coincidir con compileSdk si es posible
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8 // Cambiado a 1.8 o 11
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Asegúrate de tener una versión compatible
    }
}

dependencies {
    // -------------------------------------------------------------------------
    // Dependencias Base de Android (Reemplazadas con cadenas directas)
    // -------------------------------------------------------------------------

    // Core KTX y Lifecycle
    implementation("androidx.core:core-ktx:1.12.0") // Versión estable
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Activity Compose (Actualizada a la versión más reciente, 1.12.1, según advertencia)
    implementation("androidx.activity:activity-compose:1.12.1")

    // -------------------------------------------------------------------------
    // Dependencias de Compose (Reemplazadas con cadenas directas)
    // -------------------------------------------------------------------------

    // BOM para manejar la compatibilidad de versiones
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))

    // UI, Graphics, Tooling y Material 3 (Resolviendo los errores 'ui' y 'material3')
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // -------------------------------------------------------------------------
    // Dependencias para Gemini y Coil (Actualizadas a las versiones más recientes)
    // -------------------------------------------------------------------------

    // 1. SDK de Google Gemini (Actualizada a 0.9.0, según advertencia)
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    // 2. Coil para cargar imágenes (Actualizada a 2.7.0, según advertencia)
    implementation("io.coil-kt:coil-compose:2.7.0")


    // -------------------------------------------------------------------------
    // Dependencias de testing (Resolviendo los errores 'junit4' y 'manifest')
    // -------------------------------------------------------------------------
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
