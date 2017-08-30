package http.demo;


import com.demo.utils.LogUtil;

public final class UrlTest {
    public static boolean a;
    private static int d;
    private static String e;
    public static String b;
    private static String c;


    static {
        UrlTest.a = true;
        UrlTest.b = "http://pmir.3g.qq.com/";
        UrlTest.c = "http://bh.3g.qq.com";
        UrlTest.d = 14;
        UrlTest.e = "1.0.14";
    }

    public static String init() {
        return UrlTest.e;
    }

    public static void init(boolean arg2, boolean arg3) {
        String url = arg2 ? "http://wuptest.cs0309.3g.qq.com" : "http://pmir.3g.qq.com/";
        UrlTest.b = url;
        UrlTest.a = arg3;
        LogUtil.e("useTestURL = " + arg2 + ", logSwitchOn = " + arg3);
        url = arg2 ? "http://bh.cs0309.3g.qq.com" : "http://bh.3g.qq.com";
        UrlTest.c = url;
    }

    public static int b() {
        return UrlTest.d;
    }
}

