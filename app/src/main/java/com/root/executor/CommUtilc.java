package com.root.executor;

import android.text.TextUtils;

import com.demo.utils.LogUtil;
import com.root.dao.AbsCommUtila;
import com.root.dao.ICommUtil;
import com.root.helper.ExecutHelper;
import com.root.util.SelinuxUtils;
import com.root.util.WakeLockMgr;

import java.lang.ref.WeakReference;

public final class CommUtilc extends AbsCommUtila {
    private boolean a;
    private int b;
    private ExecutHelper c;
    private WeakReference d;

    public CommUtilc(ExecutHelper arg2) {
        super();
        this.a = true;
        this.b = 0;
        this.c = arg2;
    }

    public static ExecutHelper a(CommUtilc arg1) {
        return arg1.c;
    }

    public static void a(CommUtilc arg0, int arg1) {
        arg0.b = arg1;
    }

    public final boolean a(ICommUtil arg6) {
        int v4 = -1;
        boolean v1 = true;
        this.a = true;
        this.b = 0;
        if (this.c != null && !TextUtils.isEmpty(this.c.j) && (!this.c.g || (SelinuxUtils.a())) && (this
                .c.d == v4 || SelinuxUtils.sdk() >= this.c.d) && (this.c.e == v4 || SelinuxUtils.sdk() <= this.c.e)) {
            LogUtil.e("commUtilc executeHelper.j ="+c.j+" , c.k = "+c.k);
            boolean v0 = CommUtils2.a(this.c.j, this.c.k) == 0 ? true : false;
            this.a = v0;
            if (this.a) {
                ExecutorHelper v0_1 = new ExecutorHelper();
                v0_1.a = this.c.a;
                v0_1.b = this.c.b;
                v0_1.c = this.c.c;
                v0_1.e = this.c.i;
                this.b = CommUtils2.a(arg6, this.c.k, v0_1);
            } else {
                this.b = 15;
            }

            if ((this.a) && this.b == 0) {
                return v1;
            }

            v1 = false;
        }

        return v1;
    }

   public static boolean b(CommUtilc arg1) {
        return arg1.a;
    }

    public final boolean b(ICommUtil arg5) {
        boolean v0;
        if (this.c == null) {
            v0 = true;
            return v0;
        }

        LogUtil.d("check_su_files repair " + this.c.k);
        v0 = ((Boolean) WakeLockMgr.a(arg5, new ChUtilsd(this), new Object[0])).booleanValue();
        LogUtil.d("check_su_files repair " + this.c.k + "..." + v0);
        try {
            if (this.d == null) {
                return v0;
            }

            this.d.get();
        } catch (Exception v1) {
        }

        return v0;
    }

    static int c(CommUtilc arg1) {

        return arg1.b;
    }
}

