package za.co.thamserios.basilread.views;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.models.CachedIndex;
import za.co.thamserios.basilread.models.Crews;
import za.co.thamserios.basilread.models.OperationsUser;
import za.co.thamserios.basilread.models.Shift;
import za.co.thamserios.basilread.models.SiteConfig;
import za.co.thamserios.basilread.models.StartOfShift;
import za.co.thamserios.basilread.services.CachedIndexService;
import za.co.thamserios.basilread.services.LookupService;
import za.co.thamserios.basilread.services.StartShiftService;
import za.co.thamserios.basilread.services.UserService;
import za.co.thamserios.basilread.utils.DateUtil;

public class StartActivity extends AppCompatActivity {

    private Spinner crews;
    private Spinner benchSupervisor;
    private Spinner foreman;
    private TextView rigTopText;
    private TextView shiftTopText;

    private EditText machineOpenHours;
    private EditText trammingOpenHours;
    private ApplicationCache applicationCache;

    private int selectedForeman;
    private int selectedSupervisor;
    private String selectedCrew;

    private UserService userService;
    private LookupService lookupService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initListeners();
        bindViews();
        bindDataToSpinners();
    }

    private void bindDataToSpinners(){
        userService = new UserService();
        lookupService = new LookupService();

        List<OperationsUser> supervisors = userService.getSupervisor();
        List<OperationsUser> foremans =  userService.getForeman();
        List<Crews> crew = lookupService.getCrewsList();

        ArrayAdapter<Crews> crewsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, crew);
        crewsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crews.setAdapter(crewsAdapter);


        ArrayAdapter<OperationsUser> supervisorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supervisors);
        supervisorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        benchSupervisor.setAdapter(supervisorAdapter);

        ArrayAdapter<OperationsUser> formanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foremans);
        formanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foreman.setAdapter(formanAdapter);
    }

    private void bindViews(){
        machineOpenHours = (EditText)findViewById(R.id.openHoursMachine);
        trammingOpenHours = (EditText)findViewById(R.id.trammingHours);
        shiftTopText = (TextView)findViewById(R.id.shiftTopText);
        rigTopText = (TextView)findViewById(R.id.rigTopText);

        crews = (Spinner) findViewById(R.id.shiftCrewList);
        crews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Crews op = (Crews)adapterView.getItemAtPosition(i);
                selectedCrew = op.getCrewName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        benchSupervisor = (Spinner) findViewById(R.id.benchSupervisorList);
        benchSupervisor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperationsUser op = (OperationsUser)adapterView.getItemAtPosition(i);
                selectedSupervisor = op.getOperationsId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        foreman = (Spinner) findViewById(R.id.foremanList);
        foreman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    OperationsUser op = (OperationsUser)adapterView.getItemAtPosition(i);
                    selectedForeman = op.getOperationsId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        applicationCache = ApplicationCache.getInstance();
        applicationCache.setPreviousActivity("None");
        applicationCache.setCurrentStatus("Shift Started");
        applicationCache.setCurrentActivity("Select Activity");

        /* assign top data */
        shiftTopText.setText(applicationCache.getCachedIndex().getShiftTopText() + " Shift");
        rigTopText.setText(applicationCache.getCachedIndex().getRigTopText());
    }

    private CachedIndex getCachedModel(){
        CachedIndex cachedIndex = applicationCache.getCachedIndex();
        cachedIndex.setSelectedCrew(selectedCrew);
        cachedIndex.setSelectedForeman(selectedForeman);
        cachedIndex.setSelectedSupervisor(selectedSupervisor);
        return cachedIndex;
    }

    private StartOfShift getStartOfShift(){
        StartOfShift startOfShift = new StartOfShift();
        startOfShift.setAssistantId(applicationCache.getCachedIndex().getSelectedAssistant());
        startOfShift.setBenchSupervisorId(selectedSupervisor);
        startOfShift.setCreatedDate(DateUtil.getTimeStamp());
        startOfShift.setDateInt(DateUtil.getCurrentDay());
        startOfShift.setForemanId(selectedForeman);
        startOfShift.setRigId(applicationCache.getCachedIndex().getSelectedRig());
        startOfShift.setOperatorId(applicationCache.getCachedIndex().getSelectedUser());
        startOfShift.setShiftCrew(selectedCrew);
        startOfShift.setShiftId(applicationCache.getCachedIndex().getSelectedShift());
        /* get from input */
        String m_machineOpenHours = machineOpenHours.getText().toString();
        String m_trammingOpenHours = trammingOpenHours.getText().toString();

        startOfShift.setMachineOpenHours(Double.parseDouble(m_machineOpenHours));
        startOfShift.setTrammingOpenHours(Double.parseDouble(m_trammingOpenHours));

        return startOfShift;
    }

    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    private void businessLogic(){
        //check if operator is not assistant
        CachedIndex cachedIndex = getCachedModel();
        if (cachedIndex.getSelectedAssistant() == cachedIndex.getSelectedUser()){
            showAlertDialog(this,"Start Shift","Assistant cannot be same as operator",false);
            return;
        }

        String m_machineOpenHours = machineOpenHours.getText().toString();
        String m_trammingOpenHours = trammingOpenHours.getText().toString();

        if (m_machineOpenHours.isEmpty() || m_trammingOpenHours.isEmpty()){
            showAlertDialog(this,"Start Shift","Machine hours or Tramming hours needed.",false);
            return;
        }

        //if all good
        CachedIndex index = getCachedModel();

        CachedIndexService cachedIndexService = new CachedIndexService();
        cachedIndexService.saveCache(index);//save to database

        StartShiftService startShiftService = new StartShiftService();
        startShiftService.saveLocal(getStartOfShift());
        finish();
        startActivity(new Intent(StartActivity.this,OperatorInputActivity.class));

    }

    private void initListeners(){
        findViewById(R.id.btnStartShift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessLogic();
            }
        });
    }
}
