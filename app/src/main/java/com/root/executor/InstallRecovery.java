package com.root.executor;

import android.text.TextUtils;

import com.demo.utils.LogUtil;
import com.root.dao.ICommUtil;
import com.root.impl.ChUtilImplk;
import com.root.util.WakeLockMgr;

import java.io.BufferedReader;
import java.io.File;

/**
 * Created by Administrator on 2016/11/8.
 */

public class InstallRecovery {
    private static Object a;
    private static String b;
    static {
        InstallRecovery.a = new Object();
        InstallRecovery.b = null;
    }
    public static String a(ICommUtil arg6) {
        Object v2_2 = null;
        File v4;
        BufferedReader v2 = null;
        CharSequence v1 = null;
        Object v3 = InstallRecovery.a;
        try
        {
            if(InstallRecovery.b != null) {
                return InstallRecovery.b;
            }

            InstallRecovery.b = "/system/etc/install-recovery.sh";
            if(arg6 == null) {
                return InstallRecovery.b;
            }

            WakeLockMgr.a(arg6, new ChUtilImplk(), new Object[0]);
            do {

                String v0_2 = v2.readLine();
                if(v0_2 != null) {
                    v0_2 = v0_2.trim();
                    if(!v0_2.startsWith("service")) {
                        continue;
                    }
                    String[] v0_3 = v0_2.split("\\s+");
                    if(v0_3.length < 3) {
                        continue;
                    }
                    v4 = new File(v0_3[2].trim());
                    v0_2 = v4.getName();
                    if(TextUtils.isEmpty(((CharSequence)v0_2))) {
                        continue;
                    }

                    if(!v0_2.startsWith("install")) {
                        continue;
                    }

                    if(!v0_2.endsWith("recovery.sh")) {
                        continue;
                    }

                    break;
                }
                v2.close();
                if(TextUtils.isEmpty(v1)) {
                    return InstallRecovery.b;
                }

                v2_2 = InstallRecovery.a;
                InstallRecovery.b = ((String)v1);
            }while (true);

            String v1_1 = v4.getAbsolutePath();
            LogUtil.d("check_su_files  find install-recovery.sh location..." + v1_1);
            v2.close();
            if(TextUtils.isEmpty(v1)) {
                return InstallRecovery.b;
            }
            v2_2 = InstallRecovery.a;
            InstallRecovery.b = ((String)v1);
        }catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.exception("install_recovery.sh exception ",e);
        }
        return InstallRecovery.b;
    }
}
