package BooKookSecurities.Manager;

import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.Setting;
import javafx.collections.ObservableList;

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

    public void writeReports(ObservableList<Report> selected, ObservableList<Report> allReports){
        List<String> output = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        allReports.removeAll(selected);
        Collections.sort(allReports);
        for (Report report : allReports){
            String line = report.getItem_code() + " \"" + report.getItem_name() + "\" " + df.format(report.getItem_added_date());
            output.add(line);
        }
        try {
            Setting setting = settingsManager.getSetting();
            Path wFile = Paths.get(setting.getReport_path());
            Files.write(wFile, output, Charset.forName("UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("done");
    }
    public ArrayList<Report> getReports(){ //return report array list
        if (reports == null || reports.size() == 0) readReports();
        Collections.sort(reports);
        return reports;
    }

    public void notifyDataChanged(){ //read the latest report file
        readReports();
    }
    public Report getOldestReport(){ //return the latest report file
        if (reports == null || reports.size() == 0) readReports();
        return reports.get(0);
    }
    private void readReports(){
        if (reports == null) reports = new ArrayList<>();
        else reports.clear();
        Setting setting = settingsManager.getSetting();
        List<String> reportLines;
        File rFile = new File(setting.getReport_path());

        try{
            reportLines = Files.readAllLines(rFile.toPath());
            for (String line : reportLines){

                String[] token = line.split(" ");
                int item_num = Integer.parseInt(token[0].replaceFirst("0", ""));
                int item_date = Integer.parseInt(token[token.length - 1]);
                String name;

                if (token.length > Report.NUM_FIELDS) name = token[1] + " " + token[2];
                else name = token[1];
                name = name.substring(1, name.length() - 1); //remove quotes

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date = dateFormat.parse(Integer.toString(item_date));

                reports.add(new Report(item_num, name, date));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }

    }


}
