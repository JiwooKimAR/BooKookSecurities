package BooKookSecurities.Manager;

import BooKookSecurities.Model.Setting;
import BooKookSecurities.String.Strings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
    전역변수로 존재
    프로그램에 관련된 옵션 저장+수정을 맡음
    getInstance 호출로 인스턴스를 가져와서 사용
 */
public class SettingsManager {
    private final String settingPath = Strings.SettingPath;
    private Setting setting;
    private static SettingsManager m_instance;

    public static SettingsManager getInstance(){
        if (m_instance == null)
            m_instance = new SettingsManager();
        return m_instance;
    }
    public SettingsManager() {
    }

    public Setting getSetting(){

        if (setting == null) {
            setting = new Setting();
            setSetting();
        }
        return this.setting;
    }

    private void updateSetting(String key, String value){
        String[] updates = new String[2];
        updates[0] = key;
        updates[1] = value;
        readToken(updates);
    }
    private void setSetting(){
        //load data to setting
        List<String> settingLines;
        File sFile = new File(settingPath);

        try {
            settingLines = Files.readAllLines(sFile.toPath());
            for (String line : settingLines){
                String[] token = line.split("=");
                if (token.length == 2) readToken(token);
                else throw new IOException();
//              if length is not equal to two, setting is corrupted
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void readToken(String[] token){
        switch (token[0]){
            case "username":
                if (token[1].equals(" ")) this.setting.setUsername(null);
                else this.setting.setUsername(token[1]);
                break;
            case "report path":
                this.setting.setReport_path(token[1]);
                break;
            case "limit year":
                this.setting.setLimit_year(Integer.parseInt(token[1]));
                break;
            case "limit month":
                this.setting.setLimit_month(Integer.parseInt(token[1]));
                break;
            case "limit day":
                this.setting.setLimit_day(Integer.parseInt(token[1]));
                break;
            case "limit date":
                this.setting.setLimit_date(Integer.parseInt(token[1]));
                break;
            case "start":
                if (token[1].equals("No")) this.setting.setStartProgram(false);
                else this.setting.setStartProgram(true);
                break;
            case "time period":
                this.setting.setTime_period_hrs(Integer.parseInt(token[1]));
                break;

        }
    }
}
