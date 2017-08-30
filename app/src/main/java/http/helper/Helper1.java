package http.helper;

import java.nio.ByteBuffer;

public final class Helper1 {
    private static  byte[] a;
    private static  byte[] b;

    static {
        int v5 = 256;
        byte[] v1 = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
        byte[] v2 = new byte[v5];
        byte[] v3 = new byte[v5];
        int v0;
        for(v0 = 0; v0 < v5; ++v0) {
            v2[v0] = v1[v0 >>> 4];
            v3[v0] = v1[v0 & 15];
        }

        Helper1.a = v2;
        Helper1.b = v3;
    }

    public static byte[] copyData(ByteBuffer arg4) {
        byte[] v0 = new byte[arg4.position()];
        System.arraycopy(arg4.array(), 0, v0, 0, v0.length);
        return v0;
    }

    public static int compare(Comparable arg1, Comparable arg2) {
        return arg1.compareTo(arg2);
    }

    public static boolean check (int arg1) {
        boolean v0 = true;
        if(1 != arg1) {
            v0 = false;
        }

        return v0;
    }

    public static boolean isEquals(Object arg1, Object arg2) {
        return arg1.equals(arg2);
    }
}

