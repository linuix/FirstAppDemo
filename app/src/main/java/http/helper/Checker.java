package http.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Checker {
    public static String a(ArrayList arg6) {
        String v0_1;
        Object v0;
        StringBuffer v3 = new StringBuffer();
        int v1;
        for(v1 = 0; v1 < arg6.size(); ++v1) {
            v0 = arg6.get(v1);
            if((((String)v0).equals("java.lang.Integer")) || (((String)v0).equals("int"))) {
                v0_1 = "int32";
            }
            else {
                if(!((String)v0).equals("java.lang.Boolean") && !((String)v0).equals("boolean")) {
                    if(!((String)v0).equals("java.lang.Byte") && !((String)v0).equals("byte")) {
                        if(!((String)v0).equals("java.lang.Double") && !((String)v0).equals("double")
                                ) {
                            if(!((String)v0).equals("java.lang.Float") && !((String)v0).equals("float")
                                    ) {
                                if(!((String)v0).equals("java.lang.Long") && !((String)v0).equals("long")
                                        ) {
                                    if(!((String)v0).equals("java.lang.Short") && !((String)v0).equals(
                                            "short")) {
                                        if(((String)v0).equals("java.lang.Character")) {
                                            throw new IllegalArgumentException("can not support java.lang.Character");
                                        }
                                        else if(((String)v0).equals("java.lang.String")) {
                                            v0_1 = "string";
                                            arg6.set(v1, v0);
                                        }
                                        else {
                                            if(((String)v0).equals("java.util.List")) {
                                                v0_1 = "list";
                                            }
                                            else if(((String)v0).equals("java.util.Map")) {
                                                v0_1 = "mapC";
                                            }
                                            else {
                                            }

                                            arg6.set(v1, v0);
                                        }
                                    }

                                    v0_1 = "short";
                                    arg6.set(v1, v0);
                                }

                                v0_1 = "int64";
                                arg6.set(v1, v0);
                            }

                            v0_1 = "float";
                            arg6.set(v1, v0);
                        }

                        v0_1 = "double";
                        arg6.set(v1, v0);
                    }

                    v0_1 = "char";
                    arg6.set(v1, v0);
                }

                v0_1 = "bool";
            }

        }

        Collections.reverse(((List)arg6));
        for(v1 = 0; v1 < arg6.size(); ++v1) {
            v0 = arg6.get(v1);
            if(((String)v0).equals("list")) {
                arg6.set(v1 - 1, "<" + arg6.get(v1 - 1));
                arg6.set(0, String.valueOf(arg6.get(0)) + ">");
            }
            else if(((String)v0).equals("mapC")) {
                arg6.set(v1 - 1, "<" + arg6.get(v1 - 1) + ",");
                arg6.set(0, String.valueOf(arg6.get(0)) + ">");
            }
            else if(((String)v0).equals("Array")) {
                arg6.set(v1 - 1, "<" + arg6.get(v1 - 1));
                arg6.set(0, String.valueOf(arg6.get(0)) + ">");
            }
        }

        Collections.reverse(((List)arg6));
        Iterator v1_1 = arg6.iterator();
        while(v1_1.hasNext()) {
            v3.append(v1_1.next());
        }

        return v3.toString();
    }
}

