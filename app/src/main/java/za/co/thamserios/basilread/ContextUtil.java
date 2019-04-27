package za.co.thamserios.basilread;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

/**
 * Created by robson on 2017/01/15.
 */
public class ContextUtil extends Application implements Thread.UncaughtExceptionHandler{
    public static final String TAG = ContextUtil.class.getSimpleName();
    public static Context context;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        Log.e(TAG, "onCreate ContextUtil");
        ContextUtil.context = getApplicationContext();
        ApplicationCache.getInstance().setContext(ContextUtil.context);
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        mDefaultExceptionHandler.uncaughtException(thread, ex);
    }

    public static Context getContext() {
        return ContextUtil.context;
    }

    public static String getAppString(int resourceInt) {
        return getContext().getString(resourceInt);
    }

    public static SharedPreferences getSharedPrefs(String name, int mode) {
        return getContext().getSharedPreferences(name, mode);
    }

    public static void setSharedPrefs(String name, int mode, String key, String value) {
        SharedPreferences prefs = getContext().getSharedPreferences(name, mode);
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void hideKeyboard(Activity activity, View view) {
        if (activity != null & view != null) {
            final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public String getAppVersionName() {
        try {
            return String.valueOf(getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to get App Version Name");
            return "Unknown";
        }
    }

    public int getAppVersionCode() {
        try {
            return (getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to get App Version Code");
            return 0;
        }
    }

    public String getAppPackageName() {
        try {
            return String.valueOf(getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).packageName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to get App Package Name");
            return "Unknown";
        }
    }

    /**
     * Version + Build Number
     *
     * @return
     */
    public String getFullVersion() {
        return getAppVersionName() + " (" + getAppVersionCode() + ")";
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    public boolean isApplicationInForeground() {
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(getContext().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
