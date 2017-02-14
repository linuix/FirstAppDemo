package http.demo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/2.
 */

public class OperatorB {
    protected Hashtable a;
    protected Hashtable b;
    protected String c;//编码格式
    private HashMap e;

    public OperatorB() {
        a = new Hashtable();
        b = new Hashtable();
        c = "GBK";
    }

    /**
     * 检测数据类型，选择添加数据类型
     */
    private static void a(ArrayList arg4, Object arg5) {
        Object v0 = arg5;
        while (true) {
            if (!v0.getClass().isArray()) {
                if (v0 instanceof Array) {
                    throw new IllegalArgumentException("can not support Array, please use List");
                } else if (v0 instanceof List) {
                    arg4.add("java.util.List");
                    if (((List) v0).size() > 0) {
                        v0 = ((List) v0).get(0);
                        continue;
                    } else {
                        arg4.add("?");
                    }
                } else if (v0 instanceof Map) {
                    arg4.add("java.util.Map");
                    if (((Map) v0).size() > 0) {
                        Object v1 = ((Map) v0).keySet().iterator().next();
                        v0 = ((Map) v0).get(v1);
                        arg4.add(v1.getClass().getName());
                        continue;
                    } else {
                        arg4.add("?");
                        arg4.add("?");
                    }
                } else {
                    arg4.add(v0.getClass().getName());
                }
            } else if (!v0.getClass().getComponentType().toString().equals("byte")) {
                throw new IllegalArgumentException("only byte[] is supported");
            } else if (Array.getLength(v0) > 0) {
                arg4.add("java.util.List");
                v0 = Array.get(v0, 0);
                continue;
            } else {
                break;
            }
            return;
        }
        arg4.add("Array");
        arg4.add("?");
    }

    /**
     *
     * 设置编码格式
     *
     * */
    public void a(String arg1)
    {
        this.c = arg1;

    }

    /**
     * 调用操作的参数，在上层的子类会直接调动，这里是主要的操作
     * */
    public void a(String arg5, Object arg6)
    {
        if (arg5 == null)
        {
            throw new IllegalArgumentException("ket == null");
        }
        if (arg6 == null)
        {
            throw  new IllegalArgumentException("value is null");
        }
        if (arg6 instanceof Set)
        {
            throw new IllegalArgumentException("not supported set");

        }
        //启用别的方式解决参数
    }

}































