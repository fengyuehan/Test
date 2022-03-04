package com.example.designpattern.FlyweightPattern;

import java.util.HashMap;
import java.util.Map;

public class ReportManagerFactory {
    /**
     * 享元模式
     */
    Map<String,IReportManager> map = new HashMap<>();
    Map<String,IReportManager> managerMap = new HashMap<>();

    public IReportManager getFinancialReportManager(String id){
        IReportManager iReportManager = map.get(id);
        if (iReportManager == null){
            iReportManager = new FinanicReportManager(id);
            map.put(id,iReportManager);
        }
        return iReportManager;
    }

    public  IReportManager getEmployeeReportManager(String id){
        IReportManager iReportManager = managerMap.get(id);
        if (iReportManager == null){
            iReportManager = new EmployeeReportManager(id);
            managerMap.put(id,iReportManager);
        }
        return iReportManager;
    }
}
