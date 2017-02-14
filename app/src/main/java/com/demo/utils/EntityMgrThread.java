package com.demo.utils;

import android.content.Context;

import com.demo.entity.EntityManager;

/**
 * Created by Administrator on 2016/11/23.
 */

public class EntityMgrThread implements  Runnable {

    private Context context;
    private int flag ;
    public EntityMgrThread(int arg,Context mcontext)
    {
        context= mcontext;
        flag =arg;

    }

    @Override
    public void run() {
        EntityManager.init(flag, context);
    }
}
