package com.demo.entity;


import http.demo.SolutionInterface;
import http.helper.HelperA;
import http.helper.HelperC;

public final class KingRootResult extends JceStruct implements SolutionInterface {
    public int endTime;
    public int index;
    public long resultCode;
    public String solutionId;
    public int startTime;
    public int type;

    public KingRootResult() {
        super();
        this.solutionId = "";
        this.index = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.resultCode = 0;
    }

    public final int getEndTime() {
        return this.endTime;
    }

    public final int getIndex() {
        return this.index;
    }

    public final long getResultCode() {
        return this.resultCode;
    }

    public final String getSolutionId() {
        return this.solutionId;
    }

    public final int getStartTime() {
        return this.startTime;
    }

    public final int getType() {
        return this.type;
    }

    public final void readFrom(HelperA arg5) {
        this.solutionId = arg5.getStringFromBuffer(0, true);
        this.index = arg5.getDataForBuffer(this.index, 1, true);
        this.startTime = arg5.getDataForBuffer(this.startTime, 2, true);
        this.endTime = arg5.getDataForBuffer(this.endTime, 3, true);
        this.resultCode = arg5.getLongFromBuffer(this.resultCode, 4, true);
    }

    public final void writeTo(HelperC arg4) {
        arg4.addStringData(this.solutionId, 0);
        arg4.addIntData(this.index, 1);
        arg4.addIntData(this.startTime, 2);
        arg4.addIntData(this.endTime, 3);
        arg4.addLongData(this.resultCode, 4);
    }
}

