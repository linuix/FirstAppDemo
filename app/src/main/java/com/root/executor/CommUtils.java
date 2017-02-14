package com.root.executor;

import com.demo.process.RetValue;
import com.root.dao.ICommUtil;
import com.root.dao.IJavaProcessh;
import com.root.helper.JavaProcessk;
import com.root.helper.JavaSolutionHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CommUtils extends ICommUtil {
    private IJavaProcessh a;

    public CommUtils(IJavaProcessh arg1) {
        super();
        this.a = arg1;
    }

    public final RetValue a(String arg5) {
        JavaSolutionHelpers v0 = this.a.a(arg5);
        return new RetValue(arg5, Integer.valueOf(v0.a), v0.b, "");
    }

    public final List a(List arg4) {
        ArrayList v1 = new ArrayList();
        Iterator v2 = arg4.iterator();
        while(v2.hasNext()) {
            ((List)v1).add(this.a((String)v2.next()));// 拿到了进程反馈回来的对象retvalue，由许多的命令读取出来
        }
        return ((List)v1);
    }

    public final boolean a() {
        boolean v0 = this.a != null ? true : false;
        return v0;
    }
}

