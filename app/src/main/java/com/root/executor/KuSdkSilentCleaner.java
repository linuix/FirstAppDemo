package com.root.executor;

import com.demo.process.RetValue;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.root.dao.ICommUtil;
import com.root.process.SDKLibProcessMgr;

import java.io.File;

/**
 * Created by Administrator on 2016/11/9.
 *
 *
 * 2016.11.25：增加自己的判断属性，主要区分Kingroot和自己身份的标志
 *
 * Const.kingroot_tag
 * Const.mars_tag
 * 这两个标志属性
 */

public class KuSdkSilentCleaner{

    /**
     *
     * 在外部调用的时候，设置这个标志属性
     * 便于操作文件 su, superuser.apk这两个文件
     * */

private static  int tag = Const.kingroot_tag;//默认属性
    public  static  void setTag(int param)
    {
        tag = param;
    }

    private ICommUtil iCommUtil;
    public KuSdkSilentCleaner(ICommUtil arg2) {
        super();
        this.iCommUtil = arg2;
    }
    public final void a() {

        if (tag == Const.kingroot_tag)
        {
            kingrootTag();
        }
        else  if (tag == Const.mars_tag)
        {
            marsTab();
        }

    }

    private void kingrootTag() {
        int v0 = 1;
        int v2_2 = 1;
        if(!this.iCommUtil.a()) {
            return;
        }
        File v2 = new File("/system/bin/su");
        File v3 = new File("/system/xbin/su");

        if((v2.exists()) && !Utils.c(v2)) {
            if(v2_2 != 0) {
                RetValue v2_3 = SDKLibProcessMgr.a("sh", String.valueOf("su") + " -v");
                if (!v2_3.a() || v2_3.stdout == null || !v2_3.stdout.contains("kinguser_su")) {
                    v0 = 0;
                }

                if (!this.iCommUtil.a()) {
                    LogUtil.d("KuSdkSilentCleaner 不能拿到root授权,清理daemon进程失败!");
                    return;
                }
            }

        }

        if((v3.exists()) && !Utils.c(v3)) {
            if(v2_2 != 0) {
                RetValue v2_3 = SDKLibProcessMgr.a("sh", String.valueOf("su") + " -v");
                if (!v2_3.a() || v2_3.stdout == null || !v2_3.stdout.contains("kinguser_su")) {
                    v0 = 0;
                }

                if (!this.iCommUtil.a()) {
                    LogUtil.d("KuSdkSilentCleaner 不能拿到root授权,清理daemon进程失败!");
                    return;
                }
            }
        }

        v2_2 = 0;
        LogUtil.d("KuSdkSilentCleaner finished");
    }
/**
 *
 * 测试自己的mysu文件
 * **/
    private void marsTab() {
        LogUtil.e("mars root cleaner started .......");
        int v0 = 1;
        int v2_2 = 1;
        if(!this.iCommUtil.a()) {
            return;
        }
        File v2 = new File("/system/bin/mysu");
        File v3 = new File("/system/xbin/mysu");

        if((v2.exists()) && !Utils.c(v2)) {
            if(v2_2 != 0) {
                RetValue v2_3 = SDKLibProcessMgr.a("sh", String.valueOf("mysu") + " -v");
                if (!v2_3.a() || v2_3.stdout == null ) {
                    v0 = 0;
                }

                if (!this.iCommUtil.a()) {
                    LogUtil.d("MarsCleanersss 不能拿到root授权,清理daemon进程失败!");
                    return;
                }
            }

        }

        if((v3.exists()) && !Utils.c(v3)) {
            if(v2_2 != 0) {
                RetValue v2_3 = SDKLibProcessMgr.a("sh", String.valueOf("mysu") + " -v");
                if (!v2_3.a() || v2_3.stdout == null ) {
                    v0 = 0;
                }

                if (!this.iCommUtil.a()) {
                    LogUtil.d("mars 不能拿到root授权,清理daemon进程失败!");
                    return;
                }
            }
        }

        v2_2 = 0;
        LogUtil.d("MarsCleaner finished");
    }



}
