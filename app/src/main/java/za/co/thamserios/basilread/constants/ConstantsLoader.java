package za.co.thamserios.basilread.constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import za.co.thamserios.basilread.helper.BasilReadDatabaseHelper;
import za.co.thamserios.basilread.services.LookupService;
import za.co.thamserios.basilread.services.UserService;

/**
 * Created by robson on 2017/03/06.
 */

public class ConstantsLoader extends AsyncTaskLoader<Integer> {

    private UserService userService;
    private LookupService lookupService;
    private Integer mData;
    private String TAG = ConstantsLoader.class.getSimpleName();

    public ConstantsLoader(Context context, Bundle bundle) {
        super(context);
        userService = new UserService();
        lookupService = new LookupService();
        onContentChanged();
    }

    @Override
    public Integer loadInBackground() {
        try {
            Log.d(TAG,"started in background for loader");
            userService.getAllUsers();
            lookupService.getAllLookups();
            return 1;
        }catch (Exception ex){
            Log.d(TAG,ex.getMessage());
        }
        return -1;
    }

    @Override
    public void deliverResult(Integer data){
        mData = data;
        if (isStarted()){
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {

        if (mData != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null ) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();
    }


    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();
    }
}
