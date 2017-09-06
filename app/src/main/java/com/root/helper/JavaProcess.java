package com.root.helper;

import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;
import com.root.dao.IJavaProcessh;

/**
 * Created by Administrator on 2016/11/8.
 */

public final class JavaProcess extends JavaProcessImplw implements IJavaProcessh {


    /**
     * 增加 一个字段。用来判断是否可以退出RootMgr的execute函数
     * String flag
     *
     * */
    public  static String flag;
    private JavaProcess(RootProcess arg2, boolean arg3) {
        super();
        ((JavaProcessImplw) this).rootProcess = arg2;
        int v0 = arg3 ? 4 : 3;
        ((JavaProcessImplw) this).a = v0;
    }
    public static JavaProcess a(int arg5, int arg6) {
        ThreadLocalWeakRef.createThreadLocal();
        JavaProcess v0_1 = null;
        int v0 = 0;
        try {
            LogUtil.loge("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            RootProcess v2 = new RootProcess("sh");
            v2.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
            RetValue v1_1 = v2.execut("su");
            LogUtil.d("sh su ret : " + v1_1.ret + ", stdout : " + v1_1.stdout + ", stderr : " + v1_1.err);
            v1_1 = v2.execute("id", ((long) arg5));

            LogUtil.loge("sh su after id 测试读取到的数据 ret : " + v1_1.ret + ", stdout : " + v1_1.stdout + ", stderr : "
                    + v1_1.err + " time ="+arg5);

            /**
             *  sh su after id 测试读取到的数据 ret : 0,
             *  stdout : uid=10234(u0_a234) gid=10234(u0_a234) groups=1015(sdcard_rw),1028(sdcard_r),3003(inet),50234(all_a234) context=u:r:untrusted_app:s0
             , stderr :  time =20000

             *这里是判断拿取到root的关键点，那么在这里之后，应该是开始执行各种文件的操作，包括了su ,superuser
             * 的放置
             *
             * */

            if (v1_1.ret.intValue() == 0 && (AbsJavaProcessImpla.checkRoot(v1_1.stdout)))
            {
                LogUtil.d(" **************************** ");
                LogUtil.loge("VirtualTerminal runCommand ret : " + v1_1.ret + ", stdout : " + v1_1.stdout + ", stderr : "
                        + v1_1.err);
                v0_1 = new JavaProcess(v2, JavaProcess.d());
                flag = v1_1.stdout;
                return v0_1;
            }

            ThreadLocalWeakRef.a(7020, "ret=" + v1_1.ret + ",stdout=" + v1_1.stdout + ",stderr=" + v1_1.err);
        } catch (Exception v1) {
            /**
             * 这里还是出现了空指针异常
             * 可是明显出现了
             *
             * */
           // ThreadLocalWeakRef.sdk_gt18(7021, "", ( v1));
            v1.printStackTrace();
           // LogUtil.exception("7021 javaprocess exception ",v1);//这里怎么会一直出现空指针异常信息
        }

        if (v0 < arg6) {
            LogUtil.loge("再次尝试连su: " + (v0 + 1));
            long v1_2 = 1000;
            try {
                Thread.sleep(v1_2);
            } catch (InterruptedException v1_3) {
                //LogUtil.exception("再次尝试连接su异常 ",v1_3);
            }
            ++v0;
        }
        return v0_1;
    }
/***
 *
 * 执行检测su 是否存在
 *
 * **/
    private static boolean d() {
        RootProcess v1_1;
        boolean v0 = false;
        RootProcess v2 = null;
        v1_1 = new RootProcess("sh");
        v1_1.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
        RetValue v2_2 = v1_1.execute("su -v", 5000);
        LogUtil.d("checkIfKuSu : ret = " + v2_2.ret + ", stdout = " + v2_2.stdout + ", stderr = " + v2_2
                .err);
        if ((v2_2.isSuccess()) && v2_2.stdout != null) {
           /* if (!v2_2.stdout.contains("kinguser")) {
                v1_1.closeAll();
                return v0;
            }*/
            LogUtil.e("不要kinguser这个判断条件，在javaProcess这个类中，可以直接创建出这个类");
            v0 = true;
        }
        v1_1.closeAll();
        return v0;

    }

}
