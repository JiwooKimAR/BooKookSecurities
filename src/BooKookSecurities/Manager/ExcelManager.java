package BooKookSecurities.Manager;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

public class ExcelManager {
    //manages read+write excel
    private String filePath;

    public ExcelManager(String filePath) {
        this.filePath = filePath;
    }

    public ExcelManager() {
    }

    public void read(){
        try {
            OPCPackage opcPackage = OPCPackage.open(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            opcPackage.close();

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet){
                for (Cell cell : row){
                    System.out.println(cell.getRowIndex());
                }
            }

        }catch(InvalidFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
