##-----------------------------------------General variable---------------------------------------##
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-verbose
-optimizationpasses 5
-dontpreverify


-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable


# The support library contains references to newer platform versions. Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontwarn android.support.**
-dontwarn javax.activation.**
-dontwarn javax.security.**
-dontwarn javax.lang.model.element.Modifier
-dontwarn java.awt.**
-keep class javax.** {*;}
-keep class com.sun.** {*;}
-keep class org.apache.** {*;}



##---------------------------------------------- Logs---------------------------------------##
-assumenosideeffects class android.util.Log {
      public static *** v(...);
      public static *** d(...);
      public static *** w(...);
      public static *** e(...);
      public static *** i(...);
}

-assumenosideeffects class java.io.PrintStream {
     public void println(%);
     public void println(**);
 }



##------------------------------------- Keep Android Core classes---------------------------------------##
-keep class * extends android.app.Activity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep class android.animation.ObjectAnimator { *; }
-keep class androidx.core.app.CoreComponentFactory { *; }



##--------------------------------------Keep Project classes---------------------------------------##
-keep public class jimmytrivedi.in.android.tipcalculator.R


##--------------------------------------------Keep {...}------------------------------------------##
# Explicitly preserve all serialization members. The Serializable interface is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class android.content.Context {
    public java.io.File getExternalCacheDir();
}

-keep class android.content.Context {
    public java.io.File getCacheDir();
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed through introspection.
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * {
    public protected *;
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}



##----------------------------------------------Google--------------------------------------------##
-dontwarn android.support.v4.**
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**