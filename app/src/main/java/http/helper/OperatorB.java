package http.helper;


import com.demo.utils.LogUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/2.
 */

public class OperatorB {

    protected HashMap mapB;
    protected HashMap b;
    protected String chart;
    HelperA d;
    private HashMap e;

    OperatorB() {
        super();
        this.mapB = new HashMap();
        this.b = new HashMap();
        this.e = new HashMap();
        this.chart = "GBK";
        this.d = new HelperA();
    }

    private static void a(ArrayList arg4, Object arg5) {
        Object v0 = arg5;
        while(true) {
            if(!v0.getClass().isArray()) {
                if((v0 instanceof Array)) {
                    throw new IllegalArgumentException("can not support Array, please use List");
                }
                else if((v0 instanceof List)) {
                    arg4.add("java.util.List");
                    if(((List)v0).size() > 0) {
                        v0 = ((List)v0).get(0);
                        continue;
                    }
                    else {
                        arg4.add("?");
                    }
                }
                else if((v0 instanceof Map)) {
                    arg4.add("java.util.Map");
                    if(((Map)v0).size() > 0) {
                        Object v1 = ((Map)v0).keySet().iterator().next();
                        v0 = ((Map)v0).get(v1);
                        arg4.add(v1.getClass().getName());
                        continue;
                    }
                    else {
                        arg4.add("?");
                        arg4.add("?");
                    }
                }
                else {
                    arg4.add(v0.getClass().getName());
                }
            }
            else if(!v0.getClass().getComponentType().toString().equals("byte")) {
                throw new IllegalArgumentException("only byte[] is supported");
            }
            else if(Array.getLength(v0) > 0) {
                arg4.add("java.util.List");
                v0 = Array.get(v0, 0);
                continue;
            }
            else {
                break;
            }

            return;
        }

        arg4.add("Array");
        arg4.add("?");
    }

    public void setChart(String arg1) {
        this.chart = arg1;
    }

    public void a(String arg5, Object arg6) {
        if(arg5 == null) {
            throw new IllegalArgumentException("put key can not is null");
        }

        if(arg6 == null) {
            throw new IllegalArgumentException("put value can not is null");
        }

        if((arg6 instanceof Set)) {
            throw new IllegalArgumentException("can not support Set");
        }

        HelperC v0 = new HelperC();
        v0.setChart(this.chart);
        v0.addObjectData(arg6, 0);
        byte[] v0_1 = Helper1.copyData(v0.getByteBuffer());
        HashMap v1 = new HashMap(1);
        ArrayList v2 = new ArrayList(1);
        OperatorB.a(v2, arg6);
        v1.put(Checker.a(v2), v0_1);//
        this.e.remove(arg5);
        this.mapB.put(arg5, v1);
    }

    public void setData(byte[] arg6) {
        this.d.setBufferData(arg6);
        this.d.setChart(this.chart);
        HashMap v0 = new HashMap(1);
        HashMap v1 = new HashMap(1);
        v1.put("", new byte[0]);
        v0.put("", v1);
        this.mapB = this.d.a(((Map)v0), 0, false);
    }

    public byte[] a() {
        HelperC v0 = new HelperC(0);
        v0.setChart(this.chart);
        v0.addMapData(this.mapB, 0);
        LogUtil.d("bbbbb get data");
        return Helper1.copyData(v0.getByteBuffer());
    }


}
