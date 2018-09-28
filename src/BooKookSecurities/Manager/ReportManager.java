package BooKookSecurities.Manager;

import BooKookSecurities.Model.Report;

import java.util.ArrayList;
/*
    리포트 파일 삭제+수정+업데이트 관리
    리포트 파일의 위치는 SettingsManager에서 가져옴.
 */
public class ReportManager {
    SettingsManager settingsManager = SettingsManager.getInstance();
    private ArrayList<Report> reports = new ArrayList<>();
    private int Limit, Daytime;

    private void readReport(){
        String reportPath = settingsManager.getSetting().getReport_path();
    }

    public ArrayList<Report> getReportList(){
        ArrayList<Report> reports = new ArrayList<>();

        return reports;
    }
}
