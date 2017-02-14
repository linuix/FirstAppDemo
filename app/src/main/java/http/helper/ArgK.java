package http.helper;

import android.os.Environment;

public final class ArgK {
    public long a;
    public long b;

    public ArgK() {
        super();
    }

    public static boolean a() {
        String v0 = Environment.getExternalStorageState();
        boolean v0_1 = v0 == null ? false : v0.equals("mounted");
        return v0_1;
    }

}

