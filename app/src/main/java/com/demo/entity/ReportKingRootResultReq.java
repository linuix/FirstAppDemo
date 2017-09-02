package com.demo.entity;


import java.util.ArrayList;

import http.helper.HelperA;
import http.helper.HelperC;

public final class ReportKingRootResultReq extends JceStruct {
    static ArrayList cache_kingRootResults;
    public ArrayList kingRootResults;
    public String mac;
    public String prevSuVersion;
    public long sessionId;

    public ReportKingRootResultReq() {
        super();
        this.sessionId = 0;
        this.kingRootResults = null;
        this.mac = "";
        this.prevSuVersion = "";
    }

    public final void readFrom(HelperA helperA) {
        this.sessionId = helperA.getLongFromBuffer(this.sessionId, 0, true);
        if(ReportKingRootResultReq.cache_kingRootResults == null) {
            ReportKingRootResultReq.cache_kingRootResults = new ArrayList();
            ReportKingRootResultReq.cache_kingRootResults.add(new KingRootResult());
        }

        this.kingRootResults = (ArrayList) helperA.a(ReportKingRootResultReq.cache_kingRootResults, 1, true);
        this.mac = helperA.getStringFromBuffer(2, false);
        this.prevSuVersion = helperA.getStringFromBuffer(3, false);
    }

    public final void writeTo(HelperC arg4) {
        arg4.addLongData(this.sessionId, 0);
        arg4.addListData(this.kingRootResults, 1);
        if(this.mac != null) {
            arg4.addStringData(this.mac, 2);
        }

        if(this.prevSuVersion != null) {
            arg4.addStringData(this.prevSuVersion, 3);
        }
    }
}

