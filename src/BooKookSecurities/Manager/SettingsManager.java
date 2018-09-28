package BooKookSecurities.Manager;

import BooKookSecurities.Model.Setting;
import BooKookSecurities.String.Strings;
/*
    전역변수로 존재
    프로그램에 관련된 옵션 저장+수정을 맡음
 */
public class SettingsManager {
    private final String settingPath = Strings.SettingPath;
    private Setting setting = new Setting();
    private static SettingsManager m_instance;

    public static SettingsManager getInstance(){
        if (m_instance == null)
            m_instance = new SettingsManager();
        return m_instance;
    }
    public SettingsManager() {
    }

    private void readSettings(){
        //load data to setting
    }
    public Setting getSetting(){
        return this.setting;
    }


}
