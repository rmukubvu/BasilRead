package za.co.thamserios.basilread.helper;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.services.OperatorStatusUpdateService;

/**
 * Created by robson on 2017/02/01.
 */

public class HelperUtil {


    public static final String BREAKDOWN_COLOR_RED = "#ff0000";
    public static final String STANDING_COLOR_BLUE = "#0000ff";
    public static final String PRODUCTION_COLOR_GREEN = "#008000";
    public static final String SHIFTS_COLOR_GREEN = "#008000";


    public static long getDateDiff(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    public static void sendNotification(int userId,String color,String status){
        ApplicationCache cache = ApplicationCache.getInstance();
        cache.setColor(color);
        cache.setStatus(status);
        cache.setSetUserId(userId);
        new Thread(new PostHelper()).start();
    }
}
