package BooKookSecurities.Model;


import java.time.LocalDate;

public class ExcelInput {
    LocalDate startDate, endDate;
    int targetValue;

    public  ExcelInput(){

    }
    public ExcelInput(LocalDate startDate, LocalDate endDate, int targetValue) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetValue = targetValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }
}
