package BooKookSecurities.Manager;


import BooKookSecurities.Model.ExcelData;
import BooKookSecurities.Model.ExcelInput;
import BooKookSecurities.String.Strings;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
class DisparateAvgMax{
    double average , max;
}
public class ExcelManager {
    //manages read+write excel
    private String filePath;
    private ArrayList<ExcelData> excelDataArrayList = new ArrayList<>();
    private ObservableList<ExcelInput> excelInputs;
    private ArrayList<DisparateAvgMax> dispArrayList = new ArrayList<>();
    private int start_x, start_y;
    private long totalValue = 0;
    private double avgValue = 0;
    public ExcelManager(String filePath, ObservableList<ExcelInput> excelInputs) {
        this.filePath = filePath;
        this.excelInputs = excelInputs;
    }

    public ExcelManager() {
    }

    public ArrayList<ExcelData> getExcelDataArrayList() {
        return excelDataArrayList;
    }

    public void setExcelDataArrayList(ArrayList<ExcelData> excelDataArrayList) {
        this.excelDataArrayList = excelDataArrayList;
    }

    public ObservableList<ExcelInput> getExcelInputs() {
        return excelInputs;
    }

    public void setExcelInputs(ObservableList<ExcelInput> excelInputs) {
        this.excelInputs = excelInputs;
    }

    public void read(){
        boolean isStartPointFound = false;
        start_x = start_y = 0;

        try {
            OPCPackage opcPackage = OPCPackage.open(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            opcPackage.close();

            XSSFSheet sheet = workbook.getSheetAt(0);
            LocalDate tempLocal;
            for (Row row : sheet){
                String strRow = "";
                ExcelData excelData = new ExcelData();
                for (Cell cell : row){

                    if (isStartPointFound && cell.getColumnIndex() >= start_x + 2) break;
                    if (cell != null){
                        String cellInfo = "(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ")";
                        strRow = strRow + cellInfo;
                        switch(cell.getCellType()) {
                            case NUMERIC:
                                Date tempDate;
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    if (!isStartPointFound && cell.getColumnIndex() == 0){
                                        start_x = cell.getColumnIndex();
                                        start_y = cell.getRowIndex();
                                        isStartPointFound = true;
                                    }
                                    if (isStartPointFound){
                                        tempDate = cell.getDateCellValue();
                                        tempLocal = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                        excelData.setDate(tempLocal);
                                        strRow = strRow + " " + tempLocal.toString();
                                    }
                                }
                                else {
                                    strRow = strRow + " " + cell.getNumericCellValue();
                                    if (isStartPointFound){
                                        excelData.setValue((int)cell.getNumericCellValue());
                                        excelDataArrayList.add(excelData);
                                        totalValue += (long)cell.getNumericCellValue();
                                    }
                                }

                                break;
                            case STRING:
                                if (isStartPointFound){
                                    excelData.setValue(Integer.parseInt(cell.getStringCellValue()));
                                    excelDataArrayList.add(excelData);
                                }
                                strRow = strRow + " " + cell.getStringCellValue();
                                break;
                            default:
                                break;
                        }

                    }
                }
                System.out.println(strRow);

            }

        }catch(InvalidFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //saved date and value into the arraylist
        Collections.sort(excelDataArrayList);
        for (ExcelData data : excelDataArrayList){
            System.out.println(data.getDate().toString() + ", " + data.getValue());
        }
        System.out.println("total: " + totalValue + "avg: " + totalValue/excelDataArrayList.size());


    }
    public void calculate(){
        int size = excelInputs.size();
        for (int i = 0; i < size - 1; i++){
            DisparateAvgMax value = new DisparateAvgMax();
            int max = 0;
            long total = 0;
            double avg;
            LocalDate targetDate;

            //calculate target date
            int idx = i + 1;
            while(idx + 1< size && excelInputs.get(i).getTargetValue() == excelInputs.get(idx).getTargetValue()) idx++;

            //find max and avg between interval i and idx
            int cnt = 0;
            for (ExcelData excelData : excelDataArrayList){
                if ((excelData.getDate().isEqual(excelInputs.get(i).getStartDate()) || excelData.getDate().isAfter(excelInputs.get(i).getStartDate()))
                && excelData.getDate().isBefore(excelInputs.get(idx).getStartDate())){
                    if (excelData.getValue() > max) max = excelData.getValue();
                    total += (long)excelData.getValue();
                    cnt++;
                }
            }

            avg = (double)total/cnt;
            value.average = (avg - excelInputs.get(i).getTargetValue()) / excelInputs.get(i).getTargetValue() * 100;
            value.max = (max - excelInputs.get(i).getTargetValue()) / (double)excelInputs.get(i).getTargetValue() * 100;
            dispArrayList.add(value);
        }


        for (DisparateAvgMax v : dispArrayList){
            System.out.println(v.max + ", " + v.average);
        }
    }
    public void write(String fileName){

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("output");

        //reverse sorted list
        Collections.sort(excelDataArrayList, new Comparator<ExcelData>() {
            @Override
            public int compare(ExcelData o1, ExcelData o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        int rowNum = 0, idxInput = excelInputs.size() - 1;
        for (ExcelData excelData : excelDataArrayList){
            Row row = sheet.createRow(rowNum++);
            for (int col = 0; col < 3; col++){
                Cell cell = row.createCell(col);
                if (col == 0)cell.setCellValue(excelData.getDate().toString());
                else if (col == 1) cell.setCellValue(excelData.getValue());
                else if (col == 2){
                    if (!(excelData.getDate().isAfter(excelInputs.get(idxInput).getStartDate()) || excelData.getDate().isEqual(excelInputs.get(idxInput).getStartDate()))){
                        if (idxInput > 0) idxInput--;
                    }
                    cell.setCellValue(excelInputs.get(idxInput).getTargetValue());
                }
            }
        }

        //write result
        //assume at least 100 rows are created
        int startRow, startCol;
        int base = 5;
        startRow = startCol = base;
        Row row = sheet.getRow(startRow);
        for (String str : Strings.ExcelResultColumns){
            Cell cell = row.createCell(startCol++);
            cell.setCellValue(str);
        }
        startRow++;
        startCol = base;
        int idx = 0;
        for (DisparateAvgMax value : dispArrayList){
            row = sheet.getRow(startRow);
            Cell cell = row.createCell(startCol++);
            cell.setCellValue(excelInputs.get(idx).getStartDate().toString());

            cell = row.createCell(startCol++);
            cell.setCellValue("buy");

            cell = row.createCell(startCol++);
            cell.setCellValue(excelInputs.get(idx).getTargetValue());

            cell = row.createCell(startCol++);
            cell.setCellValue(value.average);

            cell = row.createCell(startCol++);
            cell.setCellValue(value.max);

            startCol = base;
            startRow++;
            idx++;
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().open(new File(fileName));

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
