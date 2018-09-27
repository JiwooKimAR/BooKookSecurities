package BooKookSecurities.Model;

import javafx.scene.control.CheckBox;

import java.util.Date;

public class Report {
    int number;
    String name;
    Date date;
    CheckBox isSelected;

    public Report() {
    }

    public Report(int number, String name, Date date) {
        this.number = number;
        this.name = name;
        this.date = date;
        this.isSelected.setSelected(false);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
