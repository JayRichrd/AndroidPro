/**
 * 版本信息
 */
interface Versions {
    def minSdk = 21
    def targetSdk = 31
    def compileSdk = 31
    def jetbrains_kotlin = '1.8.0'
    def room = '2.4.3'
    def wcdb = '1.0.8'
    def ndkAbi = ['armeabi-v7a', 'arm64-v8a']
    def ndk = '21.4.7075529'
}

/**
 * 依赖库路径
 */
interface Deps {
    /**
     * plugin
     */
    def android_gradle_plugin = "com.android.tools.build:gradle:7.0.0"
    def kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.jetbrains_kotlin}"
    /**
     * dependence
     */
    //androidx
    def androidx_core_ktx = 'androidx.core:core-ktx:1.8.0'
    def androidx_appcompat = 'androidx.appcompat:appcompat:1.4.2'
    def androidx_constraintlayout = 'androidx.constraintlayout:constraintlayout:2.1.4'
    def androidx_multidex = 'androidx.multidex:multidex:2.0.1'
    def androidx_room_runtime = "androidx.room:room-runtime:${Versions.room}"
    def androidx_room_compiler = "androidx.room:room-compiler:${Versions.room}"
    def androidx_lifecycle_process = "androidx.lifecycle:lifecycle-process:2.4.0"
    //kotlin
    def jetbrains_kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.jetbrains_kotlin}"
    //third party from google
    def google_android_material = "com.google.android.material:material:1.6.1"
    def permissions_dispatcher_processor = "com.github.permissions-dispatcher:permissionsdispatcher-processor:4.9.1"
    def permissions_dispatcher_ktx = "com.github.permissions-dispatcher:ktx:1.1.3"
    def reactivex_rxjava2_rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    def squareup_okhttp3 = "com.squareup.okhttp3:okhttp:3.14.9"
    def orhanobut_logger = "com.orhanobut:logger:2.2.0"
    def tencent_wcdb_android = "com.tencent.wcdb:wcdb-android:${Versions.wcdb}"
    def tencent_wcdb_room = "com.tencent.wcdb:room:${Versions.wcdb}"
    def gson = "com.google.code.gson:gson:2.8.9"
}