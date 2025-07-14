# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep your specific Proto-generated message classes
-keep class com.ica.tabletopassistant.data.dice.DiceFeatureConfig { *; }
-keep class com.ica.tabletopassistant.data.odds.OddsFeatureConfig { *; }
-keep class com.ica.tabletopassistant.data.spinners.SpinnersFeatureConfig { *; }

# General rules for Protocol Buffers
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
}

-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite$Builder {
    <fields>;
    <methods>;
}

-keepclassmembers enum * extends com.google.protobuf.ProtocolMessageEnum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class **Schema {
    *;
}

-keepclassmembers class **OuterClass* {
    *;
}

# Keep specific methods that might be used via reflection by protobuf-javalite.
-keepclassmembers class com.google.protobuf.GeneratedMessageLite {
    static java.lang.Object newInstance(java.lang.Class, ...);
}
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    public static volatile com.google.protobuf.Parser PARSER;
    public static final ** DEFAULT_INSTANCE;
    public static ** getDefaultInstance();
    public static ** parseFrom(...);
    public static ** newBuilder();
    public static ** newBuilder(com.google.protobuf.GeneratedMessageLite);
    protected java.lang.Object dynamicMethod(com.google.protobuf.GeneratedMessageLite$MethodToInvoke, ...);
}
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite$Builder {
    public static final ** DEFAULT_INSTANCE;
    public static ** getDefaultInstance();
    protected ** internalMergeFrom(com.google.protobuf.GeneratedMessageLite);
    public ** mergeFrom(com.google.protobuf.GeneratedMessageLite);
}
