package com.root.executor;


import com.demo.process.RetValue;
import com.demo.utils.LogUtil;
import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;
import com.root.util.Utilsc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

final class ChUtilsd implements IChUtils {
    private CommUtilc a;
    ChUtilsd(CommUtilc arg1) {
        super();
        this.a = arg1;
    }

    public final Object a(ICommUtil arg10, List arg11) {
        Boolean v0_2;
        Object[] v3_1=null;
        int v8 = 2;
        if(arg10.a()) {
            ArrayList v2 = new ArrayList();
            switch(CommUtilc.a(this.a).f) {
                case 1: {
                    ((List)v2).add(CheckMountUtils.c);
                    break;
                }
                case 2: {
                    ((List)v2).add(CheckMountUtils.a);
                    break;
                }
            }

            ((List)v2).add(ChattrUtils.a(CommUtilc.a(this.a).k, false));
            if(CommUtilc.a(this.a).k.length() > 1 && (CommUtilc.a(this.a).k.endsWith(File.separator))
                    ) {
                CommUtilc.a(this.a).k = CommUtilc.a(this.a).k.substring(0, CommUtilc.a(this.a).k.length()
                         - 1);
            }

            int v0 = 0;
            while(true) {
                v0 = CommUtilc.a(this.a).k.indexOf(File.separatorChar, v0 + 1);
                if(v0 == -1) {
                    break;
                }

                File v3 = new File(CommUtilc.a(this.a).k.substring(0, v0));

                LogUtil.e("chUtilsd file ="+v3.getAbsolutePath()+",\t name ="+v3.getName());

                if(v3.getAbsolutePath().equals("/system")) {
                    continue;
                }

                if(v3.getAbsolutePath().equals("/system/bin")) {
                    continue;
                }

                if(v3.getAbsolutePath().equals("/system/xbin")) {
                    continue;
                }

                if(v3.getAbsolutePath().equals("/data")) {
                    continue;
                }

                if(!v3.exists()) {
                    LogUtil.d("check_su_files  repair " + CommUtilc.a(this.a).k + "...create" + v3.getAbsolutePath());
                    ((List)v2).add("mkdir " + v3.getAbsolutePath());
                    ((List)v2).add("chown 0.0 " + v3.getAbsolutePath());
                    ((List)v2).add("chmod 0755 " + v3.getAbsolutePath());
                    ((List)v2).add("chcon u:object_r:system_file:s0 " + v3.getAbsolutePath());
                    continue;
                }

                if(!v3.isDirectory()) {
                    LogUtil.d("check_su_files  repair " + CommUtilc.a(this.a).k + "...recreate" + v3.getAbsolutePath());
                    ((List)v2).add(ChattrUtils.a(v3.getAbsolutePath(), false));
                    ((List)v2).add("rm " + v3.getAbsolutePath());
                    ((List)v2).add("mkdir " + v3.getAbsolutePath());
                    ((List)v2).add("chown 0.0 " + v3.getAbsolutePath());
                    ((List)v2).add("chmod 0755 " + v3.getAbsolutePath());
                    ((List)v2).add("chcon u:object_r:system_file:s0 " + v3.getAbsolutePath());
                    continue;
                }

                ExecutorHelper v4 = new ExecutorHelper();
                v4.f = 493;
                v4.e = "u:object_r:system_file:s0";
                if(CommUtils2.a(arg10, v3.getAbsolutePath(), v4) == 0) {
                    continue;
                }

                LogUtil.d("check_su_files repair " + CommUtilc.a(this.a).k + "...chmod,chcon" + v3.getAbsolutePath());
                ((List)v2).add(ChattrUtils.a(v3.getAbsolutePath(), false));
                ((List)v2).add("chmod 0755 " + v3.getAbsolutePath());
                ((List)v2).add("chcon u:object_r:system_file:s0 " + v3.getAbsolutePath());
            }

            if(!CommUtilc.b(this.a)) {
                LogUtil.d("check_su_files  repair " + CommUtilc.a(this.a).k + "...cat");
                ((List)v2).add("rm " + CommUtilc.a(this.a).k);
                ((List)v2).add(String.format("cat %s > %s", CommUtilc.a(this.a).j, CommUtilc.a(this.
                        a).k));
            }

            if(Utilsc.a(CommUtilc.c(this.a), 1)) {
                LogUtil.d("check_su_files repair " + CommUtilc.a(this.a).k + "...chown");
                v3_1 = new Object[3];
                v3_1[0] = Integer.valueOf(CommUtilc.a(this.a).a);
                v3_1[1] = Integer.valueOf(CommUtilc.a(this.a).b);
                v3_1[v8] = CommUtilc.a(this.a).k;
                ((List)v2).add(String.format("chown %type.%type %s", v3_1));
                CommUtilc.a(this.a, CommUtilc.c(this.a) | 2);
            }

            if(Utilsc.a(CommUtilc.c(this.a), v8)) {
                LogUtil.d("check_su_files repair " + CommUtilc.a(this.a).k + "...chmod");
                ((List)v2).add(String.format("chmod 0%o %s", Integer.valueOf(CommUtilc.a(this.a).c), 
                        CommUtilc.a(this.a).k));
            }

            if(Utilsc.a(CommUtilc.c(this.a), 8)) {
                LogUtil.d("check_su_files repair " + CommUtilc.a(this.a).k + "...chcon");
                ((List)v2).add(String.format("chcon %s %s", CommUtilc.a(this.a).i, CommUtilc.a(this.
                        a).k));
            }

            if(CommUtilc.a(this.a).h) {
                ((List)v2).add(ChattrUtils.a(CommUtilc.a(this.a).k, true));
            }

            switch(CommUtilc.a(this.a).f) {
                case 1: {
                    ((List)v2).add(CheckMountUtils.d);
                    break;
                }
                case 2: {
                    ((List)v2).add(CheckMountUtils.b);
                    break;
                }
            }
            List v3_2 = arg10.a(((List)v2));//获取到执行完成指令的对象，进行输出，查看效果
            File v4_1 = new File("/system/bin/chcon");
            if(v3_2 == null) {
                v0_2 = Boolean.valueOf(true);
            }

            if(v3_2.size() != ((List)v2).size()) {
                v0_2 = Boolean.valueOf(true);
            }
            int v2_1;
            for(v2_1 = 0; true; ++v2_1) {
                if(v2_1 >= v3_2.size()) {
                    v0_2 = Boolean.valueOf(true);
                }
                if(!((RetValue)v3_2.get(v2_1)).isSuccess()) {
                    String v0_1 = ((RetValue)v3_2.get(v2_1)).cmd;
                    if(!v0_1.startsWith("rm ") && !v0_1.startsWith("mkdir ") && !v0_1.startsWith(ChattrUtils
                            .a()) && !v0_1.startsWith("mount")) {
                        if((v0_1.startsWith("chcon ")) && !v4_1.exists()) {
                                continue;
                        }
                        v0_2 = Boolean.valueOf(false);
                        return v0_2;
                    }
                }

            }
        }
        else {
            v0_2 = Boolean.valueOf(true);
        }
        return v0_2;
    }
}

