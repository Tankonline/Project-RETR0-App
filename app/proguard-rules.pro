# Shizuku
-keep class rikka.shizuku.** { *; }
-keep interface rikka.shizuku.** { *; }

# Project RETR0 Android components and AIDL
# These entry points are instantiated by Android or Shizuku from manifest/binder
# metadata and must remain present and constructible after R8 shrinking.
-keep class com.retr0.projectretr0.MainActivity { *; }
-keep class com.retr0.projectretr0.Retr0UserService { *; }
-keep class com.retr0.projectretr0.installer.Retr0InstallService { *; }
-keep class com.retr0.projectretr0.IRetr0UserService { *; }
-keep class com.retr0.projectretr0.IRetr0UserService$Stub { *; }
-keep class com.retr0.projectretr0.IRetr0UserService$Stub$Proxy { *; }

# Data Models
-keep class com.retr0.projectretr0.installer.InstallManifest { *; }
-keep class com.retr0.projectretr0.installer.InstallFile { *; }
-keep class com.retr0.projectretr0.installer.CommunitySave { *; }
-keep class com.retr0.projectretr0.installer.CommunityMusic { *; }
-keep class com.retr0.projectretr0.installer.LibraryMusic { *; }
-keep class com.retr0.projectretr0.installer.SaveProfile { *; }

# yt-dlp & FFmpeg
-keep class com.yausername.youtubedl_android.** { *; }
-keep class com.yausername.ffmpeg.** { *; }

# Gson / JSON (if used for manifest parsing)
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.TypeAdapter

# Cronet (Chromium Network Stack)
-keep class org.chromium.net.** { *; }
-keep interface org.chromium.net.** { *; }
-dontwarn org.chromium.net.**

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao
-keep class com.retr0.projectretr0.installer.persistence.** { *; }
-dontwarn androidx.room.**

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    java.lang.String name;
}
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory { *; }
-keep class kotlinx.coroutines.android.HandlerContext { *; }
-dontwarn kotlinx.coroutines.**
