package BooKookSecurities.Manager;

import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.ReportExcelData;
import BooKookSecurities.Model.Setting;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
    리포트 파일 삭제+수정+업데이트 관리
    리포트 파일의 위치는 SettingsManager에서 가져옴.
 */
public class ReportManager {
    SettingsManager settingsManager;
    ArrayList<Report> reports;

    public ReportManager() {
        settingsManager = SettingsManager.getInstance();

    }

    public void writeReports(ObservableList<Report> selected, ObservableList<Report> allReports) {
        List<String> output = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        allReports.removeAll(selected);
        Collections.sort(allReports);
        for (Report report : allReports) {
            String line = report.getItem_code() + " \"" + report.getItem_name() + "\" " + df.format(report.getItem_added_date());
            output.add(line);
        }
        try {
            Setting setting = settingsManager.getSetting();
            Path wFile = Paths.get(setting.getReport_path());
            Files.write(wFile, output, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public ArrayList<Report> getReports() { //return report array list
        if (reports == null || reports.size() == 0) readReports();
        Collections.sort(reports);
        return reports;
    }

    public void notifyDataChanged() { //read the latest report file
        readReports();
    }

    public Report getOldestReport() { //return the latest report file
        if (reports == null) readReports();
        else if (reports.size() == 0) return  null;
        return reports.get(0);
    }

    private void readReports() {
        if (reports == null) reports = new ArrayList<>();
        else reports.clear();
        Setting setting = settingsManager.getSetting();
        List<String> reportLines;
        try {
            OPCPackage opcPackage = OPCPackage.open(new File(setting.getReport_path()));
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            opcPackage.close();

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int strIdx = 0;
                Report report = new Report();
                for (Cell cell : row) {
                    if (cell == null) continue;
                    switch (cell.getCellType()) {

                        case NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)){
                                System.out.println("Date: " + cell.getDateCellValue().toString());
                                report.setItem_added_date(cell.getDateCellValue());
                            }
                            else{
                                System.out.println("Number: " + cell.getNumericCellValue());
                                report.setItem_code((int)cell.getNumericCellValue());
                            }
                            break;
                        case STRING:
                            System.out.println("String: " + cell.getStringCellValue());
                            if (strIdx == 0) report.setItem_name(cell.getStringCellValue());
                            else report.setWriter(cell.getStringCellValue());
                            strIdx = (strIdx + 1) % 2;
                            break;
                    }
                }
                report.updateDayDifference();
                if (report.getWriter().equals(settingsManager.getSetting().getUsername()))
                    reports.add(report);
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            return;
        } catch (IOException ioE) {
            ioE.printStackTrace();
            return;
        }



    }


}
