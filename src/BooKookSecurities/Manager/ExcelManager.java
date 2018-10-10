package BooKookSecurities.Manager;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            opcPackage.close();

            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet){
                String strRow = "";
                for (Cell cell : row){

                    if (cell != null){
                        switch(cell.getCellType()) {
                            case NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) strRow = strRow + " " + cell.getDateCellValue().toString();
                                else strRow = strRow + " " + cell.getNumericCellValue();
                                break;
                            case STRING:
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
    }
}
