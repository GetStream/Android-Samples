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
private const val COMPOSE = "1.0.1"
private const val COMPOSE_ACTIVITY = "1.3.0"
private const val STREAM = "4.15.1"
private const val CAMERA_V2 = "1.1.0-alpha07"
private const val CAMERA_VIEW = "1.0.0-alpha27"
private const val EXOPLAYER = "2.14.2"
private const val COMPOSE_ACCOMPANIST = "0.16.1"

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:$ANDROID_GRADLE_PLUGIN_VERSION"
    const val glide = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
    const val androidxActivityKtx = "androidx.activity:activity-ktx:$ANDROIDX_ACTIVITY_KTX_VERSION"
    const val androidxAppCompat = "androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT_VERSION"
    const val androidXLifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
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

    const val exoPlayer = "com.google.android.exoplayer:exoplayer:$EXOPLAYER"

    const val composeCompiler = "androidx.compose.compiler:compiler:$COMPOSE"
    const val composeUi = "androidx.compose.ui:ui:$COMPOSE"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$COMPOSE"
    const val composeFoundation = "androidx.compose.foundation:foundation:$COMPOSE"
    const val composeMaterial = "androidx.compose.material:material:$COMPOSE"
    const val composeMaterialIconsCore = "androidx.compose.material:material-icons-core:$COMPOSE"
    const val composeMaterialIconsExtended =
        "androidx.compose.material:material-icons-extended:$COMPOSE"
    const val composeAccompanistPermissions =
        "com.google.accompanist:accompanist-permissions:${COMPOSE_ACCOMPANIST}"
    const val composeActivity = "androidx.activity:activity-compose:$COMPOSE_ACTIVITY"
    const val composeCameraLifecycle = "androidx.camera:camera-lifecycle:$CAMERA_V2"
    const val composeCameraView = "androidx.camera:camera-view:$CAMERA_VIEW"
    const val composeCamera2 = "androidx.camera:camera-camera2:$CAMERA_V2"

    /**
     * Stream chat sdk components
     */
    const val streamClient = "io.getstream:stream-chat-android-client:$STREAM"
    const val streamOffline = "io.getstream:stream-chat-android-offline:$STREAM"
    const val streamCompose = "io.getstream:stream-chat-android-compose:$STREAM-beta"
    const val composeCoil = "io.coil-kt:coil-compose:$COIL_VERSION"
}
