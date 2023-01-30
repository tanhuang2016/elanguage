package com;

import com.annotation.EColumn;

//@ETable("StandbyDbInfo")
public class StandbyDbInfo {

    @EColumn("设备ID")
    private String devId;

    @EColumn("厂站名")
    private String dcdName;

    @EColumn("量测名")
    private String measureName;

    @EColumn("时间")
    private String date;

    @EColumn("有功值")
    private String activeValue;

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDcdName() {
        return dcdName;
    }

    public void setDcdName(String dcdName) {
        this.dcdName = dcdName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(String activeValue) {
        this.activeValue = activeValue;
    }
}
