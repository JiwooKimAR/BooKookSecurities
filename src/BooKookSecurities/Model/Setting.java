package BooKookSecurities.Model;

public class Setting {
    private String recipient_mail, report_path;
    private boolean isStartProgram;
    private int alarm_hour, alarm_min, alarm_sec, time_period;

    public Setting() {
    }

    public int getTime_period() {
        return time_period;
    }

    public void setTime_period(int time_period) {
        this.time_period = time_period;
    }

    public String getRecipient_mail() {
        return recipient_mail;
    }

    public void setRecipient_mail(String recipient_mail) {
        this.recipient_mail = recipient_mail;
    }

    public String getReport_path() {
        return report_path;
    }

    public void setReport_path(String report_path) {
        this.report_path = report_path;
    }

    public boolean isStartProgram() {
        return isStartProgram;
    }

    public void setStartProgram(boolean startProgram) {
        isStartProgram = startProgram;
    }

    public int getAlarm_hour() {
        return alarm_hour;
    }

    public void setAlarm_hour(int alarm_hour) {
        this.alarm_hour = alarm_hour;
    }

    public int getAlarm_min() {
        return alarm_min;
    }

    public void setAlarm_min(int alarm_min) {
        this.alarm_min = alarm_min;
    }

    public int getAlarm_sec() {
        return alarm_sec;
    }

    public void setAlarm_sec(int alarm_sec) {
        this.alarm_sec = alarm_sec;
    }
}
