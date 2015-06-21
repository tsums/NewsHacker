-dontskipnonpubliclibraryclasses
-forceprocessing

# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

# Parcel library
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class org.parceler.Parceler$$Parcels

# Dart
-dontwarn com.f2prateek.dart.internal.**
-keep class **$$ExtraInjector { *; }
-keepnames class * { @com.f2prateek.dart.InjectExtra *;}

# PrettyTime
-keep public class com.ocpsoft.pretty.time.**
-dontwarn com.ocpsoft.pretty.time.**

# Models for data binding
-keepnames class com.tsums.newshacker.models.** { *;}
-keepclassmembernames class com.tsums.newshacker.models.** { *;}