package BooKookSecurities.Model;

import javafx.scene.control.CheckBox;

import java.util.Calendar;
import java.util.Date;

public class Report implements Comparable<Report>{
    public static final int NUM_FIELDS = 3;
    private int item_code;
    private String item_name;
    private Date item_added_date;
    private int date_difference;
    private CheckBox isSelected;
    public Report() {
    }

    public Report(int item_code, String item_name, Date item_added_date) {
        this.item_code = item_code;
        this.item_name = item_name;
        this.item_added_date = item_added_date;

    }

    @Override
    public int compareTo(Report o) {
        return getItem_added_date().compareTo(o.getItem_added_date());
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Date getItem_added_date() {
        return item_added_date;
    }

    public void setItem_added_date(Date item_added_date) {
        this.item_added_date = item_added_date;
    }

    public int getDate_difference() {
        return date_difference;
    }

    public void setDate_difference(int date_difference) {
        this.date_difference = date_difference;
    }


    public CheckBox getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(CheckBox isSelected) {
        this.isSelected = isSelected;
    }
}
