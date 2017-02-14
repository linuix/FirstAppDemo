package com.demo.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by Administrator on 2016/11/21.
 *
 *
 */

public class MyContextWrapper extends ContextWrapper {
    private String a;
    private AssetManager b;
    private Resources c;
    private Resources.Theme d;

    public  MyContextWrapper(Context arg7,String arg8)
    {
        super(arg7);
        this.a = arg8;
        try {
            Object v0_1 = AssetManager.class.newInstance();
            v0_1.getClass().getMethod("addAssetPath", String.class).invoke(v0_1, this.a);
            this.b = ((AssetManager)v0_1);
        }
        catch(Exception v0) {
            v0.printStackTrace();
        }

        Resources v0_2 = super.getResources();
        this.c = new Resources(this.b, v0_2.getDisplayMetrics(), v0_2.getConfiguration());
        this.d = this.c.newTheme();
        this.d.setTo(super.getTheme());
    }
    public final Resources getResources() {
        return this.c;
    }
}
