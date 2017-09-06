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
    private String urlStr;
    private String type;
    private  HttpURLConnection httpURLConnection;
    private byte[] parameterData;
    private int responseCode;
    private Hashtable g;
    private static boolean h;
    private boolean i;
    private byte j;
    private byte k;

    private HttpUtils(Context arg3, String arg4) {
        super();
        this.type = "GET";
        this.responseCode = -1;
        this.g = new Hashtable(0);
        this.h = false;
        this.i = true;
        this.j = 0;
        this.k = 0;
        this.context = arg3;
        this.urlStr = arg4;
        connection(arg4);
    }

    private void connection(String  urlStr)
    {
        byte a1 = NetUtils.isConnection(HttpUtils.context);
        if (a1 != -1) {
            try {
                connectionOrProxy(new URL(urlStr), (byte) 0);//初始化httpUrlConn
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
        else if ( a1 == -1)
        {
            //没有网络连接，请检查网络
        }
    }

    public InputStream getInputStream()
    {
        try {
            if (httpURLConnection == null)
            {
                return  null;
            }
            return httpURLConnection.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    /**
     * respCode
     */
    public final int getRespCode() {
        return this.responseCode;
    }

    /**
     * content-length
     */
    public final long getContentLength() {
        long v0 = -1;
        String v2 = this.getHead("content-length");
        if (v2 != null) {
            try {
                v0 = Long.parseLong(v2.trim());
            } catch (Exception v0_1) {
                throw new IllegalArgumentException("-56, get header field: " + v0_1.getMessage());
            }
        }

        return v0;
    }

    private String getHead(String arg6) {
        try {
            return this.httpURLConnection.getHeaderField(arg6);
        } catch (Exception v0) {
            throw new IllegalArgumentException("-56, get header field: " + v0.getMessage());
        }
    }
    /**
     * getInstance
     *
     */
    public static HttpUtils getInstance(Context context, String url) {
        HttpUtils httpUtils = null;
        httpUtils = new HttpUtils(context, url);
        //初始化httpUrlConn的
        return httpUtils;
    }
    /**
     * 请求方式的设置
     * method
     */
    public final void setRequestType(String arg2) {
        this.type = arg2;
        if ("GET".equalsIgnoreCase(arg2)) {
            this.type = "GET";
        } else if ("POST".equalsIgnoreCase(arg2)) {
            this.type = "POST";
        }
    }
    /**
     * 请求网络
     */
    public final int execute() {
        int responseCode = 0;
        if (httpURLConnection == null){
            return -1;
        }
        try {
            while (true)
            {
                this.httpURLConnection.setRequestProperty("Cookie", "");
                this.httpURLConnection.setRequestProperty("Accept", "*/*");
                this.httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
                this.httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
                this.httpURLConnection.setInstanceFollowRedirects(true);
                System.setProperty("http.keepAlive", "false");
                if (!"GET".equalsIgnoreCase(this.type)) {
                    LogUtil.e("POST");
                    this.httpURLConnection.setRequestMethod("POST");
                    this.httpURLConnection.setDoOutput(true);
                    this.httpURLConnection.setDoInput(true);
                    this.httpURLConnection.setUseCaches(false);
                    if (this.parameterData != null) {
                        this.httpURLConnection.setRequestProperty("Content-length", "" + this.parameterData.length);
                        OutputStream outputStream = this.httpURLConnection.getOutputStream();
                        outputStream.write(this.parameterData);
                        outputStream.flush();
                        outputStream.close();
                        LogUtil.e("wite data over");
                    }
                }
                else if ("GET".equalsIgnoreCase(type))//GET方式
                {
                    LogUtil.e("GET");
                    LogUtil.e("准备求情第一次网络请求，charset ="+ type);
                    httpURLConnection.setRequestMethod("GET");
                    /**
                     *
                     * 调用g()-->httpURLConnection 重新指向响应反馈回来的连接，直接请求
                     * */
                    String redirectUrl = getHead_Location();
                    if(redirectUrl == null)
                    {
                         LogUtil.e("没有请求到下载地址 ，重定向url出现空值  ,但是，拿到了反馈回来的数据请求，准备写文件了 ！！");
                        this.responseCode = this.httpURLConnection.getResponseCode();
                        return 0;//直接写死这个值
                    }
                    LogUtil.d("redirect url =" + redirectUrl);
                    httpURLConnection.disconnect();
                    LogUtil.e("准备第二次网络请求，charset ="+ type);
                    connectionOrProxy(new URL(redirectUrl), NetUtils.isConnection(context));
                    setRequestType(type);//添加编码格式
                    execute();//发送网络请求
                }
                break;
            }

            /**
             * 除了请求时post的方法，现编 是采用的GET的方式请求，主要是针对的解压出请求得到的
             * solution文件之后，会解析出来对应的文件的url，会自动请求网络加载数据
             * */
            this.responseCode = this.httpURLConnection.getResponseCode();
            LogUtil.d("response code = " + this.responseCode);
            if (this.responseCode < 301 || this.responseCode > 305) {
                if (this.responseCode != 200) {
                    throw new RuntimeException("response code is unnormal = " + this.responseCode + " != 200");
                }
                String tag = getHead_Content_Type();
                LogUtil.e("content-type = "+tag);
            }
            return this.responseCode;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.exception("request http ", e);
        }
        return responseCode;
    }

    public void closeHttp() {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            httpURLConnection = null;
            LogUtil.e("close httpConn");
        }
    }
    /**
     * 获取网络返回数据
     */
    public final int getData(AtomicReference atomicReference) {
        byte[] data;
        int stateCode = 0;
        if (this.httpURLConnection != null) {
            int v1 = this.responseCode == 200 || this.responseCode == 206 ? 1 : 0;
            if (v1 == 0) {
                stateCode = -4000;
            }
            try {
                data = HttpUtils.getDataFromService(this.httpURLConnection.getInputStream());
            } catch (Exception v0_1) {
                throw new IllegalArgumentException("-4002, get response exception : " + v0_1.getMessage());
            }
            atomicReference.set(data);
        } else {
            stateCode = -4000;
        }
        return stateCode;
    }
    /**
     * 解析网络反馈回来的数据成byte[]
     */
    private static byte[] getDataFromService(InputStream inputStream) {
        int length;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] data = new byte[2048];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while (true)
            {
                length = inputStream.read(data);
                if (length != -1)
                {
                    byteArrayOutputStream.write(data, 0, length);
                }
                else {
                    break;
                }
            }
            data = byteArrayOutputStream.toByteArray();
            bufferedInputStream.close();
            byteArrayOutputStream.close();
            LogUtil.e("deal response ok " + data.length);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("deal with http response exception", e);
        }
        return data;
    }


    /**
     * 跳转到 300  goto : 300
     */
    private int commo(boolean v2, int v7) {
        v2 = false;
        if (this.responseCode != 0 && this.responseCode != v7) {
            throw new IllegalArgumentException("response code is unnormal: " + this.responseCode);
        }
        return this.responseCode;
    }

    /**
     * 迭代请求的头部信息
     */
    private void a(Hashtable arg5) {
        if (arg5 != null && arg5.size() != 0 && this.httpURLConnection != null) {
            Iterator v2 = arg5.entrySet().iterator();
            while (v2.hasNext()) {
                Object v1 = v2.next();
                this.httpURLConnection.setRequestProperty("" + ((Map.Entry) v1).getKey(), "" + ((Map.Entry) v1).getValue());
            }
        }
    }

    public final String getHead_Content_Type() {
        try {
            return this.httpURLConnection.getHeaderField("Content-Type");
        } catch (Exception v0) {
            throw new IllegalArgumentException("-56, get content type: " + v0.getMessage());
        }
    }


    private String getHead_Location() {
        try {
            return this.httpURLConnection.getHeaderField("Location");
        } catch (Exception v0) {
            throw new IllegalArgumentException("-56, get redirect url: " + v0.getMessage());
        }
    }


    /**
     * 设置请求的参数
     * data
     */
    public final void setParameter(byte[] parameter) {
        this.parameterData = parameter;
    }

    /**
     * 初始化HttpUrlConn
     * <p>
     * arg7是用来区别的，需要代理的作用
     */
    private  void connectionOrProxy(URL url, byte arg7) {

        try {
            String v1 = null;

            if (-1 != arg7) {
                if (2 == arg7) {
                    setHttpProxy(v1, url);//设置网络的请求代理
                } else //不用设置网络的请求代理
                {
                    LogUtil.d("no proxy ");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    h = false;
                }
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setConnectTimeout(30000);
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
            httpURLConnection = (HttpURLConnection) arg6.openConnection(new Proxy(v3, new InetSocketAddress(v1, v0_5)));
            h = true;
        } catch (IOException e1) {
            e1.printStackTrace();
            LogUtil.exception("setHttpProxy exception ", e1);
        }
    }

}
