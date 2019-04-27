package za.co.thamserios.basilread.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.joaquimley.faboptions.FabOptions;

import org.w3c.dom.Text;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.ConnectivityReceiver;
import za.co.thamserios.basilread.ContextUtil;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.constants.AppConstants;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.navigation.NavigationHelper;
import za.co.thamserios.basilread.services.PostingService;
import za.co.thamserios.basilread.views.fragments.BreakdownFragment;

public class OperatorInputActivity extends AppCompatActivity implements
        OperatorStatusCapture.OnSaveStateClicked,
        OperatorStatusCapture.OnActivityClicked,
        View.OnClickListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private NavigationHelper navigationHelper;
    private ApplicationCache cache;
    private FloatingActionsMenu fab;
    private TextView txSelectedAssistant;
    private TextView txSelectedOperator;
    private RelativeLayout bottomLayout;
    private TextView rigTopText;
    private TextView shiftTopText;
    private TextView previousAction;
    private FabOptions mFabOptions;
    private int currentActivity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_input);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        navigationHelper = new NavigationHelper(this);
        cache = ApplicationCache.getInstance();
        OperatorStatusCapture.getInstance().setmSaveStateClicked(this);
        OperatorStatusCapture.getInstance().setmActivityClicked(this);
        initButtons();
        initOperatorData();
        //initFloatingMenu();
        showFragment(navigationHelper.getFragment(NavigationHelper.DEFAULT));
        Intent serviceIntent = new Intent(OperatorInputActivity.this,PostingService.class);
        startService(serviceIntent);
    }

    private void initOperatorData() {
        bottomLayout = (RelativeLayout)findViewById(R.id.bottom_navigation_layout);
        shiftTopText = (TextView)findViewById(R.id.shiftTopText);
        rigTopText = (TextView)findViewById(R.id.rigTopText);
        previousAction = (TextView)findViewById(R.id.previousAction);
        mFabOptions = (FabOptions) findViewById(R.id.fab_options);
        mFabOptions.setOnClickListener(this);

        txSelectedAssistant = (TextView) findViewById(R.id.selectedAssistant);
        txSelectedOperator = (TextView) findViewById(R.id.selectedOperator);

        txSelectedAssistant.setText(cache.getCurrentAssistant());
        txSelectedOperator.setText(cache.getCurrentOperator());

        shiftTopText.setText(cache.getCachedIndex().getShiftTopText() + " Shift");
        rigTopText.setText(cache.getCachedIndex().getRigTopText());
    }

    /*private void initFloatingMenu(){

        FloatingActionButton breakdownHistory = (FloatingActionButton) findViewById(R.id.breakdownHistory);
        breakdownHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OperatorInputActivity.this,ShiftEndReportActivity.class));
            }
        });

        FloatingActionButton dataTransfer = (FloatingActionButton) findViewById(R.id.dataTransfer);
        dataTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OperatorInputActivity.this,BluetoothActivity.class));
            }
        });
    }*/

    private void initButtons(){
        final int title = R.string.dialog_main_title;
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_production:
                                showYesNo(OperatorInputActivity.this,title,"If you move from this page you will loose your unsaved information.",new productionOrStayOnPage());
                                //mFabOptions.setVisibility(View.GONE);
                                //showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION));
                                return true;
                            case R.id.action_breakdowns:
                                showYesNo(OperatorInputActivity.this,title,"If you move from this page you will loose your unsaved information",new breakdownOrStayOnPage());
                                //mFabOptions.setVisibility(View.GONE);
                                //showFragment(navigationHelper.getFragment(NavigationHelper.BREAK_DOWN));
                                return true;
                            case R.id.action_standing:
                                showYesNo(OperatorInputActivity.this,title,"If you move from this page you will loose your unsaved information",new standingTimeOrStayOnPage());
                                //mFabOptions.setVisibility(View.GONE);
                                //showFragment(navigationHelper.getFragment(NavigationHelper.STANDING));
                                return true;
                            case R.id.action_start_edit:
                                showYesNo(OperatorInputActivity.this,title,"Are you sure you want to edit the Start Shift Details?",new leaveOrStayOnPageListener());
                                return true;
                            case R.id.action_finish:
                                showYesNo(OperatorInputActivity.this,title,"Are you sure you want to end the shift now?",new endShiftSettingOrStayOnPage());
                                return true;
                        }
                        return true;
                    }
                });

        //fab = (FloatingActionsMenu) findViewById(R.id.fabButton);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.done:
                startActivity(new Intent(OperatorInputActivity.this,ShiftEndReportActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();
    }

    @Override
    public void saveStateClicked() {

        Intent serviceIntent = new Intent(OperatorInputActivity.this,PostingService.class);
        startService(serviceIntent);

        showFragment(navigationHelper.getFragment(NavigationHelper.DEFAULT));
        mFabOptions.setVisibility(View.VISIBLE);
        //sendNotification();
    }

    @Override
    public void closeFragment() {

        Intent serviceIntent = new Intent(OperatorInputActivity.this,PostingService.class);
        startService(serviceIntent);

        showFragment(navigationHelper.getFragment(NavigationHelper.DEFAULT));
        mFabOptions.setVisibility(View.VISIBLE);
        //
        previousAction.setText(ApplicationCache.getInstance().getPreviousActivity());
    }

    @Override
    public void closeFragmentWithAction(int index) {

        Intent serviceIntent = new Intent(OperatorInputActivity.this,PostingService.class);
        startService(serviceIntent);
        //clear status and operator info
        //hiide bottom bar
        bottomLayout.setVisibility(View.GONE);
        txSelectedAssistant.setText("Not selected");
        txSelectedOperator.setText("Not selected");
        previousAction.setText("Not Selected");
        showFragment(navigationHelper.getFragment(NavigationHelper.DEFAULT));
        mFabOptions.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFabButton() {
        mFabOptions.setVisibility(View.GONE);
    }

    @Override
    public void changeFragment(int index) {
        if (index == NavigationHelper.END_SHIFT) {
            finish();
            startActivity(new Intent(OperatorInputActivity.this, LoginActivity.class));
        }
        else
            showFragment(navigationHelper.getFragment(index));
        previousAction.setText(ApplicationCache.getInstance().getPreviousActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.faboptions_favorite:
                mFabOptions.setButtonColor(R.id.faboptions_favorite, R.color.colorAccent);
                startActivity(new Intent(OperatorInputActivity.this,USBActivity.class));
                break;

            case R.id.faboptions_textsms:
                mFabOptions.setButtonColor(R.id.faboptions_textsms, R.color.colorAccent);
                startActivity(new Intent(OperatorInputActivity.this,ShiftEndReportActivity.class));
                break;
            default:
                // no-op
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        ContextUtil.setConnectivityListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final int title = R.string.dialog_main_title;
            showYesNo(this,title,"If you go back you will lose all your progress",new leaveOrStayOnPageListener());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static void showYesNo(Context context, int title, String message, DialogInterface.OnClickListener yesListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Cancel", new OperatorInputActivity.DismissDialogListener())
                .setPositiveButton("Ok", yesListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
        if (isConnected){
            startService(new Intent(OperatorInputActivity.this,PostingService.class));
        }
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Not connected to internet";
            color = Color.RED;
        }
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private static class DismissDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class leaveOrStayOnPageListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            finish();
            startActivity(new Intent(AppConstants.MAIN_ACTIVITY_INTENT_FILTER));
        }
    }

    private class endShiftSettingOrStayOnPage implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mFabOptions.setVisibility(View.GONE);
            showFragment(navigationHelper.getFragment(NavigationHelper.END_SHIFT));
        }
    }

    private class productionOrStayOnPage implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mFabOptions.setVisibility(View.GONE);
            showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION));
        }
    }

    private class standingTimeOrStayOnPage implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mFabOptions.setVisibility(View.GONE);
            showFragment(navigationHelper.getFragment(NavigationHelper.STANDING));
        }
    }

    private class breakdownOrStayOnPage implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mFabOptions.setVisibility(View.GONE);
            showFragment(navigationHelper.getFragment(NavigationHelper.BREAK_DOWN));
        }
    }
}
