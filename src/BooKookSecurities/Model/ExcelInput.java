package BooKookSecurities.Model;


import java.time.LocalDate;

public class ExcelInput implements Comparable<ExcelInput>{
    LocalDate startDate;
    int targetValue;

    public  ExcelInput(){

    }
    public ExcelInput(LocalDate startDate, int targetValue) {
        this.startDate = startDate;
        this.targetValue = targetValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    @Override
    public int compareTo(ExcelInput o) {
        return getStartDate().compareTo(o.getStartDate());
    }
}
