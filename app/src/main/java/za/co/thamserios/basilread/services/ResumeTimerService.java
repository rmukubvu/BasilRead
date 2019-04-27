package za.co.thamserios.basilread.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by robson on 2017/04/25.
 */

public class ResumeTimerService extends Service implements Runnable {

        private static final int SERVICE_ID = 5872;
        private static final String TAG = ResumeTimerService.class.getSimpleName();
        private Handler mHandler;

        @Override
        public void onCreate() {
            // TODO Auto-generated method stub
            super.onCreate();
            Log.d("Testing", "Service got created");
            //Toast.makeText(this, "RabbitService.onCreate()", Toast.LENGTH_LONG).show();
        }


        public class LocalBinder extends Binder {
            public ResumeTimerService getService() {
                return ResumeTimerService.this;
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return mBinder;
        }

        // This is the object that receives interactions from clients.  See
        // RemoteService for a more complete example.
        private final IBinder mBinder = new LocalBinder();

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i("LocalService", "Received start id " + startId + ": " + intent);
            mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(this);
            //Context ctx = getApplicationContext();
            //new PostUpdatesTask(ctx).execute();
            return START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {
            try {
                // Cancel the persistent notification.
                Log.i("LocalService", "Destroyed pusher service");
                Context ctx = getApplicationContext();
                /** this gives us the time for the first trigger.  */
                Calendar cal = Calendar.getInstance();
                AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
                long interval = 1000 * 60 * 30; // 60 minutes in milliseconds
                Intent serviceIntent = new Intent(ctx, ResumeTimerService.class);
                // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
                PendingIntent servicePendingIntent =
                        PendingIntent.getService(ctx,
                                ResumeTimerService.SERVICE_ID, // integer constant used to identify the service
                                serviceIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
                // there are other options like setInexactRepeating, check the docs
                am.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                        cal.getTimeInMillis(),
                        interval,
                        servicePendingIntent
                );

                if (mHandler != null)
                    mHandler.removeCallbacks(this);
            }catch (Exception ex){
                Log.d("RabbitService",ex.getMessage());
            }
            super.onDestroy();
        }

        @Override
        public void run() {
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

}
