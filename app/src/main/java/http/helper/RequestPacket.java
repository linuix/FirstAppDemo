package http.helper;


import com.demo.entity.JceStruct;
import com.demo.utils.LogUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class RequestPacket extends JceStruct {
    public byte cPacketType;
    static Map cache_context;
    static byte[] cache_sBuffer;
    public Map context;//按照给定的数据
    public int iMessageType;
    public int iRequestId;
    public int iTimeout;
    public short iVersion;
    public byte[] sBuffer;
    public String sFuncName;
    public String sServantName;
    public Map status;
    public static boolean $assertionsDisabled;

    static {
        byte[] v1 = null;
        boolean v0 = !RequestPacket.class.desiredAssertionStatus() ? true : false;
        RequestPacket.$assertionsDisabled = v0;
        RequestPacket.cache_sBuffer = v1;
        RequestPacket.cache_context = null;
    }

    public RequestPacket() {
        super();
        this.iVersion = 0;
        this.cPacketType = 0;
        this.iMessageType = 0;
        this.iRequestId = 0;
        this.sServantName = null;
        this.sFuncName = null;
        this.iTimeout = 0;
    }

    public RequestPacket(short arg3, byte arg4, int arg5, int arg6, String arg7, String arg8, byte[]
            arg9, int arg10, Map arg11, Map arg12) {
        super();
        this.iVersion = 0;
        this.cPacketType = 0;
        this.iMessageType = 0;
        this.iRequestId = 0;
        this.sServantName = null;
        this.sFuncName = null;
        this.iTimeout = 0;
        this.iVersion = arg3;
        this.cPacketType = arg4;
        this.iMessageType = arg5;
        this.iRequestId = arg6;
        this.sServantName = arg7;
        this.sFuncName = arg8;
        this.sBuffer = arg9;
        this.iTimeout = arg10;
        this.context = arg11;
        this.status = arg12;
    }

    public final Object clone() {
        Object v0 = null;
        try {
            v0 = super.clone();
        } catch (CloneNotSupportedException v1) {
            if (RequestPacket.$assertionsDisabled) {
                return v0;
            }
            throw new AssertionError();
        }
        return v0;
    }

    public final boolean equals(Object arg4) {
        boolean v0 = true;
        if (!Helper1.check(((RequestPacket) arg4).iVersion)
                || !Helper1.check(((RequestPacket) arg4).cPacketType)
                || !Helper1.check(((RequestPacket) arg4).iMessageType)
                || !Helper1.check(((RequestPacket) arg4).iRequestId)
                || !Helper1.isEquals(Integer.valueOf(1), ((RequestPacket) arg4).sServantName)
                || !Helper1.isEquals(Integer.valueOf(1), ((RequestPacket) arg4).sFuncName)
                || !Helper1.isEquals(Integer.valueOf(1), ((RequestPacket) arg4).sBuffer)
                || !Helper1.check(((RequestPacket) arg4).iTimeout)
                || !Helper1.isEquals(Integer.valueOf(1), ((RequestPacket) arg4).context)
                || !Helper1.isEquals(Integer.valueOf(1), ((RequestPacket) arg4).status)

                ) {
            v0 = false;
        }

        return v0;
    }
    public final void readFrom(HelperA helperA) {
        HashMap v0_1;
        try {
            this.iVersion = helperA.getShort(this.iVersion, 1, true);
            this.cPacketType = helperA.getByte(this.cPacketType, 2, true);
            this.iMessageType = helperA.getDataForBuffer(this.iMessageType, 3, true);
            this.iRequestId = helperA.getDataForBuffer(this.iRequestId, 4, true);
            this.sServantName = helperA.a(5, true);
            this.sFuncName = helperA.a(6, true);
            if (RequestPacket.cache_sBuffer == null) {
                RequestPacket.cache_sBuffer = new byte[1];
            }

            this.sBuffer = helperA.b(7, true);

            LogUtil.d("seconde buffer len ="+sBuffer.length);//取到的长度是48
            this.iTimeout = helperA.getDataForBuffer(this.iTimeout, 8, true);

            if (RequestPacket.cache_context == null) {
                v0_1 = new HashMap();
                RequestPacket.cache_context = ((Map) v0_1);
                ((Map) v0_1).put("", "");
            }

            /**
             * 需要在这里添加数据，才能读取到?????
             *
             * **/
            this.context = helperA.a(RequestPacket.cache_context, 9, true);//
            if (RequestPacket.cache_context == null)
            {

                v0_1 = new HashMap();

                RequestPacket.cache_context = ((Map) v0_1);
                ((Map) v0_1).put("", "");
            }

            this.status = helperA.a(RequestPacket.cache_context, 10, true);
            LogUtil.e("RequsetPackage: "+toString());

            return;
        }
        catch (Exception v0)
        {
            v0.printStackTrace();
            throw new RuntimeException(((Throwable) v0));
        }
    }

    public final void writeTo(HelperC arg3) {
        arg3.addShortData(this.iVersion, 1);
        arg3.addByteData(this.cPacketType, 2);
        arg3.addIntData(this.iMessageType, 3);
        arg3.addIntData(this.iRequestId, 4);
        arg3.addStringData(this.sServantName, 5);
        arg3.addStringData(this.sFuncName, 6);
        arg3.addByteData(this.sBuffer, 7);
        arg3.addIntData(this.iTimeout, 8);
        arg3.addMapData(this.context, 9);
        arg3.addMapData(this.status, 10);
    }

    @Override
    public String toString() {

        LogUtil.e("buffer ="+new String(sBuffer));


        return "RequestPacket{" +
                "cPacketType=" + cPacketType +
                ", context=" + context +
                ", iMessageType=" + iMessageType +
                ", iRequestId=" + iRequestId +
                ", iTimeout=" + iTimeout +
                ", iVersion=" + iVersion +
                ", sBuffer=" + Arrays.toString(sBuffer) +
                ", sFuncName='" + sFuncName + '\'' +
                ", sServantName='" + sServantName + '\'' +
                ", status=" + status +
                '}';
    }
}

