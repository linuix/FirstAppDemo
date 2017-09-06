package com.demo.solution;

import com.demo.entity.RootExtInfo;

import java.io.Serializable;
import java.util.Arrays;

import http.demo.SolutionHelpers;

/**
 * Created by Administrator on 2016/10/18.
 */

public class SolutionManager implements Serializable{

    public int a;
    public SolutionHelpers[] respSolutionHelpers;
    public SolutionHelpers[] executorSolutionHelpers;
    public com.demo.entity.RootExtInfo pcRootInfo;
    public RootExtInfo mobileRootInfo;
    public String v1;

    public SolutionManager() {
        super();
        //这里在创建的时候。
    }

    @Override
    public String toString() {
        return "SolutionManager{" +
                "cmd=" + a +
                ", stdout=" + Arrays.toString(respSolutionHelpers) +
                ", err=" + Arrays.toString(executorSolutionHelpers) +
                ", pcRootInfo=" + pcRootInfo +
                ", mobileRootInfo=" + mobileRootInfo +
                '}';
    }
}
