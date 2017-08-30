package http.helper;

import com.demo.entity.JceStruct;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */

public class HelperC {

    protected String chart;
    private ByteBuffer byteBuffer;

    public HelperC() {
        this(128);//默认ByteBuffer的大小
    }

    public HelperC(int arg2) {
        super();
        this.chart = "GBK";
        this.byteBuffer = ByteBuffer.allocate(arg2);
    }

    public final int setChart(String arg2) {
        this.chart = arg2;
        return 0;
    }

    /**
     * 对象的处理函数，根据不同的对象，处理不同的数据格式
     *
     * */
    public final void addObjectData(Object arg6, int arg7) {
        byte v3 = 9;
        int v2 = 8;
        int v0;

        if ((arg6 instanceof Byte)) {
            this.addByteData(((Byte) arg6).byteValue(), arg7);//操作byte类型
        } else if ((arg6 instanceof Boolean)) {
            this.addBooleanData(((Boolean) arg6).booleanValue(), arg7);//操作boolen类型
        } else if ((arg6 instanceof Short)) {
            this.addShortData(((Short) arg6).shortValue(), arg7);//操作short类型
        } else if ((arg6 instanceof Integer)) {
            this.addIntData(((Integer) arg6).intValue(), arg7);//操作int类型
        } else if ((arg6 instanceof Long)) {
            this.addLongData(((Long) arg6).longValue(), arg7);//操作long类型
        } else if ((arg6 instanceof Float)) {
            this.addFloatData(((Float) arg6).floatValue(), arg7);//操作float类型
        } else if ((arg6 instanceof Double)) {
            this.addDoubleData(((Double) arg6).doubleValue(), arg7);//操作double类型
        }
        else if((arg6 instanceof String)) {
            this.addStringData(((String)arg6), arg7);//String 类型
        }
        else if((arg6 instanceof Map)) {
            this.addMapData(((Map)arg6), arg7);//Map类型
        }

        else if((arg6 instanceof List)) {
            this.addListData(((Collection)arg6), arg7);//List类型
        }
        else if((arg6 instanceof JceStruct)) {
            this.addJceStructData(((JceStruct)arg6), arg7);//JceStruct类型，这是一个数据来源的父类类型
        }
//数组类型
        else if((arg6 instanceof byte[])) {
            this.addByteData(((byte[])arg6), arg7);//byte[]类型
        }

        else if((arg6 instanceof boolean[])) {//boolean[]类型
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((boolean[])arg6).length, 0);
            v2 = ((boolean[])arg6).length;

            for(v0 = 0; v0 < v2; ++v0) {
                this.addBooleanData(((boolean[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof short[])) {
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((short[])arg6).length, 0);
            v2 = ((short[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addShortData(((short[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof int[])) {
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((int[])arg6).length, 0);
            v2 = ((int[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addIntData(((int[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof long[])) {
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((long[])arg6).length, 0);
            v2 = ((long[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addLongData(((long[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof float[])) {
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((float[])arg6).length, 0);
            v2 = ((float[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addFloatData(((float[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof double[])) {
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((double[])arg6).length, 0);
            v2 = ((double[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addDoubleData(((double[])arg6)[v0], 0);
            }
        }
        else if(arg6.getClass().isArray()) {
            this.addByteBuffer(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((Class<?>[])arg6).length, 0);
            v2 = ((Class<?>[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addObjectData(((Class<?>[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof Collection)) {
            this.addListData(((Collection)arg6), arg7);
        }
        else {
            throw new IllegalArgumentException("write object error: unsupport type. " + arg6.getClass());
        }
        return;
    }

    /**
     * 获取ByteBuffer
     * */
    public final ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }
/**
 * 获取ByteBuffer内的数据
 * */
    public final byte[] getBufferData() {
        byte[] v0 = new byte[this.byteBuffer.position()];
        System.arraycopy(this.byteBuffer.array(), 0, v0, 0, this.byteBuffer.position());
        return v0;
    }

    /**
 *
 *操作byte[]数组类型
 * */
    public final void addByteData(byte[] arg3, int arg4) {
        this.addByteBuffer(arg3.length + 8);
        this.addByteBufferData((byte)13, arg4);
        this.addByteBufferData((byte)0, 0);
        this.addIntData(arg3.length, 0);
        this.byteBuffer.put(arg3);
    }

    /**
     *
     * 操作JceStruct类型
     * */
    public final void addJceStructData(JceStruct arg3, int arg4) {
        this.addByteBuffer(2);
        this.addByteBufferData((byte)10, arg4);
        arg3.writeTo(this);
        this.addByteBuffer(2);
        this.addByteBufferData((byte)11, 0);
    }

/***
 *
 * 操作List类型
 *
 * */
    public final void addListData(Collection arg4, int arg5) {
        this.addByteBuffer(8);
        this.addByteBufferData((byte)9, arg5);
        int v0 = arg4 == null ? 0 : arg4.size();
        this.addIntData(v0, 0);
        if(arg4 != null) {
            Iterator v2 = arg4.iterator();
            while(v2.hasNext()) {
                this.addObjectData(v2.next(), 0);
            }
        }
    }


    /**
     *
     * 操作Map类型
     * **/
    public final void addMapData(Map arg5, int arg6) {
        this.addByteBuffer(8);
        this.addByteBufferData((byte)8, arg6);
        int v0 = arg5 == null ? 0 : arg5.size();
        this.addIntData(v0, 0);
        if(arg5 != null) {
            Iterator v2 = arg5.entrySet().iterator();
            while(v2.hasNext()) {
                Object v0_1 = v2.next();
                this.addObjectData(((Map.Entry)v0_1).getKey(), 0);
                this.addObjectData(((Map.Entry)v0_1).getValue(), 1);
            }
        }
    }




/**
 *
 * 操作String类型数据
 * */
    public final void addStringData(String arg4, int arg5) {
        byte[] v0_1;
        try {
            v0_1 = arg4.getBytes(this.chart);
        }
        catch(UnsupportedEncodingException v0) {
            v0_1 = arg4.getBytes();
        }

        this.addByteBuffer(v0_1.length + 10);
        if(v0_1.length > 255) {
            this.addByteBufferData((byte)7, arg5);
            this.byteBuffer.putInt(v0_1.length);
            this.byteBuffer.put(v0_1);
        }
        else {
            this.addByteBufferData((byte)6, arg5);
            this.byteBuffer.put(((byte)v0_1.length));
            this.byteBuffer.put(v0_1);
        }
    }




    /**
     * 操作double类型
     */
    public final void addDoubleData(double arg2, int arg4) {
        this.addByteBuffer(10);
        this.addByteBufferData((byte) 5, arg4);
        this.byteBuffer.putDouble(arg2);
    }

    /**
     * 操作float类型
     */
    private void addFloatData(float arg2, int arg3) {
        this.addByteBuffer(6);//工具1
        this.addByteBufferData((byte) 4, arg3);//工具2
        this.byteBuffer.putFloat(arg2);
    }

    /**
     * 操作long类型
     */
    public final void addLongData(long arg3, int arg5) {
        this.addByteBuffer(10);
        if (arg3 < -2147483648 || arg3 > 2147483647) {
            this.addByteBufferData((byte) 3, arg5);//工具2
            this.byteBuffer.putLong(arg3);
        } else {
            this.addIntData(((int) arg3), arg5);//int类型
        }
    }

    /**
     * 操作int类型
     */
    public final void addIntData(int arg2, int arg3) {
        this.addByteBuffer(6);
        if (arg2 < -32768 || arg2 > 32767) {
            this.addByteBufferData((byte) 2, arg3);//工具2
            this.byteBuffer.putInt(arg2);
        } else {
            this.addShortData(((short) arg2), arg3);//short类型
        }
    }

    /**
     * 操作short类型
     */
    public final void addShortData(short arg2, int arg3) {
        this.addByteBuffer(4);
        if (arg2 < -128 || arg2 > 127) {
            this.addByteBufferData((byte) 1, arg3);//工具2
            this.byteBuffer.putShort(arg2);
        } else {
            this.addByteData(((byte) arg2), arg3);
        }
    }


    /**
     * 操作boolean类型
     */
    private void addBooleanData(boolean arg2, int arg3) {
        int v0 = arg2 ? 1 : 0;
        this.addByteData(((byte) v0), arg3);//工具1
    }

    /**
     * 操作byte类型
     */
    public final void addByteData(byte arg2, int arg3) {
        this.addByteBuffer(3);//工具1
        if (arg2 == 0) {
            this.addByteBufferData((byte) 12, arg3);
        } else {
            this.addByteBufferData((byte) 0, arg3);//工具2
            this.byteBuffer.put(arg2);
        }
    }

    /**
     * 工具1
     * 如果缓冲区剩余容量小于 @arg5
     * 缓冲区扩容到原来容量+arg5的两倍
     */
    private void addByteBuffer(int arg5) {
        if (this.byteBuffer.remaining() < arg5) {
            ByteBuffer v0 = ByteBuffer.allocate((this.byteBuffer.capacity() + arg5) * 2);
            v0.put(this.byteBuffer.array(), 0, this.byteBuffer.position());
            this.byteBuffer = v0;
        }
    }

    /**
     * 工具2
     */
    private void addByteBufferData(byte arg4, int arg5) {
        if (arg5 < 15) {
            this.byteBuffer.put(((byte) (arg5 << 4 | arg4)));
        } else if (arg5 < 256) {
            this.byteBuffer.put(((byte) (arg4 | 240)));
            this.byteBuffer.put(((byte) arg5));
        } else {
            throw new IllegalArgumentException("tag is too large: " + arg5);
        }

        return;

    }
}
