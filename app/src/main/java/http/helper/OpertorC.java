package http.helper;

import com.demo.utils.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OpertorC extends OperatorB {
    protected HashMap e;
    HelperA f;
    private HashMap g;

    public OpertorC() {
        super();
        this.e = null;
        this.g = new HashMap();
        this.f = new HelperA();
    }

    public void setChart(String arg1) {
        super.setChart(arg1);
    }

    public void setMapData(String arg3, Object arg4) {
        if(this.e == null) {
            super.a(arg3, arg4);
        }
        else if(arg3 == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        else if(arg4 == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        else if((arg4 instanceof Set)) {
            throw new IllegalArgumentException("can not support Set");
        }
        else {
            HelperC helperC = new HelperC();
            helperC.setChart(this.chart);
            helperC.addObjectData(arg4, 0);
            this.e.put(arg3, Helper1.copyData(helperC.getByteBuffer()));
            LogUtil.d("htpp ready data " + e.size() + ", e.size() = " + e.size());
        }
    }

    public void a(byte[] arg5) {
        try {
            super.a(arg5);
        }
        catch(Exception v0) {
            this.f.a(arg5);
            this.f.a(this.chart);
            HashMap v0_1 = new HashMap(1);
            v0_1.put("", new byte[0]);
            this.e = this.f.a(((Map)v0_1), 0, false);
        }
    }

    public byte[] a() {
        byte[] v0_1;
        if(this.e != null) {
            HelperC v0 = new HelperC(0);
            v0.setChart(this.chart);
            v0.addMapData(this.e, 0);
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
        this.e = new HashMap();
    }

    public final Object b(String arg4, Object arg5) {
        LogUtil.d("operator chart:: getMarsrootSharePreferences()");
        Object v0 = null;
        if(this.e != null) {
            LogUtil.d("operator chart:: getMarsrootSharePreferences()1");
            if(this.e.containsKey(arg4)) {
                v0 = this.e.get(arg4);//数据在这里获取即可

                /*if(this.g.containsKey(arg4)) {
                    v0 = this.g.get(arg4);
                }
                else {
                    LogUtil.d("operator chart:: getMarsrootSharePreferences()2");
                    v0 = this.e.get(arg4);
                    try {
                        this.f.sdk_gt18(((byte[])v0));//出现异常信息。这里直接关掉
                        this.f.sdk_gt18(this.chart);
                        v0 = this.f.sdk_gt18(arg5, 0, true);
                        if(v0 != null) {
                            this.chart(arg4, v0);
                        }
                    }
                    catch(Exception v0_1) {
                        v0_1.printStackTrace();
                        LogUtil.e("Operator exception ");
                    }
                }*/
            }
        }
        else if(this.a.containsKey(arg4)) {
            LogUtil.d("operator chart:: getMarsrootSharePreferences()3");
            if(this.g.containsKey(arg4)) {
                v0 = this.g.get(arg4);
            }
            else {
                v0 = this.a.get(arg4);
                byte[] v1 = new byte[0];
                Iterator v0_2 = ((HashMap)v0).entrySet().iterator();
                if(v0_2.hasNext()) {
                    v0 = v0_2.next();
                    ((Map.Entry)v0).getKey();
                    v0 = ((Map.Entry)v0).getValue();
                }
                else {
                    byte[] v0_3 = v1;
                }

                try {
                    this.f.a(((byte[])v0));
                    this.f.a(this.chart);
                    v0 = this.f.a(arg5, 0, true);
                    this.c(arg4, v0);
                }
                catch(Exception v0_1) {
                   v0_1.printStackTrace();
                    LogUtil.e("OperatorC exception 111111");
                }
            }
        }

        LogUtil.d("operator chart:: getMarsrootSharePreferences() end");
        return v0;
    }

    private void c(String arg2, Object arg3) {
        this.g.put(arg2, arg3);
    }
}

