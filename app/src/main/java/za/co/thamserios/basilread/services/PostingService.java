package za.co.thamserios.basilread.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.InetAddress;
import java.util.Calendar;

/**
 * Created by robson on 2017/03/10.
 */

public class PostingService extends IntentService {

    private StartShiftService startShiftService = new StartShiftService();
    private StandingTimeService standingTimeService = new StandingTimeService();
    private BreakdownService breakdownService = new BreakdownService();
    private ProductionService productionService = new ProductionService();
    private EndShiftService endShiftService = new EndShiftService();

    public PostingService() {
        super("PostingService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       if (isInternetAvailable()) {
            try {
                // your code for background process
                startShiftService.postToServer();
                standingTimeService.postToServer();
                breakdownService.postToServer();
                productionService.postToServer();
                endShiftService.postToServer();
            }catch (Exception ex){
                Log.d("PostingService",ex.getMessage());
            }
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
