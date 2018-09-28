package BooKookSecurities.Manager;

import BooKookSecurities.Model.Report;

import java.util.ArrayList;
/*
    Mange report files

 */
public class ReportManager {
    SettingsManager settingsManager = SettingsManager.getInstance();
    private ArrayList<Report> reports = new ArrayList<>();
    private int Limit, Daytime;

    private void readReport(){
        String reportPath = settingsManager.getSetting().getReport_path();
    }
}
