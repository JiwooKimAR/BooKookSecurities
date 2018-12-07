package BooKookSecurities.Model;

public class ReportExcelData {
    private int report_num;
    private String report_name, report_date, name;

    public ReportExcelData() {
    }

    public int getReport_num() {
        return report_num;
    }

    public void setReport_num(int report_num) {
        this.report_num = report_num;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
