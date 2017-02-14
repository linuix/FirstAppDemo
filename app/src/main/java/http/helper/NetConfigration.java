package http.helper;


import com.demo.entity.GetKingRootSolutionResp;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public final class NetConfigration extends OpertorC {
    protected RequestPacket g;
    static HashMap h;
    static HashMap i;
    private int j;

    static {
        NetConfigration.h = null;
        NetConfigration.i = null;
    }

    public NetConfigration(byte arg2) {
        super();
        this.g = new RequestPacket();
        this.j = 0;
        this.b();
    }

    public NetConfigration() {
        super();
        this.g = new RequestPacket();
        this.j = 0;
        this.g.iVersion = 2;
    }

    public final void a(String arg4, Object arg5) {
        if(arg4.startsWith(".")) {
            throw new IllegalArgumentException("put name can not startwith . , now is " + arg4);
        }

        super.a(arg4, arg5);
    }

    public final byte[] a() {
        int v3 = 2;
        if(this.g.iVersion != v3) {
            if(this.g.sServantName == null) {
                this.g.sServantName = "";
            }
            if(this.g.sFuncName != null) {
                HelperC v0 = new HelperC(0);
                v0.a(this.c);
                if(this.g.iVersion == v3) {
                    v0.a(this.a, 0);
                }
                else {
                    v0.a(this.e, 0);
                }

                this.g.sBuffer = Helper1.a(v0.a());

                v0 = new HelperC(0);
                v0.a(this.c);
                this.g.writeTo(v0);
                byte[] v0_1 = Helper1.a(v0.a());
                int v1 = v0_1.length;
                ByteBuffer v2 = ByteBuffer.allocate(v1 + 4);
                v2.putInt(v1 + 4).put(v0_1).flip();
                return v2.array();

            }

            this.g.sFuncName = "";
        }
        else if(this.g.sServantName.equals("")) {
            throw new IllegalArgumentException("servantName can not is null");
        }
        else if(this.g.sFuncName.equals("")) {
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

    public final void a(byte[] arg5) {
        HashMap v1;
        if(arg5.length < 4) {
            throw new IllegalArgumentException("decode package must include size head");
        }
        try {
            LogUtil.e("original 网络数据 arg: "+new String(arg5) + " ,len ="+arg5.length);
            GetKingRootSolutionResp response =null;
            HelperA v0_1 = new HelperA(arg5,(byte) 0);
            v0_1.a(this.c);
            this.g.readFrom(v0_1);//这里把byte[]准备好
            if(this.g.iVersion == 3)
            {//开始的时候，设置好的参数
                LogUtil.d(" iVersion == 3 " +c);
                LogUtil.e("操作后的数据字节 g.sBuffer.length ："+g.sBuffer.length+",content ="+new String(g.sBuffer));
                Utils.writeNetData(g.sBuffer);
                //从这里开始设置数据
                if (tag == Const.GET_GUID)
                {
                    ParseDataHelper.setGuid(g.sBuffer);
                    //设置完成值，就反馈回去
                    return;
                }
                if (tag == Const.GET_SOLUTIONS)
                {
                     response = new GetKingRootSolutionResp();
                    //返回一个SolutionHelpers[] ，再根据字段 resp,
                    //在GetKingRootSolutionResp 这里边 初始化了RootExtInfo，方便在后续调用，网络数据没有该字段，就暂时不管，没什么作用
                    ParseDataHelper.getSolution(g.sBuffer);
                    ArrayList solution =ParseDataHelper.getmList();
                    if (solution ==null)
                    {
                        LogUtil.e("解密出错。没拿到list对象数据 NetConfigration decodePkg");
                        return;
                    }
                    response.solutionsXmls =solution;
                    this.e.put("resp",response);
                    //读取完成，就返回去
                    return;
                }
             HelperA v0_2 = new HelperA(this.g.sBuffer);//这里的sBuffer就是反馈回来的数据的长度，需要使用自己的写法计算出来整个字符串

                v0_2.a(this.c);
                if(NetConfigration.h == null)
                {
                    v1 = new HashMap();
                    NetConfigration.h = v1;
                    h.put("", new byte[0]);
                }
//              v0_1.test();
                this.e = v0_2.a(NetConfigration.h, 0, false);
//                LogUtil.d("data = "+e.toString());//返回的数据为空，导致空指针异常

            }

            else
            {
                LogUtil.d("iVersion != 3");
                v0_1 = new HelperA(this.g.sBuffer);
                v0_1.a(this.c);
                if(NetConfigration.i == null)
                {
                    NetConfigration.i = new HashMap();
                    v1 = new HashMap();
                    v1.put("", new byte[0]);
                    NetConfigration.i.put("", v1);
                }
//              v0_1.test();
                this.a = v0_1.a(NetConfigration.i, 0, false);
                LogUtil.e("data sdk_gt18 = "+a.toString());
                this.b = new HashMap();
            }
            return;
        }
        catch(Exception v0) {
            throw new RuntimeException(((Throwable)v0));
        }
    }

    public final void a(String arg1)
    {
        super.a(arg1);
    }

    public final void a(int arg2)
    {
        this.g.iRequestId = arg2;
    }

    public final void b(String arg2)
    {
        this.g.sServantName = arg2;
    }

    public final void b()
    {
        super.b();
        this.g.iVersion = 3;
    }
    public final void c(String arg2)
    {
        this.g.sFuncName = arg2;
    }
}


