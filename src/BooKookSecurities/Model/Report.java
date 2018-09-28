package BooKookSecurities.Model;

import javafx.scene.control.CheckBox;

import java.util.Calendar;
import java.util.Date;

public class Report {
    private String item_code;
    private String item_name;
    private Calendar item_added_date;
    private int date_difference;
    private int sendCount;
    private CheckBox isSelected;
    public Report() {
    }

    public Report(String item_code, String item_name, Calendar item_added_date) {
        this.item_code = item_code;
        this.item_name = item_name;
        this.item_added_date = item_added_date;
        isSelected.setSelected(false);
    }

    public String getItemCode() {
        return item_code;
    }
    public String getItemName() {
        return item_name;
    }
    public Calendar getItemAddedDate() {
        return item_added_date;
    }
    public int getDateDifference() {
        return date_difference;
    }
    public int getSendCount() {
        return sendCount;
    }
    public void setItemCode(String code) {
        this.item_code = code;
    }
    public void setItemName(String name) {
        this.item_name = name;
    }
    public void setItemAddedDate(Calendar date) {
        this.item_added_date = date;
    }
    public void setDateDifference(int diff) {
        this.date_difference = diff;
    }
    public void setSendCount(int newCount) {
        this.sendCount = newCount;
    }
}
