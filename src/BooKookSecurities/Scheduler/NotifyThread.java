package BooKookSecurities.Scheduler;

import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.Setting;

import java.util.Calendar;

/*
    주기적으로 일정 기간이 지난 보고서가 있는지 확인
 */
public class NotifyThread implements Runnable {
    SettingsManager settingsManager = SettingsManager.getInstance();
    
    private int Limit, Daytime; // 아마도 SettingsManager의 전역변수로 나중에 바뀔 것
    private int hour, passH;
    private int sendH = -1;
    private ArrayList<Report> reports = new ArrayList<>();
    private boolean DONE;
    private Calendar currentTime, sendTime;

    @Override
    public void run() {
    	currentTime = Calendar.getInstance();
    	hour = currentTime.get(Calendar.HOUR);
    	passH++;
    	if ((!DONE) && (sendH == -1)) {
    		task();
    		setting();
    	}
    	if ((!DONE) && (hour == Daytime)) {
    		task();
    		setting();
    	}
    	if (DONE) {
    		if (passH > 12) {
    			DONE = false;
    		}
    	}
    }
    
    private void task() {
        ReportManager.readReport();
        reports = ReportManager.getReportList();
        for (int i = 0; i < reports.size(); i++) {
        	if (reports.get(i).getDateDifference() > Limit) {
        		EmailSender.SendMail(reports.get(i).getItemName(), reports.get(i).getDateDifference());
        	}
        }
    }
    
    private void setting() {
    	sendH = hour;
		DONE = true;
		passH = 0;
    }
}

/*
NotifyScheduler.java를 통해 1시간마다 호출됨

0. 
1. ReportManager.readReport()를 통해 보고서를 읽어 ReportManager.reports에 저장
2. ReportManager.getReportList()를 통해 ReportManager.reports를 불러옴
3. 이 리스트 중 SettingsManager의 Limit 기간보다 시간이 지난 리포트가 있는지 확인
4. 기간이 지난 리포트가 있으면 EmailSender.SendMail(companyName, psassedDays)를 통해 메일을 보냄 

*/
