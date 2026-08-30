# Shizuku
-keep class rikka.shizuku.** { *; }
-keep interface rikka.shizuku.** { *; }

# Project RETR0 AIDL
-keep class com.example.projectretr0installer.IRetr0UserService { *; }
-keep class com.example.projectretr0installer.IRetr0UserService$Stub { *; }
-keep class com.example.projectretr0installer.IRetr0UserService$Stub$Proxy { *; }

# Data Models
-keep class com.example.projectretr0installer.installer.InstallManifest { *; }
-keep class com.example.projectretr0installer.installer.InstallFile { *; }
-keep class com.example.projectretr0installer.installer.CommunitySave { *; }
-keep class com.example.projectretr0installer.installer.CommunityMusic { *; }
-keep class com.example.projectretr0installer.installer.LibraryMusic { *; }
-keep class com.example.projectretr0installer.installer.SaveProfile { *; }

# yt-dlp & FFmpeg
-keep class com.yausername.youtubedl_android.** { *; }
-keep class com.yausername.ffmpeg.** { *; }

# Gson / JSON (if used for manifest parsing)
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.TypeAdapter

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    java.lang.String name;
}
