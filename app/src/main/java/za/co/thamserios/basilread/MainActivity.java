package za.co.thamserios.basilread;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import me.itangqi.waveloadingview.WaveLoadingView;
import za.co.thamserios.basilread.constants.AppConstants;
import za.co.thamserios.basilread.constants.ConstantsLoader;
import za.co.thamserios.basilread.services.LookupService;
import za.co.thamserios.basilread.services.UserService;
import za.co.thamserios.basilread.views.LoginActivity;
import za.co.thamserios.basilread.views.StartActivity;

public class MainActivity extends AppCompatActivity {

    private WaveLoadingView mWaveLoadingView;
    private UserService userService;
    private LookupService lookupService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setBottomTitle("");
        mWaveLoadingView.setCenterTitle("");
        mWaveLoadingView.setTopTitle("");
        prepareLoaderData();
    }

    private void prepareLoaderData() {
        userService = new UserService();
        lookupService = new LookupService();
        new GetDataTask().execute();
    }

    private  class GetDataTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            userService.getAllUsers();
            lookupService.getAllLookups();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            finish();
            //open next intent
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

}
