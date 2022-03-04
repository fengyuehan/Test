package com.example.designpattern.FlyweightPattern;

public class FinanicReportManager implements IReportManager {
    protected String tenantId = null;

    public FinanicReportManager(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String createReport() {
        return "This is financial report";
    }
}
