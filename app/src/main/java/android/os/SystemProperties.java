package android.os;

/**
 * Created by Administrator on 2016/9/30.
 */

public class SystemProperties {

    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 91;

    public SystemProperties() {
        super();
    }

    public static String get(String arg2) {
        if (arg2.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        }

        return SystemProperties.native_get(arg2);
    }

    public static String get(String arg2, String arg3) {
        if (arg2.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        }

        return SystemProperties.native_get(arg2, arg3);
    }

    public static boolean getBoolean(String arg2, boolean arg3) {
        if (arg2.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        }

        return SystemProperties.native_get_boolean(arg2, arg3);
    }

    public static int getInt(String arg2, int arg3) {
        if (arg2.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        }

        return SystemProperties.native_get_int(arg2, arg3);
    }

    public static long getLong(String arg2, long arg3) {
        if (arg2.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        }

        return SystemProperties.native_get_long(arg2, arg3);
    }

    private static native String native_get(String arg0);

    private static native String native_get(String arg0, String arg1);

    private static native boolean native_get_boolean(String arg0, boolean arg1);

    private static native int native_get_int(String arg0, int arg1);

    private static native long native_get_long(String arg0, long arg1);

    private static native void native_set(String arg0, String arg1);

    public static void set(String arg2, String arg3) {
        if (arg2.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        }

        if (arg3 != null && arg3.length() > 91) {
            throw new IllegalArgumentException("val.length > 91");
        }

        SystemProperties.native_set(arg2, arg3);
    }
}
