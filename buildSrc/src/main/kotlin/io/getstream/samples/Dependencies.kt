package io.getstream.samples

private const val KOTLIN_VERSION = "1.5.21"
private const val ANDROID_GRADLE_PLUGIN_VERSION = "7.0.0"
private const val GLIDE_VERSION = "4.11.0"
private const val ANDROIDX_ACTIVITY_KTX_VERSION = "1.2.0"
private const val ANDROIDX_APPCOMPAT_VERSION = "1.2.0"
private const val ANDROIDX_CARD_VIEW_VERSION = "1.0.0"
private const val ANDROIDX_KTX_VERSION = "1.3.2"
private const val ANDROIDX_CONSTRAINT_LAYOUT_VERSION = "2.0.4"
private const val ANDROIDX_LEGACY_SUPPORT = "1.0.0"
private const val ANDROIDX_LIFECYCLE_EXTENSIONS_VERSION = "2.2.0"
private const val ANDROIDX_LIFECYCLE_RUNTIME = "2.3.1"
private const val ANDROIDX_RECYCLERVIEW_VERSION = "1.2.0-beta01"
private const val ANDROIDX_VIEW_PAGER_2_VERSION = "1.0.0"
private const val COIL_VERSION = "1.3.0"
private const val FRAGMENT_KTX_VERSION = "1.3.0"
private const val MATERIAL_COMPONENTS_VERSION = "1.3.0"
private const val NAVIGATION_VERSION = "2.3.3"
private const val PICASSO_VERSION = "2.71828"
private const val TIMBER_VERSION = "4.7.1"
private const val YOUTUBE_PLAYER_VERSION = "10.0.5"
private const val KTLINT_PLUGIN_VERSION = "10.0.0"
private const val COMPOSE_VERSION = "1.0.1"
private const val COMPOSE_ACTIVITY_VERSION = "1.3.0"
private const val STREAM_VERSION = "4.17.0"
private const val CAMERA_V2_VERSION = "1.1.0-alpha07"
private const val CAMERA_VIEW_VERSION = "1.0.0-alpha27"
private const val COMPOSE_ACCOMPANIST_VERSION = "0.16.1"
private const val COMPOSE_VIEW_MODEL_VERSION = "1.0.0-alpha07"
private const val COMPOSE_NAVIGATION_VERSION = "2.4.0-alpha06"

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:$ANDROID_GRADLE_PLUGIN_VERSION"
    const val glide = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
    const val androidxActivityKtx = "androidx.activity:activity-ktx:$ANDROIDX_ACTIVITY_KTX_VERSION"
    const val androidxAppCompat = "androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT_VERSION"
    const val androidXLifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:$ANDROIDX_LIFECYCLE_RUNTIME"
    const val androidxCardView = "androidx.cardview:cardview:$ANDROIDX_CARD_VIEW_VERSION"
    const val androidxCoreKtx = "androidx.core:core-ktx:$ANDROIDX_KTX_VERSION"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:$ANDROIDX_CONSTRAINT_LAYOUT_VERSION"
    const val androidxLegacySupport = "androidx.legacy:legacy-support-v4:$ANDROIDX_LEGACY_SUPPORT"
    const val androidxLifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:$ANDROIDX_LIFECYCLE_EXTENSIONS_VERSION"
    const val androidxRecyclerview =
        "androidx.recyclerview:recyclerview:$ANDROIDX_RECYCLERVIEW_VERSION"
    const val androidxViewPager2 = "androidx.viewpager2:viewpager2:$ANDROIDX_VIEW_PAGER_2_VERSION"
    const val coil = "io.coil-kt:coil:$COIL_VERSION"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:$FRAGMENT_KTX_VERSION"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val materialComponents =
        "com.google.android.material:material:$MATERIAL_COMPONENTS_VERSION"
    const val navigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VERSION"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
    const val picasso = "com.squareup.picasso:picasso:$PICASSO_VERSION"
    const val timber = "com.jakewharton.timber:timber:$TIMBER_VERSION"
    const val youtubePlayer =
        "com.pierfrancescosoffritti.androidyoutubeplayer:core:$YOUTUBE_PLAYER_VERSION"
    const val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:$KTLINT_PLUGIN_VERSION"

    const val composeCompiler = "androidx.compose.compiler:compiler:$COMPOSE_VERSION"
    const val composeUi = "androidx.compose.ui:ui:$COMPOSE_VERSION"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
    const val composeFoundation = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
    const val composeMaterial = "androidx.compose.material:material:$COMPOSE_VERSION"
    const val composeMaterialIconsCore =
        "androidx.compose.material:material-icons-core:$COMPOSE_VERSION"
    const val composeMaterialIconsExtended =
        "androidx.compose.material:material-icons-extended:$COMPOSE_VERSION"
    const val composeAccompanistPermissions =
        "com.google.accompanist:accompanist-permissions:${COMPOSE_ACCOMPANIST_VERSION}"
    const val composeActivity = "androidx.activity:activity-compose:$COMPOSE_ACTIVITY_VERSION"
    const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:$COMPOSE_VIEW_MODEL_VERSION"
    const val composeCameraLifecycle = "androidx.camera:camera-lifecycle:$CAMERA_V2_VERSION"
    const val composeCameraView = "androidx.camera:camera-view:$CAMERA_VIEW_VERSION"
    const val composeCamera2 = "androidx.camera:camera-camera2:$CAMERA_V2_VERSION"
    const val composeCoil = "io.coil-kt:coil-compose:$COIL_VERSION"
    const val composeNavigation = "androidx.navigation:navigation-compose:$COMPOSE_NAVIGATION_VERSION"

    /**
     * Stream chat sdk components
     */
    const val streamClient = "io.getstream:stream-chat-android-client:$STREAM_VERSION"
    const val streamOffline = "io.getstream:stream-chat-android-offline:$STREAM_VERSION"
    const val streamCompose = "io.getstream:stream-chat-android-compose:$STREAM_VERSION-beta"
}
