package BooKookSecurities.Scheduler;

import BooKookSecurities.Controller.MainController;
import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.Setting;
import BooKookSecurities.Util.EmailSender;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    //private int Limit, Daytime; // 아마도 SettingsManager의 전역변수로 나중에 바뀔 것
    //private int hour, passH;
    //private int sendH = -1;
    //    private ArrayList<Report> reports = new ArrayList<>();
    //private boolean DONE;
    //private Calendar currentTime, sendTime;
    private ArrayList<Report> reports;
    private Calendar curTime;
    private int limit_year, limit_month, limit_day;
    private MainController controller;

    public NotifyThread(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("Running");
        ReportManager reportManager = new ReportManager();
        reports = reportManager.getReports();

        settingsManager = SettingsManager.getInstance();
        Setting setting = settingsManager.getSetting();
        limit_year = setting.getLimit_year();
        limit_month = setting.getLimit_month();
        limit_day = setting.getLimit_day();

        curTime = GregorianCalendar.getInstance();
        int cnt_notifiaction = 0;
        EmailSender emailSender = new EmailSender(settingsManager.getSetting().getRecipient_mail());
        for (Report report : reports){
            if (isOutdated(report)){ //if outdated
                //send report
                cnt_notifiaction++;
                System.out.println("Outdated: " + report.getItem_name() + " Days passed: " + report.getDate_difference());
                emailSender.SendMail(report.getItem_name(), report.getDate_difference());
            }
        }
        //update status
        String notification;
        if (cnt_notifiaction == 0){
            notification = "보낼 알림 메일이 없습니다.";
        }
        else{
            notification = cnt_notifiaction + " 개의 알림 메일이 " + settingsManager.getSetting().getRecipient_mail() +" 로 발송됐습니다.";
        }
        controller.setNotificationText(notification);


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

        if (years_in_between >= limit_year && months_in_between >= limit_month && days_in_between >= limit_day){
            return true;
        }
        return false;
    }
    private void task() {
//        ReportManager.readReport();
//        reports = ReportManager.getReportList();
//        for (int i = 0; i < reports.size(); i++) {
//        	if (reports.get(i).getDateDifference() > Limit) {
//        		EmailSender.SendMail(reports.get(i).getItemName(), reports.get(i).getDateDifference());
//        	}
//        }
    }

//    private void setting() {
//        sendH = hour;
//        DONE = true;
//        passH = 0;
//    }
}


