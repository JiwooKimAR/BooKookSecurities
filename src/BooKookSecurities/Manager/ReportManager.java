package BooKookSecurities.Manager;

import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.Setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        reports = new ArrayList<>();
    }

    public ArrayList<Report> getReports(){
        readReports();
        Collections.sort(reports);
        return reports;
    }

    private void readReports(){
        reports.clear();
        Setting setting = settingsManager.getSetting();
        List<String> reportLines;
        File rFile = new File(setting.getReport_path());

        try{
            reportLines = Files.readAllLines(rFile.toPath());
            Report report = new Report();
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
