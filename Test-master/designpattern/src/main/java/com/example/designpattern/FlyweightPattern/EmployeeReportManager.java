package com.example.designpattern.FlyweightPattern;

public class EmployeeReportManager implements IReportManager {
    protected String tenantId = null;

    public EmployeeReportManager(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String createReport() {
        return "This is financial report";

    }
}
