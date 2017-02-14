package http.utils;

import android.content.Context;

import com.demo.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Administrator on 2016/11/3.
 */

public class HttpUtils {
    private static Context context;
    private String b;
    private String c;
    private  HttpURLConnection d;
    private byte[] e;
    private int f;
    private Hashtable g;
    private static boolean h;
    private boolean i;
    private byte j;
    private byte k;

    private HttpUtils(Context arg3, String arg4) {
        super();
        this.c = "GET";
        this.f = -1;
        this.g = new Hashtable(0);
        this.h = false;
        this.i = true;
        this.j = 0;
        this.k = 0;
        this.context = arg3;
        this.b = arg4;
        connection(arg4);
    }

    private void connection(String  arg7)
    {
        byte a1 = NetUtils.a(HttpUtils.context);
        if (a1 != -1) {
            try {
                a(new URL(arg7), (byte) 0);//初始化httpUrlConn
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
        else if ( a1 == -1)
        {
            //没有网络连接，请检查网络
        }
    }

    public InputStream b()
    {
        try {
            if (d == null)
            {
                return  null;
            }
            return d.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    /**
     * respCode
     */
    public final int c() {
        return this.f;
    }

    /**
     * content-length
     */
    public final long e() {
        long v0 = -1;
        String v2 = this.b("content-length");
        if (v2 != null) {
            try {
                v0 = Long.parseLong(v2.trim());
            } catch (Exception v0_1) {
                throw new IllegalArgumentException("-56, get header field: " + v0_1.getMessage());
            }
        }

        return v0;
    }

    private String b(String arg6) {
        try {
            return this.d.getHeaderField(arg6);
        } catch (Exception v0) {
            throw new IllegalArgumentException("-56, get header field: " + v0.getMessage());
        }
    }
    /**
     * getInstance
     *
     */
    public static HttpUtils a(Context arg6, String arg7) {
        byte v2 = 0;//省略了网络的判断
        HttpUtils v0_1 = null;
        v0_1 = new HttpUtils(arg6, arg7);

        //初始化httpUrlConn的

        return v0_1;
    }
    /**
     * 请求方式的设置
     * method
     */
    public final void a(String arg2) {
        this.c = arg2;
        if ("GET".equalsIgnoreCase(arg2)) {
            this.c = "GET";
        } else if ("POST".equalsIgnoreCase(arg2)) {
            this.c = "POST";
        }
    }
    /**
     * 请求网络
     */
    public final int a() {
        int v0_11 = 0;
        byte v0_10;
        int v1;
        int v7 = 200;
        boolean v2 = false;
        int v6 = 2;
        try {
            while (true)
            {
                this.d.setRequestProperty("Cookie", "");
                this.d.setRequestProperty("Accept", "*/*");
                this.d.setRequestProperty("Accept-Charset", "utf-8");
                this.d.setRequestProperty("Content-Type", "application/octet-stream");
                this.d.setInstanceFollowRedirects(true);
                System.setProperty("http.keepAlive", "false");
                if (!"GET".equalsIgnoreCase(this.c)) {
                    LogUtil.e("POST");
                    v1 = -2000;
                    this.d.setRequestMethod("POST");
                    this.d.setDoOutput(true);
                    this.d.setDoInput(true);
                    this.d.setUseCaches(false);
                    if (this.e != null) {
                        this.d.setRequestProperty("Content-length", "" + this.e.length);
                        OutputStream v0_9 = this.d.getOutputStream();
                        v0_9.write(this.e);
                        v0_9.flush();
                        v0_9.close();
                        LogUtil.e("wite data over");
                    }
                }


                else if ("GET".equalsIgnoreCase(c))//GET方式
                {
                    LogUtil.e("GET");
                    LogUtil.e("准备求情第一次网络请求，charset ="+c);
                    d.setRequestMethod("GET");
                    v1 = -3000;
                    /**
                     *
                     * 调用g()-->httpURLConnection 重新指向响应反馈回来的连接，直接请求
                     * */
                    String redirectUrl = g();
                    if(redirectUrl == null)
                    {
                         LogUtil.e("没有请求到下载地址 ，重定向url出现空值  ,但是，拿到了反馈回来的数据请求，准备写文件了 ！！");
                        this.f = this.d.getResponseCode();
                        return 0;//直接写死这个值
                    }
                    LogUtil.d("redirect url =" + redirectUrl);
                    d.disconnect();
                    LogUtil.e("准备求情第二次网络请求，charset ="+c);
                    a(new URL(redirectUrl), NetUtils.a(context));
                    a(c);//添加编码格式
                    a();//发送网络请求
                }


                break;
            }

            /**
             * 除了请求时post的方法，现编 是采用的GET的方式请求，主要是针对的解压出请求得到的
             * solution文件之后，会解析出来对应的文件的url，会自动请求网络加载数据
             * */
            this.f = this.d.getResponseCode();
            LogUtil.d("response code = " + f);
            if (f < 301 || f > 305) {
                if (f != 200) {
                    throw new RuntimeException("response code is unnormal = " + f + " != 200");
                }
                String tag = f();
                LogUtil.e("content-type = "+tag);
            }
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.exception("request http ", e);
        }
        return v0_11;
    }

    public void closeHttp() {
        if (d != null) {
            d.disconnect();
            d = null;
            LogUtil.e("close httpConn");
        }
    }
    /**
     * 获取网络返回数据
     */
    public final int a(AtomicReference arg6) {
        byte[] v1_1;
        int v0 = 0;
        if (this.d != null) {
            int v1 = this.f == 200 || this.f == 206 ? 1 : 0;
            if (v1 == 0) {
                v0 = -4000;
            }
            try {

                v1_1 = HttpUtils.a(this.d.getInputStream());

            } catch (Exception v0_1) {
                throw new IllegalArgumentException("-4002, get response exception : " + v0_1.getMessage());
            }
            arg6.set(v1_1);
        } else {
            v0 = -4000;
        }
        return v0;
    }
    /**
     * 解析网络反馈回来的数据成byte[]
     */
    private static byte[] a(InputStream arg6) {
        int v3;
        int v5 = -56;
        BufferedInputStream v0 = new BufferedInputStream(arg6);
        byte[] v1 = new byte[2048];
        ByteArrayOutputStream v2 = new ByteArrayOutputStream();
        try {
            while (true)
            {
                v3 = arg6.read(v1);
                if (v3 != -1)
                {
                    v2.write(v1, 0, v3);
                }
                else {
                    break;
                }
            }
            v1 = v2.toByteArray();
            v0.close();
            v2.close();
            LogUtil.e("deal response ok " + v1.length);
            return v1;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("deal with http response exception", e);
        }
        return v1;
    }


    /**
     * 跳转到 300  goto : 300
     */
    private int commo(boolean v2, int v7) {
        v2 = false;
        if (this.f != 0 && this.f != v7) {
            throw new IllegalArgumentException("response code is unnormal: " + this.f);
        }
        return this.f;
    }

    /**
     * 迭代请求的头部信息
     */
    private void a(Hashtable arg5) {
        if (arg5 != null && arg5.size() != 0 && this.d != null) {
            Iterator v2 = arg5.entrySet().iterator();
            while (v2.hasNext()) {
                Object v1 = v2.next();
                this.d.setRequestProperty("" + ((Map.Entry) v1).getKey(), "" + ((Map.Entry) v1).getValue());
            }
        }
    }

    public final String f() {
        try {
            return this.d.getHeaderField("Content-Type");
        } catch (Exception v0) {
            throw new IllegalArgumentException("-56, get content type: " + v0.getMessage());
        }
    }


    private String g() {
        try {
            return this.d.getHeaderField("Location");
        } catch (Exception v0) {
            throw new IllegalArgumentException("-56, get redirect url: " + v0.getMessage());
        }
    }


    /**
     * 设置请求的参数
     * data
     */
    public final void a(byte[] arg1) {
        this.e = arg1;
    }

    /**
     * 初始化HttpUrlConn
     * <p>
     * arg7是用来区别的，需要代理的作用
     */
    private  void a(URL arg6, byte arg7) {

        try {
            String v1 = null;

            if (-1 != arg7) {
                if (2 == arg7) {
                    setHttpProxy(v1, arg6);//设置网络的请求代理
                } else //不用设置网络的请求代理
                {
                    LogUtil.d("no proxy ");
                    d = (HttpURLConnection) arg6.openConnection();
                    h = false;
                }
                d.setReadTimeout(30000);
                d.setConnectTimeout(30000);
                LogUtil.d("http init ok --");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.exception("http init Ioexception ", e);
        } catch (SecurityException e) {
            e.printStackTrace();
            LogUtil.exception("http init SecurityException ", e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            LogUtil.exception("http init IllegalArgumentException ", e);
        }
    }

    private  void setHttpProxy(String v1, URL arg6) {
        LogUtil.d("setHttpProxy ");
        Proxy.Type v3 = Proxy.Type.HTTP;
        String v0_4 = android.net.Proxy.getHost(context);
        if (v0_4 == null || v0_4.length() == 0) {
            v1 = android.net.Proxy.getDefaultHost();
        } else if (v0_4 != null) {
            v1 = v0_4;
        } else {
            v1 = "";
        }

        int v0_5 = android.net.Proxy.getPort(context);
        if (v0_5 <= 0) {
            v0_5 = android.net.Proxy.getDefaultPort();
        }

        try {
            d = (HttpURLConnection) arg6.openConnection(new Proxy(v3, new InetSocketAddress(v1, v0_5)));
            h = true;
        } catch (IOException e1) {
            e1.printStackTrace();
            LogUtil.exception("setHttpProxy exception ", e1);
        }
    }

}
