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

/**
 * 网络请求前的数据容器
 */
public class HelperC {

    protected String chart;
    private ByteBuffer byteBuffer;

    public HelperC() {
        this(128);//默认ByteBuffer的大小
    }

    public HelperC(int size) {
        super();
        this.chart = "GBK";
        this.byteBuffer = ByteBuffer.allocate(size);
    }

    public final int setChart(String arg2) {
        this.chart = arg2;
        return 0;
    }

    /**
     * 对象的处理函数，根据不同的对象，处理不同的数据格式
     *
     * */
    public final void addObjectData(Object valueObj, int arg7) {
        byte v3 = 9;
        int v2 = 8;
        int v0;

        if ((valueObj instanceof Byte)) {
            this.addByteData(((Byte) valueObj).byteValue(), arg7);//操作byte类型
        } else if ((valueObj instanceof Boolean)) {
            this.addBooleanData(((Boolean) valueObj).booleanValue(), arg7);//操作boolen类型
        } else if ((valueObj instanceof Short)) {
            this.addShortData(((Short) valueObj).shortValue(), arg7);//操作short类型
        } else if ((valueObj instanceof Integer)) {
            this.addIntData(((Integer) valueObj).intValue(), arg7);//操作int类型
        } else if ((valueObj instanceof Long)) {
            this.addLongData(((Long) valueObj).longValue(), arg7);//操作long类型
        } else if ((valueObj instanceof Float)) {
            this.addFloatData(((Float) valueObj).floatValue(), arg7);//操作float类型
        } else if ((valueObj instanceof Double)) {
            this.addDoubleData(((Double) valueObj).doubleValue(), arg7);//操作double类型
        }
        else if((valueObj instanceof String)) {
            this.addStringData(((String)valueObj), arg7);//String 类型
        }
        else if((valueObj instanceof Map)) {
            this.addMapData(((Map)valueObj), arg7);//Map类型
        }

        else if((valueObj instanceof List)) {
            this.addListData(((Collection)valueObj), arg7);//List类型
        }
        else if((valueObj instanceof JceStruct)) {
            this.addJceStructData(((JceStruct)valueObj), arg7);//JceStruct类型，这是一个数据来源的父类类型
        }
//数组类型
        else if((valueObj instanceof byte[])) {
            this.addByteData(((byte[])valueObj), arg7);//byte[]类型
        }

        else if((valueObj instanceof boolean[])) {//boolean[]类型
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((boolean[])valueObj).length, 0);
            v2 = ((boolean[])valueObj).length;

            for(v0 = 0; v0 < v2; ++v0) {
                this.addBooleanData(((boolean[])valueObj)[v0], 0);
            }
        }
        else if((valueObj instanceof short[])) {
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((short[])valueObj).length, 0);
            v2 = ((short[])valueObj).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addShortData(((short[])valueObj)[v0], 0);
            }
        }
        else if((valueObj instanceof int[])) {
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((int[])valueObj).length, 0);
            v2 = ((int[])valueObj).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addIntData(((int[])valueObj)[v0], 0);
            }
        }
        else if((valueObj instanceof long[])) {
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((long[])valueObj).length, 0);
            v2 = ((long[])valueObj).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addLongData(((long[])valueObj)[v0], 0);
            }
        }
        else if((valueObj instanceof float[])) {
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((float[])valueObj).length, 0);
            v2 = ((float[])valueObj).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addFloatData(((float[])valueObj)[v0], 0);
            }
        }
        else if((valueObj instanceof double[])) {
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((double[])valueObj).length, 0);
            v2 = ((double[])valueObj).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addDoubleData(((double[])valueObj)[v0], 0);
            }
        }
        else if(valueObj.getClass().isArray()) {
            this.addByteBufferSize(v2);
            this.addByteBufferData(v3, arg7);
            this.addIntData(((Class<?>[])valueObj).length, 0);
            v2 = ((Class<?>[])valueObj).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.addObjectData(((Class<?>[])valueObj)[v0], 0);
            }
        }
        else if((valueObj instanceof Collection)) {
            this.addListData(((Collection)valueObj), arg7);
        }
        else {
            throw new IllegalArgumentException("inputCopyToOutput object error: unsupport type. " + valueObj.getClass());
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
        this.addByteBufferSize(arg3.length + 8);
        this.addByteBufferData((byte)13, arg4);
        this.addByteBufferData((byte)0, 0);
        this.addIntData(arg3.length, 0);
        this.byteBuffer.put(arg3);
    }

    /**
     *
     * 操作JceStruct类型
     * */
    public final void addJceStructData(JceStruct info, int arg4) {
        this.addByteBufferSize(2);
        this.addByteBufferData((byte)10, arg4);
        info.writeTo(this);
        this.addByteBufferSize(2);
        this.addByteBufferData((byte)11, 0);
    }

/***
 *
 * 操作List类型
 *
 * */
    public final void addListData(Collection arg4, int arg5) {
        this.addByteBufferSize(8);
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
    public final void addMapData(Map mapData, int length) {
        this.addByteBufferSize(8);
        this.addByteBufferData((byte)8, length);
        int v0 = mapData == null ? 0 : mapData.size();
        this.addIntData(v0, 0);
        if(mapData != null) {
            Iterator v2 = mapData.entrySet().iterator();
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
    public final void addStringData(String dataStr, int arg5) {
        byte[] dataByte;
        try {
            dataByte = dataStr.getBytes(this.chart);
        }
        catch(UnsupportedEncodingException v0) {
            dataByte = dataStr.getBytes();
        }

        this.addByteBufferSize(dataByte.length + 10);
        if(dataByte.length > 255) {
            this.addByteBufferData((byte)7, arg5);
            this.byteBuffer.putInt(dataByte.length);
            this.byteBuffer.put(dataByte);
        }
        else {
            this.addByteBufferData((byte)6, arg5);
            this.byteBuffer.put(((byte)dataByte.length));
            this.byteBuffer.put(dataByte);
        }
    }




    /**
     * 操作double类型
     */
    public final void addDoubleData(double arg2, int arg4) {
        this.addByteBufferSize(10);
        this.addByteBufferData((byte) 5, arg4);
        this.byteBuffer.putDouble(arg2);
    }

    /**
     * 操作float类型
     */
    private void addFloatData(float arg2, int arg3) {
        this.addByteBufferSize(6);//工具1
        this.addByteBufferData((byte) 4, arg3);//工具2
        this.byteBuffer.putFloat(arg2);
    }

    /**
     * 操作long类型
     */
    public final void addLongData(long arg3, int arg5) {
        this.addByteBufferSize(10);
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
    public final void addIntData(int data, int arg3) {
        this.addByteBufferSize(6);
        if (data < -32768 || data > 32767) {//判断int高16位是否有1----转换成short是否会溢出
            this.addByteBufferData((byte) 2, arg3);//工具2
            this.byteBuffer.putInt(data);
        } else {
            this.addShortData(((short) data), arg3);//short类型
        }
    }

    /**
     * 操作short类型
     */
    public final void addShortData(short arg2, int arg3) {
        this.addByteBufferSize(4);
        if (arg2 < -128 || arg2 > 127) {//判断short高8位是否有1----转换成byte是否会溢出
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
        this.addByteBufferSize(3);//工具1
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
    private void addByteBufferSize(int arg5) {
        if (this.byteBuffer.remaining() < arg5) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((this.byteBuffer.capacity() + arg5) * 2);
            byteBuffer.put(this.byteBuffer.array(), 0, this.byteBuffer.position());
            this.byteBuffer = byteBuffer;
        }
    }

    /**
     * 工具2
     */
    private void addByteBufferData(byte data, int index) {
        if (index < 15) {
            this.byteBuffer.put(((byte) (index << 4 | data)));
        } else if (index < 256) {
            this.byteBuffer.put(((byte) (data | 240)));
            this.byteBuffer.put(((byte) index));
        } else {
            throw new IllegalArgumentException("tag is too large: " + index);
        }

        return;

    }
}
