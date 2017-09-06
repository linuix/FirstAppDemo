package com.kingroot.sdk.root;

import android.content.Context;
import android.os.Handler;

import com.demo.entity.Entity;
import com.demo.solution.RecordRootSolution;
import com.demo.solution.SolutionManager;
import com.demo.utils.LogUtil;
import com.root.RootMgr;

import java.io.File;
import java.util.ArrayList;

import http.demo.HttpMgr;
import http.demo.SolutionHelpers;


/**
 * Created by Administrator on 2016/10/15.
 */

public class YunRootExcutor {
    private Context context;
    private String kd_file;
    private Entity mEntity;
    private CommonLog commonLog;
    private boolean init_flag;
    private Handler handler;
    public SolutionManager solutionMgr;

    public YunRootExcutor(Context param, Entity entity) {
        LogUtil.loge("YunExcutor.<init>");
        kd_file = entity.file.getAbsolutePath() + File.separator + "kd";
        context = param;
        mEntity = entity;
        commonLog = CommonLog.getInstanc(param);
        handler = new Handler();//这里需要传递进来自己的looper对象
        init_flag = false;
    }

    /**
     * return -1 失败
     * 0 成功
     * <p>
     * <p>
     * 读取成功之后，solutionMgr存在数据 solutionHelpers，这个时候可以调用download方法，把solution全部下载下来
     */
    //test

    /**
     * 判断读取tag
     */
    private int getTag(int arg) {
        return ((arg & 1) == 1) ? 1 : 0;
    }


    public final int prepare(int arg17) {//arg17判断参数，
        LogUtil.loge("YunExcutor.prepare()");
        int v1;
        int v2;
        String v1_2 = null;
        int retTag = getTag(arg17);
        //首先是记录日志信息，
        commonLog.recordEMID(200042, 0, "", "", handler, new Object[0]);
        solutionMgr = RecordRootSolution.prepareXmlAndJarsDir(this.context, this.mEntity, arg17);

        if (this.solutionMgr != null && this.solutionMgr.a == 0 && this.solutionMgr.mobileRootInfo != null) {
            v2 = 200043;
            String v4 = "";
            String v5 = "";
            Object[] mobileRootInfoObj = new Object[5];

            mobileRootInfoObj[0] = Integer.valueOf(this.solutionMgr.mobileRootInfo.canRoot);
            mobileRootInfoObj[1] = Integer.valueOf(this.solutionMgr.mobileRootInfo.succRate);
            mobileRootInfoObj[2] = Integer.valueOf(this.solutionMgr.mobileRootInfo.useTime);
            mobileRootInfoObj[3] = Integer.valueOf(this.solutionMgr.mobileRootInfo.succUsers);

            int v8 = 4;
            SolutionManager solutionMgr = this.solutionMgr;
            v1 = solutionMgr.mobileRootInfo == null || solutionMgr.mobileRootInfo.canRoot != 1 ? 0 : 1;
            //执行之后， v1 =0
            retTag = solutionMgr.pcRootInfo == null || solutionMgr.pcRootInfo.canRoot != 1 ? 0 : 1;
            //执行之后。retTag =0
            if (v1 != 0) {
                v1 = retTag != 0 ? 0 : 1;
            } else if (retTag != 0) {
                v1 = 2;
            } else {
                v1 = 3;
            }
            mobileRootInfoObj[v8] = Integer.valueOf(v1);
            commonLog.recordEMID(v2, 0, v4, v5, handler, mobileRootInfoObj);
            String v8_1 = "";
            if (this.solutionMgr != null) {
                SolutionHelpers[] solutionHelpers = this.solutionMgr.respSolutionHelpers;
                if (solutionHelpers != null) {
                    int v9 = solutionHelpers.length;
                    ArrayList helperArray = new ArrayList();
                    int v10;
                    for (v10 = 0; v10 < v9; ++v10) {
                        SolutionHelpers solutionHelper = solutionHelpers[v10];
                        if (RecordRootSolution.check(this.context, solutionHelper, mEntity.f)) {
                            helperArray.add(solutionHelper);
                            boolean v1_1 = (this.init_flag) || solutionHelper.l == 5 ? true : false;
                            this.init_flag = v1_1;
                            v1_2 = String.valueOf(v8_1) + solutionHelper.sindex + "_";

                        } else {
                            LogUtil.loge("不允许执行, sid = " + solutionHelper.sindex + ", KError.code = ");
                            RecordRootSolution.delete(solutionHelper.sindex);
                            v1_2 = v8_1;
                        }
                        v8_1 = v1_2;
                    }

                    v2 = helperArray.size();
                    /**
                     * 准备好download加载的数据源
                     * */

                    //在这里写本地数据，为后续读取调用重启手机或者其他时候调用
                    this.solutionMgr.respSolutionHelpers = (SolutionHelpers[]) helperArray.toArray(new SolutionHelpers[v2]);
//                    retTag = v2;
                    LogUtil.e("准备好下载解决方案jar报的数组 this.solutionMgr.respSolutionHelpers " + this.solutionMgr.respSolutionHelpers + "\n"

                            + "可以点击下载"
                    );
                    retTag = 0;
                    v1 = v9;
                } else {
                    retTag = -1;
                    v1 = 0;
                }
                LogUtil.e("方案执行列表 = " + v8_1);
                commonLog.recordEMID(200021, 0, "", "", handler, new Object[]{Integer.valueOf(v1), v8_1, Integer.valueOf(retTag)});
                return retTag;
            }
            commonLog.recordEMID(200043, 1, "", "Network Fail.", handler, new Object[0]);
        } else {
            LogUtil.e("不能请求网络，只能是网络问题");
            retTag=-1;
        }
        return retTag;
    }

    /**
     * 这里把solutionMgr.executorSolutionHelpers赋值，在执行的时候，直接启用
     */
    public boolean downloadSolution() {
        boolean flag = true;
        ArrayList list = new ArrayList();
        if (solutionMgr.respSolutionHelpers != null) {
            SolutionHelpers[] solutions = solutionMgr.respSolutionHelpers;
            for (int i = 0; i < solutions.length; i++) {

                SolutionHelpers solutionHelpers = solutions[i];
                LogUtil.e("准备方案: sid =" + solutionHelpers.sindex);
                //调用http执行下载
                boolean tag = RecordRootSolution.check(context, solutionHelpers, mEntity.f);
                if (!tag) {
                    LogUtil.e("不能使用的solution sid =" + solutionHelpers.sindex);
                }

                boolean downloadTag = HttpMgr.downloadSolutionJars(context, solutionHelpers);
                if (downloadTag) {
                    LogUtil.e("下载jar成功");
                    flag = downloadTag;

                    list.add(solutionHelpers);
                } else {

                    flag = downloadTag;
                    LogUtil.e("下载jar失败");
                }
            }

            /**
             * 这里的executorSolutionHelpers就是下载好的可执行文件
             * */
            solutionMgr.executorSolutionHelpers = (SolutionHelpers[]) list.toArray(new SolutionHelpers[list.size()]);

            if (solutionMgr.executorSolutionHelpers == null) {
                LogUtil.e("没有下载成功解决方案，网络出现异常信息，哎 ~~~~~~~~~~~");
                solutionMgr.a = -60000;
                return false;
            }


            LogUtil.e("设置root解决方案文件");

            RootMgr.setSolutionHelpers(solutionMgr.executorSolutionHelpers);

            LogUtil.e("设置root解决方案文件成功！@！！！！ Boom boom ~~~~~");
        }
        return flag;
    }
}
