package BooKookSecurities.Model;

import java.time.LocalDate;

public class ExcelData implements Comparable<ExcelData>{
    LocalDate date;
    int value;

    public ExcelData() {
    }

    public ExcelData(LocalDate date, int value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(ExcelData o) {
        return getDate().compareTo(o.getDate());
    }
}
