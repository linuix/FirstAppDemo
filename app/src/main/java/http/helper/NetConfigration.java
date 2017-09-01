package http.helper;


import com.demo.entity.GetKingRootSolutionResp;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public final class NetConfigration extends OpertorC {
    protected RequestPacket requestPacket;
    static HashMap h;
    static HashMap i;

    static {
        NetConfigration.h = null;
        NetConfigration.i = null;
    }

    public NetConfigration(byte arg2) {
        super();
        this.requestPacket = new RequestPacket();
        this.b();
    }

    public NetConfigration() {
        super();
        this.requestPacket = new RequestPacket();
        this.requestPacket.iVersion = 2;
    }

    public final void setMapData(String key, Object value) {
        if(key.startsWith(".")) {
            throw new IllegalArgumentException("put name can not startwith . , now is " + key);
        }
        super.setMapData(key, value);
    }

    public final byte[] getHelperCBuffer() {
        int v3 = 2;
        if(this.requestPacket.iVersion != v3) {
            if(this.requestPacket.sServantName == null) {
                this.requestPacket.sServantName = "";
            }
            if(this.requestPacket.sFuncName != null) {
                HelperC helperC = new HelperC(0);
                helperC.setChart(this.chart);
                if(this.requestPacket.iVersion == v3) {
                    helperC.addMapData(this.mapB, 0);
                }else{
                    helperC.addMapData(this.mapC, 0);
                }
                this.requestPacket.sBuffer = Helper1.copyData(helperC.getByteBuffer());
                helperC = new HelperC(0);
                helperC.setChart(this.chart);
                this.requestPacket.writeTo(helperC);
                byte[] helperC_data = Helper1.copyData(helperC.getByteBuffer());
                int length = helperC_data.length;
                ByteBuffer byteBuffer = ByteBuffer.allocate(length + 4);
                byteBuffer.putInt(length + 4).put(helperC_data).flip();
                return byteBuffer.array();

            }

            this.requestPacket.sFuncName = "";
        }
        else if(this.requestPacket.sServantName.equals("")) {
            throw new IllegalArgumentException("servantName can not is null");
        }
        else if(this.requestPacket.sFuncName.equals("")) {
            throw new IllegalArgumentException("funcName can not is null");
        }

        return null;
    }

    /**
     *
     * 备注：
     * tag =-1 什么也不做
     *public  static  final int GET_GUID=20000;//设置guid属性到kr-stock-conf文件，为了后续的读取属性文件字段w.g做准备
     *public  static  final  int GET_SOLUTIONS=20001；//获取solution解决方案，添加到ArrayList内。通过XmlFileSolute解析出对应
     *
     * */
    private static int tag=-1;

    public static void setTag(int tag) {
        NetConfigration.tag = tag;
    }

    public final void setData(byte[] data) {
        HashMap hashMap;
        if(data.length < 4) {
            throw new IllegalArgumentException("decode package must include size head");
        }
        try {
            LogUtil.e("original 网络数据 arg: "+new String(data) + " ,len ="+data.length);
            GetKingRootSolutionResp response = null;
            HelperA helperA = new HelperA(data,(byte) 0);
            helperA.setChart(this.chart);
            this.requestPacket.readFrom(helperA);//这里把byte[]准备好
            if(this.requestPacket.iVersion == 3)
            {//开始的时候，设置好的参数
                LogUtil.d(" iVersion == 3 " + chart);
                LogUtil.e("操作后的数据字节 md5.sBuffer.length ："+ requestPacket.sBuffer.length+",content ="+new String(requestPacket.sBuffer));
                Utils.writeNetData(requestPacket.sBuffer);
                //从这里开始设置数据
                if (tag == Const.GET_GUID)
                {
                    ParseDataHelper.setGuid(requestPacket.sBuffer);
                    //设置完成值，就反馈回去
                    return;
                }
                if (tag == Const.GET_SOLUTIONS)
                {
                     response = new GetKingRootSolutionResp();
                    //返回一个SolutionHelpers[] ，再根据字段 resp,
                    //在GetKingRootSolutionResp 这里边 初始化了RootExtInfo，方便在后续调用，网络数据没有该字段，就暂时不管，没什么作用
                    ParseDataHelper.getSolution(requestPacket.sBuffer);
                    ArrayList solution =ParseDataHelper.getmList();
                    if (solution ==null)
                    {
                        LogUtil.e("解密出错。没拿到list对象数据 NetConfigration decodePkg");
                        return;
                    }
                    response.solutionsXmls =solution;
                    this.mapC.put("resp",response);
                    //读取完成，就返回去
                    return;
                }
             HelperA helperA1 = new HelperA(this.requestPacket.sBuffer);//这里的sBuffer就是反馈回来的数据的长度，需要使用自己的写法计算出来整个字符串

                helperA1.setChart(this.chart);
                if(NetConfigration.h == null)
                {
                    hashMap = new HashMap();
                    NetConfigration.h = hashMap;
                    h.put("", new byte[0]);
                }
//              helperA.test();
                this.mapC = helperA1.a(NetConfigration.h, 0, false);
//                LogUtil.type("data = "+fileSize.toString());//返回的数据为空，导致空指针异常
            }
            else
            {
                LogUtil.d("iVersion != 3");
                helperA = new HelperA(this.requestPacket.sBuffer);
                helperA.setChart(this.chart);
                if(NetConfigration.i == null)
                {
                    NetConfigration.i = new HashMap();
                    hashMap = new HashMap();
                    hashMap.put("", new byte[0]);
                    NetConfigration.i.put("", hashMap);
                }
//              helperA.test();
                this.mapB = helperA.a(NetConfigration.i, 0, false);
                LogUtil.e("data sdk_gt18 = "+ mapB.toString());
                this.b = new HashMap();
            }
            return;
        }
        catch(Exception v0) {
            throw new RuntimeException(v0);
        }
    }

    public final void setChart(String arg1)
    {
        super.setChart(arg1);
    }

    public final void setRequestId(int arg2)
    {
        this.requestPacket.iRequestId = arg2;
    }

    public final void setServantName(String arg2)
    {
        this.requestPacket.sServantName = arg2;
    }

    public final void b()
    {
        super.b();
        this.requestPacket.iVersion = 3;
    }
    public final void setFuncName(String arg2)
    {
        this.requestPacket.sFuncName = arg2;
    }
}


