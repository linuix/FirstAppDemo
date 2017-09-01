package http.helper;

import com.demo.utils.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OpertorC extends OperatorB {
    protected HashMap mapC;
    HelperA f;
    private HashMap g;

    public OpertorC() {
        super();
        this.mapC = null;
        this.g = new HashMap();
        this.f = new HelperA();
    }

    public void setChart(String arg1) {
        super.setChart(arg1);
    }

    public void setMapData(String key, Object value) {
        if(this.mapC == null) {
            super.a(key, value);
        }
        else if(key == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        else if(value == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        else if((value instanceof Set)) {
            throw new IllegalArgumentException("can not support Set");
        }
        else {
            HelperC helperC = new HelperC();
            helperC.setChart(this.chart);
            helperC.addObjectData(value, 0);
            this.mapC.put(key, Helper1.copyData(helperC.getByteBuffer()));
            LogUtil.d("htpp ready data " + mapC.size() + ", fileSize.size() = " + mapC.size());
        }
    }

    public void setData(byte[] arg5) {
        try {
            super.setData(arg5);
        }
        catch(Exception v0) {
            this.f.setBufferData(arg5);
            this.f.setChart(this.chart);
            HashMap v0_1 = new HashMap(1);
            v0_1.put("", new byte[0]);
            this.mapC = this.f.a(((Map)v0_1), 0, false);
        }
    }

    public byte[] a() {
        byte[] v0_1;
        if(this.mapC != null) {
            HelperC v0 = new HelperC(0);
            v0.setChart(this.chart);
            v0.addMapData(this.mapC, 0);
            v0_1 = Helper1.copyData(v0.getByteBuffer());
            LogUtil.d("get data cccc "+v0_1.length);
        }
        else {
            v0_1 = super.a();
            LogUtil.d("get data bbbb "+v0_1.length);
        }

        return v0_1;
    }

    public void b() {
        this.mapC = new HashMap();
    }

    public final Object getDataFromMap(String key, Object arg5) {
        LogUtil.d("operator chart:: getMarsrootSharePreferences()");
        Object obj = null;
        if(this.mapC != null) {
            LogUtil.d("operator chart:: getMarsrootSharePreferences()1");
            if(this.mapC.containsKey(key)) {
                obj = this.mapC.get(key);//数据在这里获取即可

                /*if(this.md5.containsKey(key)) {
                    obj = this.md5.get(key);
                }
                else {
                    LogUtil.type("operator chart:: getMarsrootSharePreferences()2");
                    obj = this.fileSize.get(key);
                    try {
                        this.getHead_Content_Type.sdk_gt18(((byte[])obj));//出现异常信息。这里直接关掉
                        this.getHead_Content_Type.sdk_gt18(this.chart);
                        obj = this.getHead_Content_Type.sdk_gt18(arg5, 0, true);
                        if(obj != null) {
                            this.chart(key, obj);
                        }
                    }
                    catch(Exception v0_1) {
                        v0_1.printStackTrace();
                        LogUtil.fileSize("Operator exception ");
                    }
                }*/
            }
        }
        else if(this.mapB.containsKey(key)) {
            LogUtil.d("operator chart:: getMarsrootSharePreferences()3");
            if(this.g.containsKey(key)) {
                obj = this.g.get(key);
            }
            else {
                obj = this.mapB.get(key);
                byte[] v1 = new byte[0];
                Iterator v0_2 = ((HashMap)obj).entrySet().iterator();
                if(v0_2.hasNext()) {
                    obj = v0_2.next();
                    ((Map.Entry)obj).getKey();
                    obj = ((Map.Entry)obj).getValue();
                }
                else {
                    byte[] v0_3 = v1;
                }

                try {
                    this.f.setBufferData(((byte[])obj));
                    this.f.setChart(this.chart);
                    obj = this.f.a(arg5, 0, true);
                    this.c(key, obj);
                }
                catch(Exception v0_1) {
                   v0_1.printStackTrace();
                    LogUtil.e("OperatorC exception 111111");
                }
            }
        }

        LogUtil.d("operator chart:: getMarsrootSharePreferences() end");
        return obj;
    }

    private void c(String arg2, Object arg3) {
        this.g.put(arg2, arg3);
    }
}

