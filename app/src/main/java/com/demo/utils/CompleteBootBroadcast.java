package com.demo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Administrator on 2016/11/24.
 *
 * 监听手机重启广播。
 * 执行root的过程中，可能会导致手机重启，需要监听到这个行为
 * 在手机重启后，启动root，继续执行执行正在root的行为，不需要再次执行
 * 初始化和网络请求
 *
 */

public class CompleteBootBroadcast extends BroadcastReceiver {
    public CompleteBootBroadcast(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
            LogUtil.e("手机重启 ！！ "+action);
            RebootUtils.init(context);
    }
}
