package BooKookSecurities.Scheduler;

import BooKookSecurities.Controller.MainController;
import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.Setting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
    주기적으로 일정 기간이 지난 보고서가 있는지 확인
 */

/*
NotifyScheduler.java를 통해 Setting.dat 에서 time period 시간마다 호출됨.

0.
1. ReportManager.readReport()를 통해 보고서를 읽어 ReportManager.reports에 저장
2. ReportManager.getReportList()를 통해 ReportManager.reports를 불러옴
3. 이 리스트 중 SettingsManager의 Limit 기간보다 시간이 지난 리포트가 있는지 확인
4. 기간이 지난 리포트가 있으면 EmailSender.SendMail(companyName, passedDays)를 통해 메일을 보냄
*/

public class NotifyThread implements Runnable {
    SettingsManager settingsManager;

    private int Limit, Daytime; // 아마도 SettingsManager의 전역변수로 나중에 바뀔 것
    //private int hour, passH;
    //private int sendH = -1;
    //    private ArrayList<Report> reports = new ArrayList<>();
    //private boolean DONE;
    //private Calendar currentTime, sendTime;
    private ArrayList<Report> reports;
    private Calendar curTime;
    private int limit_year, limit_month, limit_day;
    private MainController controller;
    private ReportManager reportManager;

    public NotifyThread(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        reportManager = new ReportManager();
        reports = reportManager.getReports();
        settingsManager = SettingsManager.getInstance();
        Setting setting = settingsManager.getSetting();
        limit_year = setting.getLimit_year();
        limit_month = setting.getLimit_month();
        limit_day = setting.getLimit_day();
        Limit = setting.getLimit_date();

        curTime = GregorianCalendar.getInstance();
        // EmailSender emailSender = new EmailSender(settingsManager.getSetting().getRecipient_mail());
        for (Report report : reports){
            if (isOutdated(report)){ //if outdated
                //send report
                System.out.println("Outdated: " + report.getItem_name() + " Days passed: " + report.getDate_difference());
                // ##### 삭제 해야 됨
                // emailSender.SendMail(report.getItem_name(), report.getDate_difference());
                }
        }

        int cnt_notifiaction = task();

        //update status
        String notification;
        if (cnt_notifiaction == 0){
            notification = "알림 보낼 보고서가 없습니다.";
        }
        else{
            // notification = cnt_notifiaction + " 개의 알림 메일이 " + settingsManager.getSetting().getRecipient_mail() +" 로 발송됐습니다.";
            notification = cnt_notifiaction + " 개의 알림이 보여졌습니다.";
        }
        controller.setNotificationText(notification);

        // 알림창 안 띄울거면 필요 없음
        //    	currentTime = Calendar.getInstance();
//    	hour = currentTime.get(Calendar.HOUR);
//    	passH++;
//    	if ((!DONE) && (sendH == -1)) {
//    		task();
//    		setting();
//    	}
//    	if ((!DONE) && (hour == Daytime)) {
//    		task();
//    		setting();
//    	}
//    	if (DONE) {
//    		if (passH > 12) {
//    			DONE = false;
//    		}
//    	}
    }

    private  boolean isOutdated(Report report){ //checks whether report is outdated or not
        Calendar reportDate = Calendar.getInstance();
        reportDate.setTimeInMillis(report.getItem_added_date().getTime());

        int years_in_between = curTime.get(Calendar.YEAR) - reportDate.get(Calendar.YEAR);
        int months_in_between = curTime.get(Calendar.MONTH) - reportDate.get(Calendar.MONTH);
        int days_in_between = curTime.get(Calendar.DAY_OF_MONTH) - reportDate.get(Calendar.DAY_OF_MONTH);
//        System.out.println("Years: " + years_in_between + " Months: " + months_in_between + " Days: " + days_in_between);

        if (years_in_between >= 2){
            return true;
        }
        return false;
    }

    private int task() {
        ArrayList<Report> target_reports = new ArrayList<>();
        int cnt = 0;
        for (Report report : reports) {
            if (report.getDate_difference() > Limit && report.getDate_difference() < 180) {
                target_reports.add(report);
                cnt++;
            }
        }
        return cnt;
    }

//    private void setting() {
//        sendH = hour;
//        DONE = true;
//        passH = 0;
//    }
}
