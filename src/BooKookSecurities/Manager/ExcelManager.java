package BooKookSecurities.Manager;


import BooKookSecurities.Model.ExcelData;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ExcelManager {
    //manages read+write excel
    private String filePath;
    private ArrayList<ExcelData> excelDataArrayList = new ArrayList<>();
    private int start_x, start_y;
    public ExcelManager(String filePath) {
        this.filePath = filePath;
    }

    public ExcelManager() {
    }

    public ArrayList<ExcelData> getExcelDataArrayList() {
        return excelDataArrayList;
    }

    public void setExcelDataArrayList(ArrayList<ExcelData> excelDataArrayList) {
        this.excelDataArrayList = excelDataArrayList;
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

    }

    public void write(String fileName){

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("output");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(0);
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
