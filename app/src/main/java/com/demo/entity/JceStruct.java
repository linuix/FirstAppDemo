package com.demo.entity;

import java.io.Serializable;

import http.helper.HelperA;
import http.helper.HelperC;

public abstract class JceStruct implements Serializable {
    public static final byte BYTE = 0;
    public static final byte DOUBLE = 5;
    public static final byte FLOAT = 4;
    public static final byte INT = 2;
    public static final int JCE_MAX_STRING_LENGTH = 104857600;
    public static final byte LIST = 9;
    public static final byte LONG = 3;
    public static final byte MAP = 8;
    public static final byte SHORT = 1;
    public static final byte SIMPLE_LIST = 13;
    public static final byte STRING1 = 6;
    public static final byte STRING4 = 7;
    public static final byte STRUCT_BEGIN = 10;
    public static final byte STRUCT_END = 11;
    public static final byte ZERO_TAG = 12;

    public JceStruct() {
        super();
    }

    public boolean containField(String arg2) {
        return Boolean.getBoolean(arg2);
    }

    public Object getFieldByName(String arg2) {
        return null;
    }

    public JceStruct newInit() {
        return null;
    }

    public abstract void readFrom(HelperA arg1);

    public void recyle() {
    }

    public void setFieldByName(String arg1, Object arg2) {
    }

    public byte[] toByteArray() {
        HelperC v0 = new HelperC();
        this.writeTo(v0);
        return v0.getBufferData();
    }

    public abstract void writeTo(HelperC arg1);
}

