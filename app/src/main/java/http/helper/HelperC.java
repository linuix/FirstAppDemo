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

    protected String a;
    private ByteBuffer b;

    public HelperC() {
        this(128);//默认ByteBuffer的大小
    }

    public HelperC(int arg2) {
        super();
        this.a = "GBK";
        this.b = ByteBuffer.allocate(arg2);
    }

    public final int a(String arg2) {
        this.a = arg2;
        return 0;
    }

    /**
     * 对象的处理函数，根据不同的对象，处理不同的数据格式
     *
     * */
    public final void a(Object arg6, int arg7) {
        byte v3 = 9;
        int v2 = 8;
        int v0;

        if ((arg6 instanceof Byte)) {
            this.a(((Byte) arg6).byteValue(), arg7);//操作byte类型
        } else if ((arg6 instanceof Boolean)) {
            this.a(((Boolean) arg6).booleanValue(), arg7);//操作boolen类型
        } else if ((arg6 instanceof Short)) {
            this.a(((Short) arg6).shortValue(), arg7);//操作short类型
        } else if ((arg6 instanceof Integer)) {
            this.a(((Integer) arg6).intValue(), arg7);//操作int类型
        } else if ((arg6 instanceof Long)) {
            this.a(((Long) arg6).longValue(), arg7);//操作long类型
        } else if ((arg6 instanceof Float)) {
            this.a(((Float) arg6).floatValue(), arg7);//操作float类型
        } else if ((arg6 instanceof Double)) {
            this.a(((Double) arg6).doubleValue(), arg7);//操作double类型
        }
        else if((arg6 instanceof String)) {
            this.a(((String)arg6), arg7);//String 类型
        }
        else if((arg6 instanceof Map)) {
            this.a(((Map)arg6), arg7);//Map类型
        }

        else if((arg6 instanceof List)) {
            this.a(((Collection)arg6), arg7);//List类型
        }
        else if((arg6 instanceof JceStruct)) {
            this.a(((JceStruct)arg6), arg7);//JceStruct类型，这是一个数据来源的父类类型
        }
//数组类型
        else if((arg6 instanceof byte[])) {
            this.a(((byte[])arg6), arg7);//byte[]类型
        }

        else if((arg6 instanceof boolean[])) {//boolean[]类型
            this.a(v2);
            this.b(v3, arg7);
            this.a(((boolean[])arg6).length, 0);
            v2 = ((boolean[])arg6).length;

            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((boolean[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof short[])) {
            this.a(v2);
            this.b(v3, arg7);
            this.a(((short[])arg6).length, 0);
            v2 = ((short[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((short[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof int[])) {
            this.a(v2);
            this.b(v3, arg7);
            this.a(((int[])arg6).length, 0);
            v2 = ((int[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((int[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof long[])) {
            this.a(v2);
            this.b(v3, arg7);
            this.a(((long[])arg6).length, 0);
            v2 = ((long[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((long[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof float[])) {
            this.a(v2);
            this.b(v3, arg7);
            this.a(((float[])arg6).length, 0);
            v2 = ((float[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((float[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof double[])) {
            this.a(v2);
            this.b(v3, arg7);
            this.a(((double[])arg6).length, 0);
            v2 = ((double[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((double[])arg6)[v0], 0);
            }
        }
        else if(arg6.getClass().isArray()) {
            this.a(v2);
            this.b(v3, arg7);
            this.a(((Class<?>[])arg6).length, 0);
            v2 = ((Class<?>[])arg6).length;
            for(v0 = 0; v0 < v2; ++v0) {
                this.a(((Class<?>[])arg6)[v0], 0);
            }
        }
        else if((arg6 instanceof Collection)) {
            this.a(((Collection)arg6), arg7);
        }
        else {
            throw new IllegalArgumentException("write object error: unsupport type. " + arg6.getClass());
        }
        return;
    }

    /**
     * 获取ByteBuffer
     * */
    public final ByteBuffer a() {
        return this.b;
    }
/**
 * 获取ByteBuffer内的数据
 * */
    public final byte[] b() {
        byte[] v0 = new byte[this.b.position()];
        System.arraycopy(this.b.array(), 0, v0, 0, this.b.position());
        return v0;
    }

    /**
 *
 *操作byte[]数组类型
 * */
    public final void a(byte[] arg3, int arg4) {
        this.a(arg3.length + 8);
        this.b((byte)13, arg4);
        this.b((byte)0, 0);
        this.a(arg3.length, 0);
        this.b.put(arg3);
    }

    /**
     *
     * 操作JceStruct类型
     * */
    public final void a(JceStruct arg3, int arg4) {
        this.a(2);
        this.b((byte)10, arg4);
        arg3.writeTo(this);
        this.a(2);
        this.b((byte)11, 0);
    }

/***
 *
 * 操作List类型
 *
 * */
    public final void a(Collection arg4, int arg5) {
        this.a(8);
        this.b((byte)9, arg5);
        int v0 = arg4 == null ? 0 : arg4.size();
        this.a(v0, 0);
        if(arg4 != null) {
            Iterator v2 = arg4.iterator();
            while(v2.hasNext()) {
                this.a(v2.next(), 0);
            }
        }
    }


    /**
     *
     * 操作Map类型
     * **/
    public final void a(Map arg5, int arg6) {
        this.a(8);
        this.b((byte)8, arg6);
        int v0 = arg5 == null ? 0 : arg5.size();
        this.a(v0, 0);
        if(arg5 != null) {
            Iterator v2 = arg5.entrySet().iterator();
            while(v2.hasNext()) {
                Object v0_1 = v2.next();
                this.a(((Map.Entry)v0_1).getKey(), 0);
                this.a(((Map.Entry)v0_1).getValue(), 1);
            }
        }
    }




/**
 *
 * 操作String类型数据
 * */
    public final void a(String arg4, int arg5) {
        byte[] v0_1;
        try {
            v0_1 = arg4.getBytes(this.a);
        }
        catch(UnsupportedEncodingException v0) {
            v0_1 = arg4.getBytes();
        }

        this.a(v0_1.length + 10);
        if(v0_1.length > 255) {
            this.b((byte)7, arg5);
            this.b.putInt(v0_1.length);
            this.b.put(v0_1);
        }
        else {
            this.b((byte)6, arg5);
            this.b.put(((byte)v0_1.length));
            this.b.put(v0_1);
        }
    }




    /**
     * 操作double类型
     */
    public final void a(double arg2, int arg4) {
        this.a(10);
        this.b((byte) 5, arg4);
        this.b.putDouble(arg2);
    }

    /**
     * 操作float类型
     */
    private void a(float arg2, int arg3) {
        this.a(6);//工具1
        this.b((byte) 4, arg3);//工具2
        this.b.putFloat(arg2);
    }

    /**
     * 操作long类型
     */
    public final void a(long arg3, int arg5) {
        this.a(10);
        if (arg3 < -2147483648 || arg3 > 2147483647) {
            this.b((byte) 3, arg5);//工具2
            this.b.putLong(arg3);
        } else {
            this.a(((int) arg3), arg5);//int类型
        }
    }

    /**
     * 操作int类型
     */
    public final void a(int arg2, int arg3) {
        this.a(6);
        if (arg2 < -32768 || arg2 > 32767) {
            this.b((byte) 2, arg3);//工具2
            this.b.putInt(arg2);
        } else {
            this.a(((short) arg2), arg3);//short类型
        }
    }

    /**
     * 操作short类型
     */
    public final void a(short arg2, int arg3) {
        this.a(4);
        if (arg2 < -128 || arg2 > 127) {
            this.b((byte) 1, arg3);//工具2
            this.b.putShort(arg2);
        } else {
            this.a(((byte) arg2), arg3);
        }
    }


    /**
     * 操作boolean类型
     */
    private void a(boolean arg2, int arg3) {
        int v0 = arg2 ? 1 : 0;
        this.a(((byte) v0), arg3);//工具1
    }

    /**
     * 操作byte类型
     */
    public final void a(byte arg2, int arg3) {
        this.a(3);//工具1
        if (arg2 == 0) {
            this.b((byte) 12, arg3);
        } else {
            this.b((byte) 0, arg3);//工具2
            this.b.put(arg2);
        }
    }

    /**
     * 工具1
     */
    private void a(int arg5) {
        if (this.b.remaining() < arg5) {
            ByteBuffer v0 = ByteBuffer.allocate((this.b.capacity() + arg5) * 2);
            v0.put(this.b.array(), 0, this.b.position());
            this.b = v0;
        }
    }

    /**
     * 工具2
     */
    private void b(byte arg4, int arg5) {
        if (arg5 < 15) {
            this.b.put(((byte) (arg5 << 4 | arg4)));
        } else if (arg5 < 256) {
            this.b.put(((byte) (arg4 | 240)));
            this.b.put(((byte) arg5));
        } else {
            throw new IllegalArgumentException("tag is too large: " + arg5);
        }

        return;

    }
}
