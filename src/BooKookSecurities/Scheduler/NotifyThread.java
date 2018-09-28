package BooKookSecurities.Scheduler;

import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.Setting;

import java.util.Calendar;

public class NotifyThread implements Runnable {
    SettingsManager settingsManager = SettingsManager.getInstance();
    @Override
    public void run() {
        System.out.println("Called");
        //확인작업
    }
}
