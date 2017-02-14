package com.root.dao;


/**
 * Created by Administrator on 2016/11/8.
 */

public abstract class AbsCommUtila implements ICommoUtil2 {
    private boolean a;
    private boolean b;

    public AbsCommUtila() {
        super();
        this.a = false;
        this.b = false;
    }

    @Override
    public boolean a() {
        boolean v0_1;
        v0_1 = this.b;
        return v0_1;
    }

    public abstract boolean a(ICommUtil arg1);

    public abstract boolean b(ICommUtil arg1);



    @Override
    public boolean c(ICommUtil arg4) {
        boolean v2;
        boolean v0 = false;
        if(this.a(arg4)) {
            v2 = false;
        }
        else {
            v2 = true;
        }
        this.b = v2;
        this.a = true;
        if(!this.b) {
            v0 = true;
        }
        return v0;
    }

    @Override
    public boolean d(ICommUtil arg2) {
        boolean v0_1 = true;

        if(!this.a) {
            this.c(arg2);
        }

        if(!this.b) {
        }
        else {
            v0_1 = this.b(arg2);
        }

        return v0_1;
    }
}
