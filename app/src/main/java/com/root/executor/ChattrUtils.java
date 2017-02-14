package com.root.executor;

public final class ChattrUtils {
    public static String a(String arg4, boolean arg5) {
        String v0 = String.valueOf(KToolsUtils.a()) + " chattr";
        return arg5 ? "echo" : String.format("%s -ai %s", v0, arg4);
    }

    public static String a() {
        return String.valueOf(KToolsUtils.a()) + " chattr";
    }
}

