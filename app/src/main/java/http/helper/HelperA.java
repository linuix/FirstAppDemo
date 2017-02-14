package http.helper;


import com.demo.entity.JceStruct;
import com.demo.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */

public class HelperA {
    protected String a;//charset
    private ByteBuffer buffer;

    public HelperA() {
        super();
        this.a = "GBK";
    }

    public HelperA(byte[] arg3, byte arg4) {
        super();
        this.a = "GBK";
        this.buffer = ByteBuffer.wrap(arg3);
            this.buffer.position(4);
        int limit = buffer.limit();
        int capacity = buffer.capacity();
        int position = buffer.position();
        LogUtil.d("1 bytebuffer limit = "+limit+" ,capacity = "+capacity+" ,position = "+position);
    }

    public HelperA(byte[] arg2)
    {
        super();
        this.a = "GBK";
        //below phrase was tested
        this.buffer = ByteBuffer.wrap(arg2);
        int capacity = buffer.capacity();
        int position = buffer.position();
        int limit = buffer.limit();
        LogUtil.d("2 bytebuffer limit = "+limit+" ,capacity = "+capacity+" ,position = "+position);

    }
//test
    /**
     * 这里测试出包含的数据
     * */
//    public void test()
//    {
//
//        Buffer flip = buffer.flip();
//        byte buffer = this.buffer.get();
//        Object array = flip.array();
//        this.buffer.clear();
//    }
    //


    public final void a(byte[] arg2) {
        if (this.buffer != null) {
            this.buffer.clear();
        }

        this.buffer = ByteBuffer.wrap(arg2);
    }

    public final int a(String arg2) {
        this.a = arg2;
        return 0;
    }

    public final HashMap a(Map arg2, int arg3, boolean arg4) {
        return (HashMap) this.a(new HashMap(), arg2, arg3, arg4);
    }

    private Map a(Map arg9, Map arg10, int arg11, boolean arg12) {
        HashMap v9 = null;
        if (arg10 == null) {
            v9 = new HashMap();
        } else if (!arg10.isEmpty()) {
            Object v0 = arg10.entrySet().iterator().next();
            Object v2 = ((Map.Entry) v0).getKey();
            Object v3 = ((Map.Entry) v0).getValue();
            if (this.b(arg11))
            {
                FuzhuB fuzhuB = new FuzhuB();
                this.a(fuzhuB);
                switch (fuzhuB.a)
                {
                    case 8:
                        int v4 = this.a(0, 0, true);
                        if (v4 < 0)
                        {
                            throw new IllegalArgumentException("size invalid: " + v4);
                        }

                        else
                        {
                            int v0_2 = 0;
                            while (true)
                            {
                                if (v0_2 < v4)
                                {
                                    arg9.put(this.a(v2, 0, true), this.a(v3, 1, true));//调用操作Object函数
                                    ++v0_2;
                                    continue;
                                }

                                return ((Map) v9);
                            }
                        }

                    default:
                        throw new IllegalArgumentException("type mismatch.");
                }
            } else {
                if (arg12) {
                    throw new IllegalArgumentException("trequire field not exist.");
                }

            }

        } else {
            v9 = new HashMap();
        }

        return v9;
    }

    /**
     * 操作Object類型
     */
    public final Object a(Object arg5, int arg6, boolean arg7) {
        boolean[] v0_13 = null;
        ArrayList v0_11;
        int v0 = 0;
        if ((arg5 instanceof Byte)) {
            Byte v0_1 = Byte.valueOf(this.a((byte) 0, arg6, arg7));//獲取byte類型
        } else if ((arg5 instanceof Boolean)) {
            Boolean v0_2 = Boolean.valueOf(this.c(arg6, arg7));//獲取boolean類型
        } else if ((arg5 instanceof Short)) {
            Short v0_3 = Short.valueOf(this.a((short) 0, arg6, arg7));//short类型
        } else if ((arg5 instanceof Integer)) {
            Integer v0_4 = Integer.valueOf(this.a(0, arg6, arg7));
        } else if ((arg5 instanceof Long)) {
            Long v0_5 = Long.valueOf(this.a((long) 0, arg6, arg7));//获取long类型
        } else if ((arg5 instanceof Float)) {
            Float v0_6 = Float.valueOf(this.a(0f, arg6, arg7));//获取long类型
        } else if ((arg5 instanceof Double)) {
            Double v0_7 = Double.valueOf(this.a((double) 0, arg6, arg7));//double類型
        } else if ((arg5 instanceof String)) {
            String v0_8 = String.valueOf(this.a(arg6, arg7));//获取String类型
        } else if ((arg5 instanceof Map)) {
            HashMap v0_9 = this.a(((Map) arg5), arg6, arg7);//获取Map类型的数据
        }

        /******************************************************/
        else if ((arg5 instanceof List)) {
            if (arg5 != null && !((List) arg5).isEmpty()) {
                Object[] v2 = this.b(((List) arg5).get(0), arg6, arg7);//处理不同数据对象的函数
                if (v2 == null) {
                    Object v0_10 = null;
                } else {
                    ArrayList v1 = new ArrayList();
                    while (v0 < v2.length) {
                        v1.add(v2[v0]);
                        ++v0;
                    }
                    v0_11 = v1;
                }
                return v0_13;
            }
            v0_11 = new ArrayList();
        } else {
            if ((arg5 instanceof JceStruct)) {
                JceStruct v0_12 = this.a(((JceStruct) arg5), arg6, arg7);//获取JceStruct类型
                return v0_13;
            }
            if (!arg5.getClass().isArray()) {
                throw new IllegalArgumentException("read object error: unsupport type.");
            }
            if (!(arg5 instanceof byte[]) && !(arg5 instanceof Byte[])) {
                if ((arg5 instanceof boolean[])) {
                    v0_13 = this.d(arg6, arg7);//获取booelan[]
                } else if ((arg5 instanceof short[])) {
                    short[] v0_14 = this.e(arg6, arg7);//获取short[]
                } else if ((arg5 instanceof int[])) {
                    int[] v0_15 = this.f(arg6, arg7);
                } else if ((arg5 instanceof long[])) {
                    long[] v0_16 = this.g(arg6, arg7);
                } else if ((arg5 instanceof float[])) {
                    float[] v0_17 = this.h(arg6, arg7);
                } else if ((arg5 instanceof double[])) {
                    double[] v0_18 = this.i(arg6, arg7);
                } else {
                    Object[] v0_19 = this.a(((Object[]) arg5), arg6, arg7);//对象数组
                }
                return v0_13;
            }

            byte[] v0_20 = this.b(arg6, arg7);
        }
        return v0_13;

    }


    /**
     * byte[]
     */
    public final byte[] b(int arg7, boolean arg8) {
        byte[] v0 = null;
        if (this.b(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);
            switch (v0_1.a) {
                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new byte[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.a(v0[0], 0, true);
                                ++v1;
                                continue;
                            }
                            return v0;
                        }
                    }
                case 13:
                    FuzhuB v1_1 = new FuzhuB();
                    this.a(v1_1);
                    if (v1_1.a != 0) {
                        throw new IllegalArgumentException("type mismatch, tag: " + arg7 + ", type: " + v0_1.a
                                + ", " + v1_1.a);
                    } else {
                        int v2 = this.a(0, 0, true);

                        if (v2 < 0) {
                            throw new IllegalArgumentException("invalid size, tag: " + arg7 + ", type: " + v0_1
                                    .a + ", " + v1_1.a + ", size: " + v2);
                        } else {
                            v0 = new byte[v2];
                            this.buffer.get(v0);
                        }
                    }
            }

        } else if (arg8) {
            throw new IllegalArgumentException("require field not exist.");

        }
        return v0;
    }

    /**
     * 对象数组
     * Object[]
     */
    private Object[] a(Object[] arg3, int arg4, boolean arg5)
    {
        if (arg3 != null && arg3.length != 0) {
            return this.b(arg3[0], arg4, arg5);
        }

        throw new IllegalArgumentException("unable to get type of key and value.");
    }

    /**
     * double[]
     */
    private double[] i(int arg8, boolean arg9)
    {
        double[] v0 = null;
        if (this.b(arg8)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);
            switch (v0_1.a) {

                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new double[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.a(v0[0], 0, true);
                                ++v1;
                                continue;
                            }
                            return v0;
                        }
                    }


                default:
                    throw new IllegalArgumentException("type mismatch.");

            }
        } else {
            if (arg9) {
                throw new IllegalArgumentException("require field not exist.");
            }
        }
        return v0;
    }

    /**
     * float[]
     */
    private float[] h(int arg7, boolean arg8) {
        float[] v0 = null;

        if (this.b(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);

            switch (v0_1.a) {

                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new float[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.a(v0[0], 0, true);
                                ++v1;
                                continue;
                            }

                            return v0;
                        }

                    }
                default:
                    throw new IllegalArgumentException("type mismatch.");
            }
        } else {
            if (arg8) {
                throw new IllegalArgumentException("require field not exist.");
            }

        }

        return v0;
    }

    /**
     * long[]
     */
    private long[] g(int arg8, boolean arg9) {
        long[] v0 = null;
        if (this.b(arg8)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);
            switch (v0_1.a) {
                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new long[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.a(v0[0], 0, true);
                                ++v1;
                                continue;
                            }

                            return v0;
                        }

                    }
                default:
                    throw new IllegalArgumentException("type mismatch.");
            }
        } else {
            if (arg9) {
                throw new IllegalArgumentException("require field not exist.");
            }

        }
        return v0;
    }


    /**
     * int[]
     */

    private int[] f(int arg7, boolean arg8) {
        int[] v0 = null;

        if (this.b(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);

            switch (v0_1.a) {

                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new int[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.a(v0[0], 0, true);
                                ++v1;
                                continue;
                            }
                            return v0;
                        }
                    }
                default:
                    throw new IllegalArgumentException("type mismatch.");
            }
        } else {
            if (arg8) {
                throw new IllegalArgumentException("require field not exist.");
            }

        }

        return v0;

    }

    /**
     * 获取short[]
     */
    private short[] e(int arg7, boolean arg8) {
        short[] v0 = null;

        if (this.b(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);
            switch (v0_1.a) {
                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new short[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.a(v0[0], 0, true);
                                ++v1;
                                continue;
                            }

                            return v0;
                        }
                    }
                default:
                    throw new IllegalArgumentException("type mismatch.");
            }
        } else {
            if (arg8) {
                throw new IllegalArgumentException("require field not exist.");
            }
        }

        return v0;

    }


    /**
     * 获取boolean[]
     */
    private boolean[] d(int arg7, boolean arg8) {
        boolean[] v0 = null;
        if (this.b(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.a(v0_1);
            switch (v0_1.a) {
                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {

                        v0 = new boolean[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.c(0, true);
                                ++v1;
                                continue;
                            }

                            return v0;
                        }
                    }
                default:
                    throw new IllegalArgumentException("type mismatch.");

            }
        } else {
            if (arg8) {
                throw new IllegalArgumentException("require field not exist.");
            }
        }
        return v0;

    }


    /**
     * 处理不同数据对象的函数
     */
    private Object[] b(Object arg7, int arg8, boolean arg9) {
        Object v0_1[] = new Object[0];
        if (this.b(arg8)) {
            FuzhuB v0 = new FuzhuB();
            this.a(v0);
            switch (v0.a) {
                case 9:
                    int v3 = this.a(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0_1 = (Object[]) Array.newInstance(arg7.getClass(), v3);
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0_1[v1] = this.a(arg7, 0, true);
                                ++v1;
                                continue;
                            }

                            return (v0_1);
                        }
                    }
                default: {
                    throw new IllegalArgumentException("type mismatch.");
                }
            }

        } else {
            if (arg9) {
                throw new IllegalArgumentException("require field not exist.");
            } else {
                Object[] v0_2 = null;
                v0_1 = v0_2;
            }
        }
        return v0_1;


    }


    /**
     * 获取JceStruct类型
     **/
    public final JceStruct a(JceStruct arg4, int arg5, boolean arg6) {
        Object v0_2 = null;
        JceStruct v0 = null;
        if (this.b(arg5)) {

            try {
                v0_2 = arg4.getClass().newInstance();
            } catch (Exception v0_1) {
                v0_1.printStackTrace();
                LogUtil.e("HelperA deal with JceStruct exceptioni!!!");
            }

            FuzhuB v1 = new FuzhuB();
            this.a(v1);
            if (v1.a != 10) {
                throw new IllegalArgumentException("type mismatch.");
            } else {
                ((JceStruct) v0_2).readFrom(this);
                this.a();
            }

        } else if (arg6) {
            throw new IllegalArgumentException("require field not exist.");
        }
        return v0;

    }


    /**
     * 获取String 类型数据集
     */
    public final String a(int arg5, boolean arg6)

    {
        byte[] v1;
        String v0 = null;
        int index =0;
        if (!this.b(arg5))

        {
            if (arg6)
            {
                throw new IllegalArgumentException("require field not exist.");
            }

        }

        FuzhuB v0_1 = new FuzhuB();
        this.a(v0_1);

        LogUtil.d("fuzhuB  sdk_gt18 = "+a);

        switch (v0_1.a)
        {
            case 6:
            {
                index = this.buffer.get();
                LogUtil.d("6 geStr index ="+index);

                if (index < 0)
                {
                    index += 256;
                }
                v1 = new byte[index];
                this.buffer.get(v1);
                try
                {

                    v0 = new String(v1, this.a);
                    LogUtil.e("返回的字符串是: "+v0);

                } catch (UnsupportedEncodingException v0_3)
                {
                    v0 = new String(v1);
                }

                return v0;
            }
            case 7: {
                index = this.buffer.getInt();
                LogUtil.d("7 geStr index ="+index);
                if (index <= 104857600 && index >= 0) {
                    v1 = new byte[index];
                    this.buffer.get(v1);
                    try {
                        v0 = new String(v1, this.a);
                    } catch (UnsupportedEncodingException v0_3) {
                        v0 = new String(v1);
                    }

                    return v0;
                }

                throw new IllegalArgumentException("String too long: " + index);
            }
        }

        throw new IllegalArgumentException("type mismatch.");
    }

    /**
     * 獲取double 類型
     */
    public final double a(double arg3, int arg5, boolean arg6) {
        if (this.b(arg5)) {
            FuzhuB v0 = new FuzhuB();
            this.a(v0);
            switch (v0.a) {
                case 4: {
                    arg3 = ((double) this.buffer.getFloat());
                    break;
                }

                case 5: {
                    return this.buffer.getDouble();
                }
                case 12: {
                    return 0;
                }
            }

            throw new IllegalArgumentException("type mismatch.");
        } else if (arg6) {
            throw new IllegalArgumentException("require type is not exist");
        }
        return arg3;
    }

    /**
     * 获取long类型
     */

    private float a(float arg3, int arg4, boolean arg5) {
        if (this.b(arg4)) {
            FuzhuB v0 = new FuzhuB();
            this.a(v0);
            switch (v0.a) {
                case 4: {
                    arg3 = this.buffer.getFloat();
                    break;
                }
                case 12: {
                    return 0f;
                }
            }
        } else if (arg5) {
            throw new IllegalArgumentException("require type is not exist");
        }
        return arg3;
    }

    /**
     * 获取long类型
     */
    public final long a(long arg3, int arg5, boolean arg6) {
        if (this.b(arg5)) {
            FuzhuB v0 = new FuzhuB();
            this.a(v0);
            switch (v0.a) {
                case 0: {
                    arg3 = ((long) this.buffer.get());
                    break;
                }
                case 1: {
                    return ((long) this.buffer.getShort());
                }
                case 2: {
                    return ((long) this.buffer.getInt());
                }
                case 3: {
                    return this.buffer.getLong();
                }
                case 12: {
                    return 0;
                }
            }
        } else if (arg6) {
            throw new IllegalArgumentException("require typt is not exist");
        }
        return arg3;
    }

    /**
     * 获取 short类型
     */
    public final short a(short arg3, int arg4, boolean arg5) {
        if (this.b(arg4)) {
            FuzhuB v0 = new FuzhuB();
            this.a(v0);
            switch (v0.a)
            {
                case 0: {
                    arg3 = ((short) this.buffer.get());
                    break;
                }
                case 1: {
                    return this.buffer.getShort();
                }
                case 12: {
                    return 0;
                }
            }


        } else if (arg5) {
            throw new RuntimeException("require field not exist");
        }
        return arg3;
    }

    /**
     * 获取boolean类型
     */
    private boolean c(int arg3, boolean arg4) {
        boolean v0 = false;
        if (this.a((byte) 0, arg3, arg4) != 0) {//调用获取byte类型
            v0 = true;
        }

        return v0;
    }

    /**
     * 獲取byte類型
     */
    public final byte a(byte arg3, int arg4, boolean arg5) {
        if (this.b(arg4)) {
            FuzhuB v0 = new FuzhuB();
            this.a(v0);
            switch (v0.a) {
                case 0: {
                    arg3 = this.buffer.get();
                    LogUtil.e("ret byte = "+ String.valueOf(arg3));
                    return arg3;
                }
                case 12: {
                    return 0;
                }
            }
        } else if (arg5) {
            throw new IllegalArgumentException("require field not exist.");
        }
        return 0;
    }

    /**
     * 参数是int类型
     */
    private boolean b(int arg6) {
        boolean v0 = false;
        try {
            FuzhuB v1_2 = new FuzhuB();
            while (true) {
                int v2 = HelperA.a(v1_2, this.buffer.duplicate());
                if (arg6 > v1_2.b && v1_2.a != 11) {
                    this.a(v2);//参数类型是int
                    this.a(v1_2.a);//参数类型是byte
                    continue;
                }

                break;
            }

            if (arg6 != v1_2.b) {
                return v0;
            }

            return true;
        } catch (BufferUnderflowException v1) {
            return v0;
        }

    }

    /**
     * 参数是byte类型
     */
    private void a(byte arg5) {
        int v2 = 8;
        int v1 = 4;
        int v0 = 0;
        switch (arg5) {
            case 0: {
                this.a(1);
                return;
            }
            case 1: {
                this.a(2);
                return;
            }
            case 2: {
                this.a(v1);
                return;
            }
            case 3: {
                this.a(v2);
                return;
            }
            case 4: {
                this.a(v1);
                return;
            }
            case 5: {
                this.a(v2);
                return;
            }
            case 6: {
                v0 = this.buffer.get();
                if (v0 < 0) {
                    v0 += 256;
                }

                this.a(v0);
                return;
            }
            case 7: {
                this.a(this.buffer.getInt());
                return;
            }
            case 8: {
                v1 = this.a(0, 0, true);//工具2
                while (v0 < v1 * 2) {
                    this.b();//操作FuzhuB
                    ++v0;
                }
            }
            case 9: {
                v1 = this.a(0, 0, true);//工具2
                while (true) {
                    if (v0 >= v1) {
                        return;
                    }

                    this.b();
                    ++v0;
                }
            }
            case 10: {
                this.a();
                return;
            }
            case 11:
            case 12: {
                return;
            }
            case 13: {
                FuzhuB v1_1 = new FuzhuB();
                this.a(v1_1);
                if (v1_1.a != 0) {
                    throw new IllegalArgumentException("skipField with invalid type, type value: " + arg5 + ", " +
                            v1_1.a);
                }
                this.a(this.a(0, 0, true));
                return;
            }
        }
    }

    private void a() {
        FuzhuB v0 = new FuzhuB();
        do {
            this.a(v0);
            this.a(v0.a);
        }
        while (v0.a != 11);
    }

    /**
     * 工具2
     */
    public final int a(int arg3, int arg4, boolean arg5) {
        if (this.b(arg4)) {
            FuzhuB v0 = new FuzhuB();
            a(v0);
            switch (v0.a) {
                case 0:
                    arg3 = this.buffer.get();
                    LogUtil.d(" index= "+v0.a+"return int ="+arg3);
                    break;
                case 1:
                    LogUtil.d(" index= "+v0.a+"return short");
                    return this.buffer.getShort();
                case 2:
                    LogUtil.d(" index= "+v0.a+"return int");
                    return this.buffer.getInt();
                case 12:
                    return 0;
            }

        } else if (arg5) {
            throw new IllegalArgumentException("require field not exist.");
        }
        return arg3;
    }


    private void b() {
        FuzhuB v0 = new FuzhuB();
        this.a(v0);
        this.a(v0.a);//这里是调用的byte类型的函数
    }

    //操作FuzhuB
    private void a(FuzhuB arg2) {
        HelperA.a(arg2, this.buffer);//操作FuzhuB
    }

    /**
     * 操作FuzhuB
     */
    private static int a(FuzhuB fuzhuB, ByteBuffer arg3) {
        LogUtil.d("此时的position 是："+arg3.position());//这里出现了异常信息
        int v0 = arg3.get();
        LogUtil.d("moved 此时的position 是："+arg3.position()+" ,get() = "+v0);
        fuzhuB.a = ((byte) (v0 & 15));
        fuzhuB.b = (v0 & 240) >> 4;
        LogUtil.d("fuzhuB sdk_gt18 ="+fuzhuB.a+" ,fuzhuB b ="+fuzhuB.b);
        if (fuzhuB.b == 15)
        {
            fuzhuB.b = arg3.get();
            v0 = 2;
        }
        else
        {
            v0 = 1;
        }
        return v0;
    }

    /**
     * 设置ByteBuffer参数
     */
    private void a(int arg3)
    {

        this.buffer.position(this.buffer.position() + arg3);

    }


}
