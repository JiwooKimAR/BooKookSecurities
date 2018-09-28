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

    @Override
    public void run() {
        System.out.println("Called");
        //확인작업
    }
}
