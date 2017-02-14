package com.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.test.Test;
import com.demo.utils.Const;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.root.RootMgr;
import com.mars.root.R;
public class MainActivity extends Activity {

    private TextView tv;
    private static Handler handler;
    private MyBroadcase broadcase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main);

        tv = (TextView) findViewById(R.id.tv);
    }



    public  static void setHandler(Handler arg)
    {
        handler = arg;
    }

    /**
     * 一些准备工作都在application准备好，这里知己测试对应的功能
     * 修改掉一开始直接在application层面执行的任务，更改为
     * 手动测试，包括
     * initsdk()
     * http()
     * execute()
     * 这几个功能
     */
    public void test(View view) {
        switch (view.getId()) {
            case R.id.init:
                //初始化准备
//				InitConfig.initSdk();
                handler.sendEmptyMessage(Const.INT_CODE);
                show("intSdk() test!!");
                break;

            /**
             * 这里应该包括了
             * 网络请求解决方案列表和解析获取解决方案的url:: getSolutions()
             * 网络下载解决方案，准备好解决方案供给执行
             *
             * */
            case R.id.http:
                //网络请求
                handler.sendEmptyMessage(Const.HTTP_TEST);
                show("http getSolutions() !!!");
//测试参数的正确性
//				String deviceXmlInfo = XmlFileSolute.getDeviceXmlInfo();
//				tv.setText("deviceXmlInfo:"+deviceXmlInfo);
                //测试获取deviceXmlInfo成功
//                MyAsyncTask task =new MyAsyncTask();
//                task.execute();

                break;
            case R.id.execute:
                //执行root
                handler.sendEmptyMessage(Const.ROOT_TEST);
                Toast.makeText(this, "test root", Toast.LENGTH_SHORT).show();
//                MyAsyncTask task = new MyAsyncTask();
//                task.execute();
                break;
            case R.id.find:
                show("查看文件信息!");
//                handler.sendEmptyMessage(Const.FIND_FILE);
//                XmlFileSolute.test();
                MyAsyncTask task = new MyAsyncTask();
                task.execute();
                break;
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Void, String> {
        Context context = App.getContext();
        RootMgr rootMgr = null;
        StringBuilder sb = new StringBuilder("test sindex :");

        @Override
        protected String doInBackground(Void... params) {


            String ret = Test.getkrcfg_txt(InitConfig.mContext, InitConfig.entity);
//            boolean ret = InitConfig.initSdk();
//            if (!ret)
//            {
//                rootMgr = new RootMgr(context, InitConfig.entity ,handler);
//                rootMgr.execteRoot(3);
//            }

//            int index = 0;
//            int lent = rootMgr.sids.length;
           /* while (index < lent) {
//                javaRoot = new JavaRoot(context, InitConfig.entity, "798");
//                flag = javaRoot.beforeRoot();
                rootUtil = RootUtil.getInstanc(context,rootMgr.sids[index]);
                flag =  rootUtil.extract(InitConfig.entity.file+"test","777");
                sb.append(flag).append("" + index);
                index++;
            }*/
//			boolean r =rootUtil.extract(javaRoot.play,"777");
//            String deviceXmlInfo = XmlFileSolute.getDeviceXmlInfo();
//            if (deviceXmlInfo != null)
//            {
//                return  deviceXmlInfo;
//            }

            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            tv.setText("" + s);
        }


    }

    /**
     * toast显示函数
     */
    private void show(String msg) {
        Toast toast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        broadcase  = new MyBroadcase();
        IntentFilter filter  = new IntentFilter();
        filter.addAction(Const.UP_DATE_UI);
        registerReceiver(broadcase,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(broadcase != null)
        {
            unregisterReceiver(broadcase);
            broadcase = null;
        }
    }


    private String getName()
    {
         return this.getPackageName();
    }

    /**
     * 更新ui的广播
     */
    class MyBroadcase extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
            {
                LogUtil.e("然并卵 ！！！");
                tv.setText("NOt Root,Try again!");
            }
            if (action.equals(Const.UP_DATE_UI))
            {
                String data = intent.getStringExtra("root");
                boolean flag = intent.getBooleanExtra("flag",false);//默认取出false
                tv.setText("Root 了吗？"+data+" \n状态标识：flag ="+flag+"\t"+getName());
            }
        }
    }


}
