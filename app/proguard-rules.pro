# ProGuard / R8 rules for Kiebord

# Keep JNI / Rust NativeEngine methods
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class dev.darkdesign69.onlykeyboard.feature.ime.NativeEngine {
    *;
}

# Keep Activities, Services, and components launched by intent or reflection
-keep class dev.darkdesign69.onlykeyboard.kiebord.MainActivity { *; }
-keep class dev.darkdesign69.onlykeyboard.feature.ime.KiebordService { *; }
-keep class androidx.emoji2.emojipicker.BundledEmojiListLoader { *; }

# Keep Kotlinx Serialization data models
-keepattributes *Annotation*,InnerClasses
-dontnote kotlinx.serialization.SerializationKt

-keepclassmembers class * {
    *** Companion;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep class dev.darkdesign69.onlykeyboard.common.model.** { *; }
-keep class dev.darkdesign69.onlykeyboard.theme.** { *; }
-keep class dev.darkdesign69.onlykeyboard.data.layout.** { *; }

