package com.root.executor;

import android.text.TextUtils;

import com.demo.process.RetValue;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.root.dao.ICommUtil;
import com.root.impl.ChUtilsImplh;
import com.root.util.WakeLockMgr;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class CommUtils2 {

    protected static int a(String arg6, String arg7) {
        int v0_1;
        File v0 = new File(arg6);
        if (!v0.exists()) {
            v0_1 = -3;
        } else {
            File v1 = new File(arg7);
            if (!v1.exists()) {
                v0_1 = -2;
            } else {
                if (v0.length() == v1.length()) {
                    long v2 = Utils.a(v0);
                    long v0_2 = Utils.a(v1);
                    if (v2 != 0 && v2 == v0_2) {
                        return 0;
                    }
                }

                v0_1 = -1;
            }
        }

        return v0_1;
    }

    public static int a(ICommUtil arg6, String arg7, ExecutorHelper arg8) {
        int v0 = 0;
        int v4 = -1;
        ExecutorHelper v1 = CommUtils2.a(arg6, arg7);
        if (v1 != null) {
            if (arg8.a != v4 && v1.a != arg8.a || arg8.b != v4 && v1.b != arg8.b) {
                v0 = 1;
            }

            if (arg8.c != v4 && v1.c != arg8.c) {
                v0 |= 2;
            } else if (arg8.f != v4 && (v1.c & arg8.f) != arg8.f) {
                v0 |= 2;
            }

            if (arg8.d != -1 && v1.d != arg8.d) {
                v0 |= 4;
            }

            if (TextUtils.isEmpty(arg8.e)) {
                return v0;
            }

            if (TextUtils.isEmpty(v1.e)) {
                return v0;
            }

            if (v1.e.equals(arg8.e)) {
                return v0;
            }

            v0 |= 8;
        }
        return v0;
    }

    public static boolean a(ICommUtil arg4, List arg5, ExecutorHelper arg6) {
        boolean v0_2;
        Iterator v1 = arg5.iterator();
        do {
            if (v1.hasNext()) {
                Object v0 = v1.next();
                File v2 = new File(((String)v0));
                if(v2.exists()) {
                   try
                   {
                       if(Utils.c(v2)) {
                           continue;
                       }
                       if(CommUtils2.a(arg4, ((String)v0), arg6) == 0) {
                           continue;
                       }

                       break;
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                       LogUtil.exception("CommUtils2 exception",e);
                   }
                }
                else
                {
                    continue;
                }
            } else {
                return false;
            }

            return true;

        } while (true);

        return true;
    }

    private static ExecutorHelper a(ICommUtil arg5, String arg6) {
        ExecutorHelper v0 = null;
        ExecutorHelper v1 = new ExecutorHelper();
        RetValue v2 = arg5.a(String.valueOf(KToolsUtils.a()) + " zls " + arg6);
        if((v2.a()) && !v2.stdout.trim().equals("Hehe")) {
            String[] v2_1 = v2.stdout.split(" ");
            if(v2_1.length >= 5)
            {
                try
                {
                    v1.c = Integer.parseInt(v2_1[0].trim());
                    v1.a = Integer.parseInt(v2_1[1].trim());
                    v1.b = Integer.parseInt(v2_1[2].trim());
                    v1.d = Long.parseLong(v2_1[3].trim());
                    String v2_2 = v2_1[4].trim().equals("-") ? "" : v2_1[4].trim();
                    v1.e = v2_2;
                    return v0;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    LogUtil.exception("commUtils2 KToolsUtils exception ",e);
                }
            }
            v0 = v1;

        }
        return v0;
    }

    public static void b(ICommUtil arg3, String arg4, ExecutorHelper arg5) {
        if(!Utils.c(new File(arg4))) {
            LogUtil.d("check_su_files repair " + arg4 + "...chown,chmod,chcon");
            WakeLockMgr.a(arg3, new ChUtilsImplh(), new Object[]{arg4, arg5});
        }
    }

    public static void b(ICommUtil arg2, List arg3, ExecutorHelper arg4) {
        Iterator v1 = arg3.iterator();
        while(v1.hasNext()) {
            CommUtils2.b(arg2, (String)v1.next(), arg4);
        }
    }
}
