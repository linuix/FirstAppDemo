package com.demo.entity;


import java.util.ArrayList;

import http.helper.HelperA;
import http.helper.HelperC;

public final class GetKingRootSolutionResp extends JceStruct {
    static RootExtInfo cache_mobileRootInfo;
    static RootExtInfo cache_pcRootInfo;
    static ArrayList cache_solutionsXmls;
    public RootExtInfo mobileRootInfo;
    public RootExtInfo pcRootInfo;
    public long sessionId;
    public ArrayList solutionsXmls;

    public GetKingRootSolutionResp() {
        super();
        this.sessionId = 0;
        this.solutionsXmls = null;
        this.pcRootInfo = new RootExtInfo();
        this.mobileRootInfo = new RootExtInfo();
    }

    public final void readFrom(HelperA arg5) {
        this.sessionId = arg5.a(this.sessionId, 0, true);
        if(GetKingRootSolutionResp.cache_solutionsXmls == null) {
            GetKingRootSolutionResp.cache_solutionsXmls = new ArrayList();
            GetKingRootSolutionResp.cache_solutionsXmls.add("");
        }

        this.solutionsXmls = (ArrayList) arg5.a(GetKingRootSolutionResp.cache_solutionsXmls, 1, true);
        if(GetKingRootSolutionResp.cache_pcRootInfo == null) {
            GetKingRootSolutionResp.cache_pcRootInfo = new RootExtInfo();
        }

        this.pcRootInfo = (RootExtInfo) arg5.a(GetKingRootSolutionResp.cache_pcRootInfo, 2, false);
        if(GetKingRootSolutionResp.cache_mobileRootInfo == null) {
            GetKingRootSolutionResp.cache_mobileRootInfo = new RootExtInfo();
        }

        this.mobileRootInfo = (RootExtInfo) arg5.a(GetKingRootSolutionResp.cache_mobileRootInfo, 3, false);
    }

    public final void writeTo(HelperC arg4) {
        arg4.a(this.sessionId, 0);
        arg4.a(this.solutionsXmls, 1);
        if(this.pcRootInfo != null) {
            arg4.a(this.pcRootInfo, 2);
        }

        if(this.mobileRootInfo != null) {
            arg4.a(this.mobileRootInfo, 3);
        }
    }
}

