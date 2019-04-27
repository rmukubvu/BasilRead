package za.co.thamserios.basilread.utils;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robson on 2017/03/01.
 */
public class DateUtil {

    // long unixTime = System.currentTimeMillis() / 1000L;
    public static java.sql.Timestamp getCurrentTimeStamp() {
        Date today = new Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public static String getFormattedTime(long diff){
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        return String.format("%d h %d min(s)",diffHours,diffMinutes);
    }

    public static String getFormattedDateFromLong(long date){
        Date d = new Date(date);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
        return df2.format(d);
    }

    public static String getTimeFromLong(long date){
        Date d = new Date(date);
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
        return df2.format(d);
    }

    public static int getCurrentDay(){
        DateTime dt = new DateTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dateNew = dt.toDate();
        return Integer.parseInt(dateFormat.format(dateNew));
    }

    public static long getTimeStamp(){
        return new Date().getTime();
    }
}
