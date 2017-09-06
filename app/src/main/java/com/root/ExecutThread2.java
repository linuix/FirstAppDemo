package com.root;

import com.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/11/7.
 */

public class ExecutThread2 extends Thread {

    private JavaRoot2 root2;
    private Process proess;

    public ExecutThread2(JavaRoot2 arg1, Process arg2) {
        super();
        root2 = arg1;
        proess = arg2;

    }

    @Override
    public void run() {
        try {
            int v0_2 = this.proess.waitFor();
            JavaRoot2.setI();
            LogUtil.d("executThread2:  ExeRootSolution process exit: " + v0_2);
        }
        catch(Throwable v0) {
            LogUtil.exception("ExeRootSolution waitFor throw e2", (Exception) v0);
        }

    }
}
