package com.demo.process;

import com.demo.utils.LogUtil;
import com.demo.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2016/10/27.
 */

public class RootProcess {

    private static Object locak = new Object();
    private ByteArrayOutputStream baos_str;
    private ByteArrayOutputStream baos_err;
    private Process processBuilder;
    private DataOutputStream data_out_put_stream;
    private RootThread stThread;
    private RootThread errThread;

    public RootProcess(String arg) {
        if (arg != null && arg.length() > 0) {
            init(arg);
        }
    }

    public static Object getObject(RootProcess arg) {
        return arg.locak;
    }

    private void init(String arg) {
        try {
            baos_err = new ByteArrayOutputStream();
            baos_str = new ByteArrayOutputStream();
            //判断sh文件是否存在，在执行
            File file = new File(arg);
            boolean flag = file.exists();
            LogUtil.loge("path = " + file.getAbsolutePath() + " , flag =" + flag);
            if (arg != null && arg.length() != 0) {
                if (arg.startsWith("/") && !(new File(arg).exists())) {
                    LogUtil.loge("init processs not found ");
                    throw new FileNotFoundException();
                }
            }
            processBuilder = new ProcessBuilder(Arrays.asList(arg.split(" "))).start();
            synchronized (locak) {
                locak.wait(10);
            }
            //执行sh 命令，是不能执行这个方法，但是执行其他的，就可以，这个sh命令。是类似于打开读写操作.进程会一直处于等待状态，只要写入 exit\n字符串，即可停下来
//            int ret = processBuilder.exitValue();
//            if (ret != 0) {
//                throw new IOException();
//            }
//            LogUtil.loge("get exitvalue = " + ret);

            data_out_put_stream = new DataOutputStream(processBuilder.getOutputStream());
            //准备线程读取流信息
            stThread = new RootThread(this, "str", processBuilder.getInputStream(), baos_str);
            errThread = new RootThread(this, "err", processBuilder.getErrorStream(), baos_err);
            synchronized (locak) {
                locak.wait(10);
            }
            RootThread.setIsRunning(true);
            stThread.start();
            errThread.start();
            LogUtil.loge("process inited ok &  thread str err start !");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.exception("init IOException ",e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            LogUtil.exception("init InterruptedException ",e);
        }
    }

    /**
     * 关闭所有的流管道
     */
    public void closeAll()

    {
        LogUtil.loge("close stream !!");
        if (stThread != null) {
            stThread.interrupt();
            stThread = null;
        }
        if (errThread != null) {
            errThread.interrupt();
            errThread = null;
        }
        /*if (data_out_put_stream != null) {
            try {
                data_out_put_stream.write("exit\n".getBytes());
                data_out_put_stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Utils.close(data_out_put_stream);
            }
        }*/
        if (processBuilder != null) {
            processBuilder.destroy();
            processBuilder = null;
        }
        Utils.close(baos_err);
        Utils.close(baos_str);
        RootThread.setIsRunning(false);
    }

    /**
     * 使用默认时间 120000
     */
    public RetValue execut(String arg) {
        return execut(new ProcessHelper(arg, arg, 120000));
    }

    /**
     * 需要传递控制进程时间
     */
    public RetValue execute(String arg, long time) {
        return execut(new ProcessHelper(arg, arg, time));
    }

    /**
     * 读取进程反馈信息
     */
    private RetValue execut(ProcessHelper object) {
        RetValue result = null;
        int v0_1 = 0;
        long v2 = 0;
        if (object != null) {
            try {
                checkArg(object, v0_1, v2);
                //重置流里边的内容
                streamRest();
                if(data_out_put_stream == null)
                {
                    LogUtil.d("创建的进程失败 data_out_put_stream == null，不读写进程!");
                    return result;
                }
                data_out_put_stream.write((String.valueOf(object.b) + "\n").getBytes());
                data_out_put_stream.flush();
                synchronized (locak)
                {
                    locak.wait(10);
                }
                data_out_put_stream.writeBytes("echo :RET=$?\n");
                /**
                 * 在使用调试模式执行的时候，会首先执行线程内的方法，然后才会call readProcessStatus()
                 * 但在运行的时候，会先执行readProcessStatus()，
                 * 在readStatus函数中修改了等待时间，增加了循环取的次数，但是，这里的
                 * 阻塞现象还是要解决(第二次写的时候，还是阻塞)，一直不能读取到反馈信息
                 * 这里只会写入一次，线程那边会读取到对应的流，
                 * 主要问题；
                 * 由于第一次写完之后，调用了break ,导致读取的流不再继续获取，那么在第二次调用的时候，
                 * 已经没有经过线程，导致baos_str_stream()读取不到信息
                 * */
                data_out_put_stream.flush();//第一次读取到这里的时候，thread会读取到数据，这个时候。strstream会有数据
//                LogUtil.loge("write RET over ");
                //但是第二次之后，就不能读取到数据,流信息在这里阻塞了。线程那边没有读取到
                long sysTime = System.nanoTime();
                long tmpTm = 0;
                do
                {
                    if (object.c != 0)
                    {
                        tmpTm = object.c - (System.nanoTime() - sysTime) / 100000;
                        if (tmpTm < 0)
                        {
//                            closeAll();
                            throw new TimeoutException("Exc timeout !!");
                        }
                    }
                    //读取进程信息,读取进程信息
                    result = readProcessStatus(object, tmpTm);
                    if (result == null)
                    {
//                        LogUtil.loge("没有反馈信息，continue " + tmpTm);
                        continue;
                    }
//                    LogUtil.loge("break @@@@@");
//                    LogUtil.loge("status  "+result.toString());
                    break;
                } while (true);
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.exception(" IOException " ,e);
            } catch (InterruptedException e) {
                e.printStackTrace();
                LogUtil.exception("InterruptedException",e);
            } catch (TimeoutException e) {
                e.printStackTrace();
                LogUtil.exception("time exception " ,e);
            }
        }
        return result;
    }

    /**
     * 清楚管道流内的缓存数据
     */
    private void streamRest()

    {
        LogUtil.loge("clear stream ");
        if (baos_str != null) {
            baos_str.reset();
        }
        if (baos_err != null) {
            baos_err.reset();
        }
    }

    /**
     * 读取进程的反馈信息
     */
    private RetValue readProcessStatus(ProcessHelper object, long tmpTm) {
//        LogUtil.loge("readProcess status ");
        String tmp = null;
        int tag = -1;
        RetValue value=null;
        int flag;
        //首先检测str流的信息，是否包含了RET=0的字符串，如果是RET=""，那么出现了最后的字符串没写完
//        LogUtil.loge("*** "+new String(baos_str.toByteArray()));
        if (new String(baos_str.toByteArray()).lastIndexOf(":RET=") == tag) {
            //出现了空的状态，那么
            flag = 1;
        } else {
            flag = 0;//=0表示已经写完数据
        }
        if (flag == 0) {
//            LogUtil.loge("00000000000");
            StringBuilder[] builders = new StringBuilder[2];
            builders[0] = new StringBuilder();//str_stream
            builders[1] = new StringBuilder();//err_stream
            ByteArrayOutputStream[] baos = new ByteArrayOutputStream[]{baos_str, baos_err};
            int index = 0;
            while (index < baos.length) {
                builders[index].append(new String(baos[index].toByteArray()));//取出每一个留信息
                ++index;
            }
            String str = builders[0].toString();
            String err = builders[1].toString();

            if (str.lastIndexOf(":RET=") != tag) {
                streamRest();
                if (str.lastIndexOf(":RET=0") != tag) {
//                    LogUtil.loge("00001111");
//                    LogUtil.loge("=0=  "+object.sdk_gt18+" - "+Integer.valueOf(0)+" - "+  str.substring(0, str.lastIndexOf(":RET="))+" - "+err);
//                   result = new RetValue(object.cmd, Integer.valueOf(0), new String(str.substring(str.lastIndexOf(":RET="))), err);
//                    LogUtil.d("return 0000  + "+result.toString());
                     value = new RetValue(object.a, Integer.valueOf(0), str.substring(0, str.lastIndexOf(":RET=")),err);

                    return value;
                } else {
                    if (str.lastIndexOf(":RET=EOF") == tag && err.lastIndexOf(":RET=EOF") == tag) {
//                        LogUtil.loge("11111");
//                        LogUtil.loge("=1=  "+object.sdk_gt18+" - "+Integer.valueOf(1)+" - "+  str.substring(0, str.lastIndexOf(":RET="))+" - "+err);
                        value = new RetValue(object.a, Integer.valueOf(1), new String(str.substring(str.lastIndexOf(":RET="))), err);
//                        LogUtil.d("return !!!!  + "+value.toString());
                        return value;
                    }
                }
            }
        }

        //没有出现返回值的情况的时候，那么需要在这里等待
        synchronized (locak) {
            try {
                /**
                 *原始是等待了tmTime=120000毫秒
                 * 这里调整了等待的时间，增加循环的次数。那么即使在现执行了readStatus函数，
                 * 那么下一次也会读取到信息,
                 *
                 * */
//                LogUtil.loge("11111111111111");
                locak.wait(10000);//等到时间结束之后，在返回
//                locak.notifyAll();//看看
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        LogUtil.loge("33333");
        return value;
    }

    /**
     * 检测参数的合法性
     */
    private void checkArg(ProcessHelper object, int v0_1, long v2) {
        if (object.a == null) {
            v0_1 = 1;
        } else if (object.a.length() <= 0) {
            v0_1 = 1;
        } else if (object.b == null) {
            v0_1 = 1;
        } else if (object.b.length() > 0) {
            v0_1 = 0;
        } else {
            v0_1 = 1;
        }
        if (v0_1 != 0) {
            throw new IllegalArgumentException("Cmd Argument Invalid");
        }
        if (object.c < v2) {
            throw new IllegalArgumentException("Cmd Argument Invalid");
        }

    }


}
