package http.demo;

/**
 * Created by Administrator on 2016/11/3.
 */

public class Helpers {
    public int a;
    public String b;
    public String c;
    public String n;
    public String m;
    public int l;

    public Helpers(int arg3, String arg4) {
        super();
        this.a = arg3;
        int v0 = arg4.indexOf("|");
        this.b = arg4.substring(0, v0);
        this.c = arg4.substring(v0 + 1, arg4.length());
    }
}
