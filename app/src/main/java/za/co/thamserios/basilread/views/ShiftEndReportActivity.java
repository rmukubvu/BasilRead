
package za.co.thamserios.basilread.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.adapters.ProductionLogAdapter;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.models.CaptureLog;
import za.co.thamserios.basilread.navigation.NavigationHelper;
import za.co.thamserios.basilread.services.PostingService;

public class ShiftEndReportActivity extends AppCompatActivity implements OperatorStatusCapture.OnSaveStateClicked,OperatorStatusCapture.OnActivityClicked {

    private NavigationHelper navigationHelper;
    private ApplicationCache cache;
    private RelativeLayout bottomLayout;
    private TextView rigTopText;
    private TextView shiftTopText;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_end_report);
        navigationHelper = new NavigationHelper(this);
        cache = ApplicationCache.getInstance();
        initButtons();
        initOperatorData();
        showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION_HISTORY));
        headerText.setText("Production History");
    }

    private void initOperatorData() {
        bottomLayout = (RelativeLayout)findViewById(R.id.bottom_navigation_layout);
        shiftTopText = (TextView)findViewById(R.id.shiftTopText);
        rigTopText = (TextView)findViewById(R.id.rigTopText);
        headerText = (TextView)findViewById(R.id.headerText);

        shiftTopText.setText(cache.getCachedIndex().getShiftTopText() + " Shift");
        rigTopText.setText(cache.getCachedIndex().getRigTopText());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initButtons(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_production_his:
                                headerText.setText("Production History");
                                showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION_HISTORY));
                                return true;
                            case R.id.action_breakdowns_his:
                                headerText.setText("Breakdown History");
                                showFragment(navigationHelper.getFragment(NavigationHelper.BREAKDOWN_HISTORY));
                                return true;
                            case R.id.action_standing_his:
                                headerText.setText("Standing Time History");
                                showFragment(navigationHelper.getFragment(NavigationHelper.STANDING_TIME_HISTORY));
                                return true;
                        }
                        return true;
                    }
                });
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
                startActivity(new Intent(ShiftEndReportActivity.this,ShiftEndReportActivity.class));
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
        showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION_HISTORY));
    }

    @Override
    public void closeFragment() {

        Intent serviceIntent = new Intent(ShiftEndReportActivity.this,PostingService.class);
        startService(serviceIntent);

        showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION_HISTORY));
        //fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeFragmentWithAction(int index) {
        //clear status and operator info
        //hiide bottom bar
        bottomLayout.setVisibility(View.GONE);
        showFragment(navigationHelper.getFragment(NavigationHelper.PRODUCTION_HISTORY));
        //fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFabButton() {

    }

    private void sendNotification(){
    }

    @Override
    public void changeFragment(int index) {
        if (index == NavigationHelper.END_SHIFT) {
            finish();
            startActivity(new Intent(ShiftEndReportActivity.this, StartActivity.class));
        }
        else
            showFragment(navigationHelper.getFragment(index));
    }

}
