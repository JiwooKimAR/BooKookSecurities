package BooKookSecurities.Model;

public class Setting {
    private String recipient_mail, report_path;
    private boolean isStartProgram;
    private int limit_year, limit_month, limit_sec, time_period;

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

    public int getLimit_year() {
        return limit_year;
    }

    public void setLimit_year(int limit_year) {
        this.limit_year = limit_year;
    }

    public int getLimit_month() {
        return limit_month;
    }

    public void setLimit_month(int limit_month) {
        this.limit_month = limit_month;
    }

    public int getLimit_sec() {
        return limit_sec;
    }

    public void setLimit_sec(int limit_sec) {
        this.limit_sec = limit_sec;
    }
}
