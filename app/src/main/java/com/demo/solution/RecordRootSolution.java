package com.demo.solution;

import android.content.Context;
import android.text.TextUtils;

import com.demo.entity.Entity;
import com.demo.entity.RootExtInfo;
import com.demo.utils.FileUtils;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;
import com.demo.utils.Utils;
import com.kingroot.sdk.root.CommonLog;
import com.root.helper.ThreadLocalWeakRef;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import http.demo.HttpMgr;
import http.demo.SolutionHelpers;
import http.utils.XmlFileSolute;


/**
 * Created by Administrator on 2016/10/18.
 */

public class RecordRootSolution {

    private static Context mContext;
    private static Entity entity;
    private static int flag;
    private static RecordRootSolution instance;
    private RecordRootSolution() {

    }
    public static boolean check(Context arg5, SolutionHelpers solutionHelpers, boolean arg7) {
        boolean v0 = false;
        ThreadLocalWeakRef.createThreadLocal();
        if(!a(solutionHelpers.l, new int[]{0, 1, 2, 3, 4, 5})) {
            ThreadLocalWeakRef.a(7003, "interface_type=" + solutionHelpers.l);
        }
        else
        {
            if((arg7) && SpfUtils.c(arg5, "solution_crash_" + solutionHelpers.b) == 1) {
                ThreadLocalWeakRef.a(7004, "sid=" + solutionHelpers.b);
                return v0;
            }
            int v2 = SpfUtils.c(arg5, "solution_fail_count_" + solutionHelpers.b);
            if(v2 >= 3) {
                ThreadLocalWeakRef.a(7059, "sid = " + solutionHelpers.b + ", failcount=" + v2);
                return v0;
            }

            v0 = true;
        }
        LogUtil.loge("这里需要注意:::  RecordRootSolution recordSolution check !!!! " +v0);
        return v0;
    }

/**
 *
 * 这的check函数调用
 * */
    public static boolean a(int arg4, int[] arg5) {
        boolean v0 = false;
        int v2 = arg5.length;
        int v1 = 0;
        while(v1 < v2) {
            if(arg4 == arg5[v1]) {
                v0 = true;
            }
            else {
                ++v1;
                continue;
            }

            return v0;
        }

        return v0;
    }

    public static RecordRootSolution getInstance(Context param, Entity entity1, int arg) {
        mContext = param;
        entity = entity1;
        flag = arg;
        instance = new RecordRootSolution();
        return instance;
    }

//    public void test() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                prepareXmlAndJarsDir(mContext, entity, flag);
//            }
//        }).start();
//    }

    /**
     * 准备网络下载的文件存放地址
     */
    public static SolutionManager prepareXmlAndJarsDir(Context context, Entity entity, int arg15) {
        LogUtil.loge("准备xml ,jars文件夹 ");
        SolutionHelpers[] v3_3;
        SolutionHelpers[] v4_1;
        SolutionHelpers[] v1_3 =null;
        int v1_1;
        SolutionManager solutionManager = null;
        File xmls = new File(entity.a(new String[]{"xmls"}));
        if (!xmls.exists()) {
            xmls.mkdirs();
        }
        File jars = new File(entity.a(new String[]{"jars"}));

        if (!jars.exists())
        {
            jars.mkdirs();
        }
        String v1 = SpfUtils.getMarsrootSharePreferences(context, "next_execute_solution_id");
        ///在这里执行读取重启方案的数据
        if ( reboot ==1)
        {
            solutionManager = getSolutionMgr(context,jars,xmls,getHelpes());
            return solutionManager;
        }
        //获取本地解决方案，
        if((arg15 & 4) == 4)
        {
            solutionManager = getLocalSolution(context, entity, xmls, jars);//本地获取解决方案，如果没有 ，在请求网络获取
            if (solutionManager != null)
            {
                return solutionManager;
            }
        }
        ///获取网络下载的方案
        LogUtil.e("使用网络下载");
        int v4;
        solutionManager = getSolutionFromNet(context, entity, xmls, jars);//这里写好solution_success_id
        if (solutionManager != null)
        {
            return solutionManager;
        }
        SolutionHelpers[] respSolutionHelpers = solutionManager.respSolutionHelpers;
        iteratorJars(jars, respSolutionHelpers);//迭代文件
         v1 = SpfUtils.getMarsrootSharePreferences(context, "solution_success_id");//取出solution_success_id
        if (!TextUtils.isEmpty(((CharSequence) v1)))
        {
            File v3 = new File(xmls, v1);
            if (!v3.exists())
            {
                v1_3 = respSolutionHelpers;
            }
            v1 = CommonLog.getkernelInfo(v3.getAbsolutePath());
            if (TextUtils.isEmpty(((CharSequence) v1))) {
                v1_3 = respSolutionHelpers;
            }
            ArrayList v3_1 = new ArrayList();
            v3_1.add(v1);
            //新增加的方法
            v1_3 = XmlFileSolute.getSolutionHelpers(v3_1);
            //这里的返回值是空，需要注意一下
            if (v1_3 == null) {
                v1_3 = respSolutionHelpers;
            }
            if (v1_3.length <= 0)
            {
                v1_3 = respSolutionHelpers;
            }
            v4 = v1_3.length;
            int v3_2;
            for (v3_2 = 0; v3_2 < v4; ++v3_2)
            {
                //这里赋值给solutionHelper.n = 文件的路径
                v1_3[v3_2].n = String.valueOf(jars.getAbsolutePath()) + File.separator + v1_3[v3_2].b;
            }
            if (v1_3 == null) {
                v4_1 = new SolutionHelpers[0];
            } else {
                v4_1 = v1_3;
            }
            if (respSolutionHelpers == null) {
                v3_3 = new SolutionHelpers[0];
            } else {
                v3_3 = respSolutionHelpers;
            }
            v1_3 = new SolutionHelpers[v4_1.length + v3_3.length];
            System.arraycopy(v4_1, 0, v1_3, 0, v4_1.length);
            System.arraycopy(v3_3, 0, v1_3, v4_1.length, v3_3.length);
            if (v1_3 == null) {
                v1_3 = new SolutionHelpers[0];
            }
            LogUtil.loge("完成solution");

            solutionManager.respSolutionHelpers =v1_3;

            return solutionManager;
        }
        ///************
        return solutionManager;
    }
    /**
     * 重启标志
     *
     * **/
    private  static int reboot=-1;
    public  static void setReboot(int arg)
    {
        reboot =arg;
    }

    /**************/
    private static ArrayList mList;

    public static void setSolutionList(ArrayList list)
    {
        mList =list;
    }
    private static SolutionHelpers[] getHelpes()
    {
        return XmlFileSolute.getSolutionHelpers(mList);
    }
    /**
     * 获取本地方案
     * */
    private static SolutionManager getLocalSolution(Context context, Entity entity, File xmls, File jars) {

        LogUtil.e("查找本地方案是否存在可以用");
        SolutionManager solutionMgr =null;
        String path  = SpfUtils.getMarsrootSharePreferences(context,"solution_success_id");
        if (!TextUtils.isEmpty(path))
        {
            File file = new File(xmls,path);
            if (!file.exists())
            {
                return null;
            }
            path =Utils.readLocalSolution(path);
            if (TextUtils.isEmpty(path))
            {
                return  null;
            }
            ArrayList list = new ArrayList();
            list.add(path);
            SolutionHelpers[] helpers = XmlFileSolute.getSolutionHelpers(list);
            if (helpers == null)
            {
                return null;
            }
            if (helpers.length <=0)
            {
                return  null;
            }
            for (int index =0;index<helpers.length;index++)
            {
                helpers[index].n =String.valueOf(jars.getAbsoluteFile()+File.separator+helpers[index].b);
            }
            solutionMgr = new SolutionManager();
            LogUtil.e("存在本地的方案，选择本地方案");
            solutionMgr.executorSolutionHelpers = helpers;
//            LogUtil.e("本地方案赋值完成，可以直接调用root 功能");
//            RootMgr.setSolutionHelpers(helpers);
            LogUtil.e("本地方案存在，已经赋值完成! 点击root按钮即可");
        }
        LogUtil.e("查找本地方案完成 ，返回的是 solutionMgr = "+solutionMgr);
        return  solutionMgr;
    }

    private static void iteratorJars(File jars, SolutionHelpers[] solutionHelperses) {
        int v4;
        int v1_1;
        if (jars.isDirectory()) {
            File[] v8 = jars.listFiles();
            if (v8 == null)
            {
                return;
            }
            int v9 = v8.length;
            for (v4 = 0; v4 < v9; ++v4) {
                String v10 = v8[v4].getName();
                if (!v10.equals("131")) {
                    if (solutionHelperses != null) {
                        int v11 = solutionHelperses.length;
                        v1_1 = 0;
                        while (true) {
                            if (v1_1 >= v11) {
                                v1_1 = 0;
                            } else if (solutionHelperses[v1_1].b.equals(v10)) {
                                v1_1 = 1;
                            } else {
                                ++v1_1;
                                continue;
                            }

                            break;
                        }
                    } else {
                        v1_1 = 0;
                    }
                    if (v1_1 != 0) {
                        break;
                    }
                    delete(v10);
                }
            }
        }
    }


    public static void delete(String arg6) {
        FileUtils.isDelete(entity.a(new String[]{"jars", arg6}));
        FileUtils.isDelete(entity.a(new String[]{"xmls", arg6}));
    }


    /**
     * 创建yis_cfg.txt文件
     */
    private static void createYISConfi(Entity arg5, SolutionManager arg6) {
        File v1 = new File(arg5.a(new String[]{"yis_cfg.txt"}));
        if (v1.exists()) {
            arg6.pcRootInfo = new RootExtInfo();
            arg6.mobileRootInfo = new RootExtInfo();
            String v0 = CommonLog.getkernelInfo(v1.getAbsolutePath());
            if (v0 == null) {
                return;
            }
            if (v0.trim().length() == 0) {
                return;
            }
            try {
                JSONObject v2 = new JSONObject(v0);
                JSONObject v0_2 = v2.getJSONObject("pcRootInfo");
                v2 = v2.getJSONObject("mobileRootInfo");
                arg6.pcRootInfo.canRoot = v0_2.getInt("canRoot");
                arg6.pcRootInfo.useTime = v0_2.getInt("useTime");
                arg6.pcRootInfo.succUsers = v0_2.getInt("succUsers");
                arg6.pcRootInfo.succRate = v0_2.getInt("succRate");
                arg6.mobileRootInfo.canRoot = v2.getInt("canRoot");
                arg6.mobileRootInfo.useTime = v2.getInt("useTime");
                arg6.mobileRootInfo.succUsers = v2.getInt("succUsers");
                arg6.mobileRootInfo.succRate = v2.getInt("succRate");
            } catch (Exception v0_1) {
                v0_1.printStackTrace();
                v1.delete();
            }
        }
    }

    static SolutionManager solutionMgrNet = null;

    /**
     * 网络请求root解决方案
     */
    private static SolutionManager getSolutionFromNet(Context context, Entity entity, File xmls, File jars) {
        LogUtil.loge("联网下发方案列表 biu biu ~~~~~~");
        //**********网络请求解决方案
        //test start
        HttpMgr httpManagers = new HttpMgr(context);
        solutionMgrNet=  httpManagers.getSolutions(context);
        //test over
        if (solutionMgrNet == null) {
            return solutionMgrNet;
        }
        solutionMgrNet= getSolutionMgr(context,jars,xmls,solutionMgrNet.respSolutionHelpers);
        return solutionMgrNet;
    }
    private static SolutionManager getSolutionMgr(Context context,File jars,File xmls, SolutionHelpers[] solutionHelpers)
    {
        if (solutionHelpers == null)
        {
            return null;
        }
        if (solutionHelpers != null && solutionHelpers.length > 0) {
            String[] v5 = new String[solutionHelpers.length];
            int solutionLength = solutionHelpers.length;
            int v1;
            for (v1 = 0; v1 < solutionLength; ++v1) {
                SolutionHelpers SolutionHelpers = solutionHelpers[v1];
                File v0 = new File(xmls, SolutionHelpers.b);
                try {
                    if (!v0.exists() && v0.length()<0) {
                        //*********记录文件
                        Utils.writeSolutionFiles(SolutionHelpers.m.getBytes(), v0.getAbsolutePath());
                    }
                    //*******
                    v5[v1] = SolutionHelpers.b;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**
                 * 保存文件地址
                 * */
                SolutionHelpers.n = String.valueOf(jars.getAbsolutePath()) + File.separator + SolutionHelpers.b;
            }
            if (v5.length == 0) {
                LogUtil.loge("记录 yis_cfg.txt文件==== ");
                File v1_1 = new File(entity.a(new String[]{"yis_cfg.txt"}));
                v1_1.delete();
                if (solutionMgrNet != null && solutionMgrNet.pcRootInfo != null && solutionMgrNet.mobileRootInfo != null) {
                    JSONObject v0_2 = new JSONObject();
                    JSONObject v2 = new JSONObject();
                    JSONObject v4_1 = new JSONObject();
                    try {
                        v2.put("canRoot", solutionMgrNet.pcRootInfo.canRoot);
                        v2.put("useTime", solutionMgrNet.pcRootInfo.useTime);
                        v2.put("succUsers", solutionMgrNet.pcRootInfo.succUsers);
                        v2.put("succRate", solutionMgrNet.pcRootInfo.succRate);
                        v4_1.put("canRoot", solutionMgrNet.mobileRootInfo.canRoot);
                        v4_1.put("useTime", solutionMgrNet.mobileRootInfo.useTime);
                        v4_1.put("succUsers", solutionMgrNet.mobileRootInfo.succUsers);
                        v4_1.put("succRate", solutionMgrNet.mobileRootInfo.succRate);
                        v0_2.put("pcRootInfo", v2);
                        v0_2.put("mobileRootInfo", v4_1);
                        String v0_3 = v0_2.toString();
                        LogUtil.e("saveRootExtInfo.json = " + v0_3);
                        //写文件*******写入获取的文件信息
                        Utils.writeSolutionFiles(v1_1, new String[]{v0_3});
                        //*****
                    } catch (Exception e) {
                        e.getMessage();
                        LogUtil.exception("获取网络解决方案异常 写json位置", e);
                    }
                } else {
                    LogUtil.loge("后台数据不完整，不存储YIS！");
                }
                return solutionMgrNet;
            }
            LogUtil.loge("存储方案下发顺序列表 记录在spf中");
            SpfUtils.removeMarsRootSharedPreferences(context, "solution_id_list", TextUtils.join(",", ((Object[]) v5)));
        }
        return  solutionMgrNet;
    }

}
