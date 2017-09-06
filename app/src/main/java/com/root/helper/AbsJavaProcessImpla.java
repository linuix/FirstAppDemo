package com.root.helper;

import android.text.TextUtils;

import com.demo.utils.LogUtil;
import com.root.dao.IJavaProcessh;

/**
 * Created by Administrator on 2016/11/8.
 */

public abstract class AbsJavaProcessImpla implements IJavaProcessh {

    protected int a;
    protected String b;

    public AbsJavaProcessImpla() {
        super();
        this.b = "0";
    }

    public static int a(IJavaProcessh arg1) {
        int v0;
        if (arg1 == null) {
            v0 = 0;
        } else if ((arg1 instanceof AbsJavaProcessImpla)) {
            v0 = ((AbsJavaProcessImpla) arg1).a;
        } else {
            v0 = -1;
        }

        return v0;
    }

    public final int a() {
        return this.a;
    }

/**
 *
 * 静默安装的函数，在父类中实现，子类调用，
 * arg8:需要执行的命令 pm install -r -type +apk_path
 *
 *   arg8 = pm install -r -type  /system/app/demo.apk
 *
 * */
    public JavaSolutionHelpers a(String arg8) {
        JavaSolutionHelpers javaSolutionHelpers;
        JavaSolutionHelpers v1 = null;
        int v6 = -1;
        String v2 = this.d(String.valueOf(arg8) + ";echo $?");/**核心实现的工具，主要是子类实现**/
        if (v2 == null) {
            javaSolutionHelpers = new JavaSolutionHelpers(v6, ThreadLocalWeakRef.b());
        }
        int v3 = v2.lastIndexOf("\n", v2.length() - 2);
        int v4 = v2.lastIndexOf("\n");
        if (v3 >= 0 && v4 > 0) {
            try {
                javaSolutionHelpers = new JavaSolutionHelpers(Integer.parseInt(v2.substring(v3 + 1, v4)), v2.substring(0, v3));
            } catch (NumberFormatException v0) {
                LogUtil.exception("executeCommand2 exception.", ((Throwable) v0));
                javaSolutionHelpers = v1;
            }
            if (javaSolutionHelpers != null) {
                return javaSolutionHelpers;
            } else {
                javaSolutionHelpers = new JavaSolutionHelpers(v6, v2);
                if (javaSolutionHelpers.a == 0) {
                    LogUtil.d("cmd: " + arg8 + ", ret: " + javaSolutionHelpers.a + ", stdout = " + javaSolutionHelpers.b);
                } else {
                    LogUtil.loge("cmd: " + arg8 + ", ret: " + javaSolutionHelpers.a + ", stdout = " + javaSolutionHelpers.b);
                }

                return javaSolutionHelpers;
            }

        }
        if (v3 == v6) {
            try {
                javaSolutionHelpers = new JavaSolutionHelpers(Integer.parseInt(v2.trim()), "");
            } catch (NumberFormatException v0) {
                LogUtil.exception("executeCommand2 exception.", ((Throwable) v0));
                javaSolutionHelpers = v1;
            }
        } else {
            javaSolutionHelpers = v1;
        }

        return javaSolutionHelpers;
    }

    public JavaSolutionHelpers a(String arg2, long arg3) {
        LogUtil.loge("executeCommand2() Not sopport timeout parameter.");
        return this.a(arg2);
    }
    public static boolean checkRoot(String arg4) {
        LogUtil.d("AbsJavaProcessImpla v18 检测是否获取root权限 ");
        ThreadLocalWeakRef.createThreadLocal();
        boolean v0 = false;
        LogUtil.d("id = " + arg4);
        if(!TextUtils.isEmpty(arg4) && (arg4.contains("uid=0(root)"))) {
            v0 = true;
        }
       LogUtil.d("isFullyRoot|id: " + arg4 + ", isRoot: " + v0);
        if(!v0) {
            ThreadLocalWeakRef.a(7012, "id=" + arg4);
        }
//在这里安装自己的su superuser.apk
//        if (v0)
//        {
//            LogUtil.fileSize("拿到临时root 权限，接着就是写自己的文件");
//            v0 = MarsRoot.startRoot();
//            if (v0)
//            {
//                //成功，那么通知RootMgr不知继续往下执行
//                RootMgr.setIsSucc(v0);
//                LogUtil.fileSize("设置完成状态标识，"+v0);
//            }
//            v0=true;
//        }
        return v0;
    }
    public final boolean b() {
        LogUtil.d("监测是否安装了 kingroot.apk " +false);
        return false;
    }
    public final void c(String arg1) {
        this.b = arg1;
    }
}
