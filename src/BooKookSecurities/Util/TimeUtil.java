package BooKookSecurities.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getCurrentTime(){
        Date date = Calendar.getInstance().getTime();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
