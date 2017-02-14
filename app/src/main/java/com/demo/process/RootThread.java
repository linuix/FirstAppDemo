package com.demo.process;

import com.demo.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/10/27.
 */

public class RootThread extends Thread {

    private InputStream in;
    private OutputStream out;
    private Object lock;
    private static boolean isRunning = true;

    public RootThread(RootProcess arg1, String name, InputStream arg2, OutputStream arg3) {
        super(name);//添加线程的名称
        in = arg2;
        out = arg3;
        lock = RootProcess.getObject(arg1);
    }

    /**
     * 增加了synchronized关键字
     */
    public static synchronized void setIsRunning(boolean arg) {
        isRunning = arg;
    }

    /**
     * 读取进程中err ,str流信息，作为反馈标志
     */
    @Override
    public void run() {
        /**
         * 这里的缓冲区，如果设置成为4096，那么一次就可以读取到所有的流，如果是1024，那么，只能读取到一部分
         * 不能完全读取，
         * 即使是这样，也不能读取到最后的流，
         * */

        if (isRunning) {
            byte[] buf = new byte[1024];
            int len = -1;
            try {
                LogUtil.loge(" run()");
                while (true) {
                    len = in.read(buf);
                    if (len == -1) {
                        out.write(":RET=EOF".getBytes());
                        out.flush();
                        synchronized (lock) {
                            lock.notifyAll();
//                            LogUtil.loge("notify 222");
                        }
                        break;
                    }
                    if (len <= 0) {
                        continue;
                    }
                    /**
                     * 这只会读取一次
                     *
                     * */
//                    LogUtil.loge("read length > 0 break");
//                    break;
//                    LogUtil.loge("写进程  每次读取到的长度 = " + len + " byte = " + new String(buf));
                    out.write(buf, 0, len);
                    out.flush();
                    synchronized (lock) {
                        lock.notifyAll();
//                        LogUtil.loge("notitfy 1111 ");
                    }
//                    LogUtil.loge("通知其他等待进程  !!");
                    //写进程日志保留，
//                HttpArgUtils.writeFile(in, "test.txt");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
