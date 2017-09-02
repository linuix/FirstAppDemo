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

/**
 * 初步猜测是一个加强型的buff
 */
public class HelperA {
    protected String charset;//charset
    private ByteBuffer buffer;

    public HelperA() {
        super();
        this.charset = "GBK";
    }

    public HelperA(byte[] data, byte arg4) {
        super();
        this.charset = "GBK";
        this.buffer = ByteBuffer.wrap(data);
        this.buffer.position(4);
        int limit = buffer.limit();
        int capacity = buffer.capacity();
        int position = buffer.position();
        LogUtil.d("1 bytebuffer limit = "+limit+" ,capacity = "+capacity+" ,position = "+position);
    }

    public HelperA(byte[] arg2)
    {
        super();
        this.charset = "GBK";
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


    public final void setBufferData(byte[] data) {
        if (this.buffer != null) {
            this.buffer.clear();
        }
        this.buffer = ByteBuffer.wrap(data);
    }

    public final int setChart(String charset) {
        this.charset = charset;
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
            if (this.getIntFromBuffer(arg11))
            {
                FuzhuB fuzhuB = new FuzhuB();
                this.readBuffer(fuzhuB);
                switch (fuzhuB.lowByte)
                {
                    case 8:
                        int v4 = this.getDataForBuffer(0, 0, true);
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
    public final Object a(Object obj, int arg6, boolean arg7) {
        boolean[] v0_13 = null;
        int v0 = 0;
        if ((obj instanceof Byte)) {
            Byte v0_1 = Byte.valueOf(this.getByteFromBuffer((byte) 0, arg6, arg7));//獲取byte類型
        } else if ((obj instanceof Boolean)) {
            Boolean v0_2 = Boolean.valueOf(this.getBooleanFromBuffer(arg6, arg7));//獲取boolean類型
        } else if ((obj instanceof Short)) {
            Short v0_3 = Short.valueOf(this.getShort((short) 0, arg6, arg7));//short类型
        } else if ((obj instanceof Integer)) {
            Integer v0_4 = Integer.valueOf(this.getDataForBuffer(0, arg6, arg7));
        } else if ((obj instanceof Long)) {
            Long v0_5 = Long.valueOf(this.getLongFromBuffer((long) 0, arg6, arg7));//获取long类型
        } else if ((obj instanceof Float)) {
            Float v0_6 = Float.valueOf(this.getFloatFromBuffer(0f, arg6, arg7));//获取long类型
        } else if ((obj instanceof Double)) {
            Double v0_7 = Double.valueOf(this.getDoubleFromBuffer((double) 0, arg6, arg7));//double類型
        } else if ((obj instanceof String)) {
            String v0_8 = String.valueOf(this.getStringFromBuffer(arg6, arg7));//获取String类型
        } else if ((obj instanceof Map)) {
            HashMap v0_9 = this.a(((Map) obj), arg6, arg7);//获取Map类型的数据
        } else if ((obj instanceof List)) {
            if (obj != null && !((List) obj).isEmpty()) {
                Object[] v2 = this.b(((List) obj).get(0), arg6, arg7);//处理不同数据对象的函数
                if (v2 == null) {
                    Object v0_10 = null;
                } else {
                    ArrayList arrayList = new ArrayList();
                    while (v0 < v2.length) {
                        arrayList.add(v2[v0]);
                        ++v0;
                    }
                }
                return v0_13;
            }
        } else {
            if ((obj instanceof JceStruct)) {
                JceStruct v0_12 = this.getJceStructFromBuffer(((JceStruct) obj), arg6, arg7);//获取JceStruct类型
                return v0_13;
            }
            if (!obj.getClass().isArray()) {
                throw new IllegalArgumentException("read object error: unsupport type.");
            }
            if (!(obj instanceof byte[]) && !(obj instanceof Byte[])) {
                if ((obj instanceof boolean[])) {
                    v0_13 = this.getBooleansFromBuffer(arg6, arg7);//获取booelan[]
                } else if ((obj instanceof short[])) {
                    short[] v0_14 = this.e(arg6, arg7);//获取short[]
                } else if ((obj instanceof int[])) {
                    int[] v0_15 = this.f(arg6, arg7);
                } else if ((obj instanceof long[])) {
                    long[] v0_16 = this.g(arg6, arg7);
                } else if ((obj instanceof float[])) {
                    float[] v0_17 = this.h(arg6, arg7);
                } else if ((obj instanceof double[])) {
                    double[] v0_18 = this.i(arg6, arg7);
                } else {
                    Object[] v0_19 = this.a(((Object[]) obj), arg6, arg7);//对象数组
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
        byte[] data = null;
        if (this.getIntFromBuffer(arg7)) {
            FuzhuB fuzhuB = new FuzhuB();
            this.readBuffer(fuzhuB);
            switch (fuzhuB.lowByte) {
                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        data = new byte[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                data[v1] = this.getByteFromBuffer(data[0], 0, true);
                                ++v1;
                                continue;
                            }
                            return data;
                        }
                    }
                case 13:
                    FuzhuB fuzhuB1 = new FuzhuB();
                    this.readBuffer(fuzhuB1);
                    if (fuzhuB1.lowByte != 0) {
                        throw new IllegalArgumentException("type mismatch, tag: " + arg7 + ", type: " + fuzhuB.lowByte
                                + ", " + fuzhuB1.lowByte);
                    } else {
                        int v2 = this.getDataForBuffer(0, 0, true);

                        if (v2 < 0) {
                            throw new IllegalArgumentException("invalid size, tag: " + arg7 + ", type: " + fuzhuB
                                    .lowByte + ", " + fuzhuB1.lowByte + ", size: " + v2);
                        } else {
                            data = new byte[v2];
                            this.buffer.get(data);
                        }
                    }
            }

        } else if (arg8) {
            throw new IllegalArgumentException("require field not exist.");

        }
        return data;
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
        if (this.getIntFromBuffer(arg8)) {
            FuzhuB v0_1 = new FuzhuB();
            this.readBuffer(v0_1);
            switch (v0_1.lowByte) {

                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new double[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.getDoubleFromBuffer(v0[0], 0, true);
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

        if (this.getIntFromBuffer(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.readBuffer(v0_1);

            switch (v0_1.lowByte) {

                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new float[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.getFloatFromBuffer(v0[0], 0, true);
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
        if (this.getIntFromBuffer(arg8)) {
            FuzhuB v0_1 = new FuzhuB();
            this.readBuffer(v0_1);
            switch (v0_1.lowByte) {
                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new long[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.getLongFromBuffer(v0[0], 0, true);
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

        if (this.getIntFromBuffer(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.readBuffer(v0_1);

            switch (v0_1.lowByte) {

                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new int[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.getDataForBuffer(v0[0], 0, true);
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

        if (this.getIntFromBuffer(arg7)) {
            FuzhuB v0_1 = new FuzhuB();
            this.readBuffer(v0_1);
            switch (v0_1.lowByte) {
                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new short[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.getShort(v0[0], 0, true);
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
    private boolean[] getBooleansFromBuffer(int arg7, boolean arg8) {
        boolean[] v0 = null;
        if (this.getIntFromBuffer(arg7)) {
            FuzhuB fuzhuB = new FuzhuB();
            this.readBuffer(fuzhuB);
            switch (fuzhuB.lowByte) {
                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
                    if (v3 < 0) {
                        throw new IllegalArgumentException("size invalid: " + v3);
                    } else {
                        v0 = new boolean[v3];
                        int v1 = 0;
                        while (true) {
                            if (v1 < v3) {
                                v0[v1] = this.getBooleanFromBuffer(0, true);
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
        if (this.getIntFromBuffer(arg8)) {
            FuzhuB v0 = new FuzhuB();
            this.readBuffer(v0);
            switch (v0.lowByte) {
                case 9:
                    int v3 = this.getDataForBuffer(0, 0, true);
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
    public final JceStruct getJceStructFromBuffer(JceStruct arg4, int arg5, boolean arg6) {
        Object v0_2 = null;
        JceStruct v0 = null;
        if (this.getIntFromBuffer(arg5)) {

            try {
                v0_2 = arg4.getClass().newInstance();
            } catch (Exception v0_1) {
                v0_1.printStackTrace();
                LogUtil.e("HelperA deal with JceStruct exceptioni!!!");
            }

            FuzhuB v1 = new FuzhuB();
            this.readBuffer(v1);
            if (v1.lowByte != 10) {
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
    public final String getStringFromBuffer(int arg5, boolean arg6)

    {
        byte[] v1;
        String v0 = null;
        int index =0;
        if (!this.getIntFromBuffer(arg5))

        {
            if (arg6)
            {
                throw new IllegalArgumentException("require field not exist.");
            }

        }

        FuzhuB fuzhuB = new FuzhuB();
        this.readBuffer(fuzhuB);

        LogUtil.d("fuzhuB  sdk_gt18 = "+ charset);

        switch (fuzhuB.lowByte)
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

                    v0 = new String(v1, this.charset);
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
                        v0 = new String(v1, this.charset);
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
    public final double getDoubleFromBuffer(double arg3, int arg5, boolean arg6) {
        if (this.getIntFromBuffer(arg5)) {
            FuzhuB v0 = new FuzhuB();
            this.readBuffer(v0);
            switch (v0.lowByte) {
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

    private float getFloatFromBuffer(float arg3, int arg4, boolean arg5) {
        if (this.getIntFromBuffer(arg4)) {
            FuzhuB v0 = new FuzhuB();
            this.readBuffer(v0);
            switch (v0.lowByte) {
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
    public final long getLongFromBuffer(long arg3, int arg5, boolean arg6) {
        if (this.getIntFromBuffer(arg5)) {
            FuzhuB v0 = new FuzhuB();
            this.readBuffer(v0);
            switch (v0.lowByte) {
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
    public final short getShort(short version, int arg4, boolean arg5) {
        if (this.getIntFromBuffer(arg4)) {
            FuzhuB fuzhuB = new FuzhuB();
            this.readBuffer(fuzhuB);
            switch (fuzhuB.lowByte)
            {
                case 0: {
                    version = ((short) this.buffer.get());
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
        return version;
    }

    /**
     * 获取boolean类型
     */
    private boolean getBooleanFromBuffer(int arg3, boolean arg4) {
        boolean v0 = false;
        if (this.getByteFromBuffer((byte) 0, arg3, arg4) != 0) {//调用获取byte类型
            v0 = true;
        }

        return v0;
    }

    /**
     * 獲取byte類型
     */
    public final byte getByteFromBuffer(byte arg3, int arg4, boolean arg5) {
        if (this.getIntFromBuffer(arg4)) {
            FuzhuB v0 = new FuzhuB();
            this.readBuffer(v0);
            switch (v0.lowByte) {
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
    private boolean getIntFromBuffer(int arg6) {
        boolean v0 = false;
        try {
            FuzhuB fuzhuB = new FuzhuB();
            while (true) {
                int v2 = HelperA.readBufferToFuzhuB(fuzhuB, this.buffer.duplicate());
                if (arg6 > fuzhuB.highByte && fuzhuB.lowByte != 11) {
                    this.setPosition(v2);//参数类型是int
                    this.setPositionByArg(fuzhuB.lowByte);//参数类型是byte
                    continue;
                }

                break;
            }

            if (arg6 != fuzhuB.highByte) {
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
    private void setPositionByArg(byte arg5) {
        int v2 = 8;
        int v1 = 4;
        int v0 = 0;
        switch (arg5) {
            case 0: {
                this.setPosition(1);
                return;
            }
            case 1: {
                this.setPosition(2);
                return;
            }
            case 2: {
                this.setPosition(v1);
                return;
            }
            case 3: {
                this.setPosition(v2);
                return;
            }
            case 4: {
                this.setPosition(v1);
                return;
            }
            case 5: {
                this.setPosition(v2);
                return;
            }
            case 6: {
                v0 = this.buffer.get();
                if (v0 < 0) {
                    v0 += 256;
                }

                this.setPosition(v0);
                return;
            }
            case 7: {
                this.setPosition(this.buffer.getInt());
                return;
            }
            case 8: {
                v1 = this.getDataForBuffer(0, 0, true);//工具2
                while (v0 < v1 * 2) {
                    this.setPositionByBufferData();//操作FuzhuB
                    ++v0;
                }
            }
            case 9: {
                v1 = this.getDataForBuffer(0, 0, true);//工具2
                while (true) {
                    if (v0 >= v1) {
                        return;
                    }

                    this.setPositionByBufferData();
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
                this.readBuffer(v1_1);
                if (v1_1.lowByte != 0) {
                    throw new IllegalArgumentException("skipField with invalid type, type value: " + arg5 + ", " +
                            v1_1.lowByte);
                }
                this.setPosition(this.getDataForBuffer(0, 0, true));
                return;
            }
        }
    }

    private void a() {
        FuzhuB v0 = new FuzhuB();
        do {
            this.readBuffer(v0);
            this.setPositionByArg(v0.lowByte);
        }
        while (v0.lowByte != 11);
    }

    /**
     * 工具2
     */
    public final int getDataForBuffer(int arg3, int arg4, boolean arg5) {
        if (this.getIntFromBuffer(arg4)) {
            FuzhuB fuzhuB = new FuzhuB();
            readBuffer(fuzhuB);
            switch (fuzhuB.lowByte) {
                case 0:
                    arg3 = this.buffer.get();
                    LogUtil.d(" index= "+fuzhuB.lowByte +"return int ="+arg3);
                    break;
                case 1:
                    LogUtil.d(" index= "+fuzhuB.lowByte +"return short");
                    return this.buffer.getShort();
                case 2:
                    LogUtil.d(" index= "+fuzhuB.lowByte +"return int");
                    return this.buffer.getInt();
                case 12:
                    return 0;
            }

        } else if (arg5) {
            throw new IllegalArgumentException("require field not exist.");
        }
        return arg3;
    }

    private void setPositionByBufferData() {
        FuzhuB fuzhuB = new FuzhuB();
        this.readBuffer(fuzhuB);
        this.setPositionByArg(fuzhuB.lowByte);//这里是调用的byte类型的函数
    }

    //操作FuzhuB
    private void readBuffer(FuzhuB fuzhuB) {
        HelperA.readBufferToFuzhuB(fuzhuB, this.buffer);//操作FuzhuB
    }

    /**
     * 操作FuzhuB
     */
    private static int readBufferToFuzhuB(FuzhuB fuzhuB, ByteBuffer byteBuffer) {
        LogUtil.d("此时的position 是："+byteBuffer.position());//这里出现了异常信息
        int v0 = byteBuffer.get();
        LogUtil.d("moved 此时的position 是："+byteBuffer.position()+" ,get() = "+v0);
        fuzhuB.lowByte = ((byte) (v0 & 15));//
        fuzhuB.highByte = (v0 & 240) >> 4;//
        LogUtil.d("fuzhuB sdk_gt18 ="+fuzhuB.lowByte +" ,fuzhuB getMarsrootSharePreferences ="+fuzhuB.highByte);
        if (fuzhuB.highByte == 15)
        {
            fuzhuB.highByte = byteBuffer.get();
            v0 = 2;
        }else{
            v0 = 1;
        }
        return v0;
    }

    /**
     * 设置ByteBuffer参数
     */
    private void setPosition(int position)
    {
        this.buffer.position(this.buffer.position() + position);
    }


}
