package com.root.util;

import com.demo.process.RetValue;
import com.demo.utils.LogUtil;
import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  final class ChPropertyUtils implements IChUtils {
   public ChPropertyUtils() {
        super();
    }

    public final Object a(ICommUtil arg10, List arg11) {
        LogUtil.d("ChPropertyUtils  start ");
        String v1_1 = null;
        boolean v3 = false;
        Object v0 = arg11.get(0);
        Object v1 = arg11.get(1);
        if(arg10.a()) {
            ArrayList v5 = new ArrayList(8);
            if(((String)v1).length() > 1 && (((String)v1).endsWith(File.separator))) {
                v1_1 = ((String)v1).substring(0, ((String)v1).length() - 1);
            }

            int v2 = 0;
            while(true) {
                v2 = v1_1.indexOf(File.separatorChar, v2 + 1);
                if(v2 == -1) {
                    break;
                }

                File v6 = new File(v1_1.substring(0, v2));
                if(v6.getAbsolutePath().equals("/data")) {
                    continue;
                }

                if(!v6.exists()) {
                    ((List)v5).add("mkdir " + v6.getAbsolutePath());
                    ((List)v5).add("chown 0.0 " + v6.getAbsolutePath());
                    ((List)v5).add("chmod 0755 " + v6.getAbsolutePath());
                    ((List)v5).add("chcon u:object_r:system_data_file:s0 " + v6.getAbsolutePath());
                    continue;
                }

                if(!v6.isDirectory()) {
                    ((List)v5).add("rm " + v6.getAbsolutePath());
                    ((List)v5).add("mkdir " + v6.getAbsolutePath());
                    ((List)v5).add("chown 0.0 " + v6.getAbsolutePath());
                    ((List)v5).add("chmod 0755 " + v6.getAbsolutePath());
                    ((List)v5).add("chcon u:object_r:system_data_file:s0 " + v6.getAbsolutePath());
                    continue;
                }

                ((List)v5).add("chmod 0755 " + v6.getAbsolutePath());
                ((List)v5).add("chcon u:object_r:system_data_file:s0 " + v6.getAbsolutePath());
            }

            ((List)v5).add("rm " + v1_1);
            ((List)v5).add(String.format("cat %s > %s", v0, v1_1));
            ((List)v5).add("chown 0.0 " + v1_1);
            ((List)v5).add("chmod 0755 " + v1_1);
            ((List)v5).add("chcon u:object_r:system_file:s0 " + v1_1);
            List v2_1 = arg10.a(((List)v5));
            int v1_2;
            for(v1_2 = 0; v1_2 < v2_1.size(); ++v1_2) {
                if(!((RetValue)v2_1.get(v1_2)).isSuccess()) {
                    String v0_1 = ((RetValue)v2_1.get(v1_2)).cmd;
                    if(!v0_1.startsWith("rm ") && !v0_1.startsWith("mount")) {
                        if(v0_1.startsWith("mkdir ")) {
                            LogUtil.d("v0_1.startsWith(mkdir  cotinue");
                          continue;
                        }
                        return Boolean.valueOf(v3);
                    }
                }
            }
            v3 = true;
        }
        LogUtil.d("ChPropertyUtils  end ");
        return v3;
    }
}

