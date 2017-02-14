package http.demo;


import com.demo.entity.RootExtInfo;

import java.util.Arrays;

public final class SolutionManagers {
    public int a;
    public SolutionHelpers[] b;
    public SolutionHelpers[] c;
    public RootExtInfo d;
    public RootExtInfo e;

    public SolutionManagers() {
        super();
    }

    @Override
    public String
    toString() {
        return "SolutionManagers{" +
                "sdk_gt18=" + a +
                ", b=" + Arrays.toString(b) +
                ", c=" + Arrays.toString(c) +
                ", d=" + d +
                ", e=" + e +
                '}';
    }
}

