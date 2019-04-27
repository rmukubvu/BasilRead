package za.co.thamserios.basilread.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.models.CachedIndex;
import za.co.thamserios.basilread.models.OperationsUser;
import za.co.thamserios.basilread.models.Shift;
import za.co.thamserios.basilread.models.SiteConfig;
import za.co.thamserios.basilread.services.CachedIndexService;
import za.co.thamserios.basilread.services.LookupService;
import za.co.thamserios.basilread.services.StartShiftService;
import za.co.thamserios.basilread.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private Spinner operatorList;
    private Spinner assistantList;
    private Spinner rigs;
    private Spinner shifts;

    private int selectedUser;
    private int selectedAssistant;
    private int selectedRig;
    private String rigText;
    private String operatorName;
    private String operatorAssistant;
    private String selectedShiftText;
    private int selectedShift;
    private UserService userService;
    private LookupService lookupService;
    private ApplicationCache applicationCache;

    private TextView rigTopText;
    private TextView shiftTopText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initListeners();
        bindViews();
        bindDataToSpinners();
    }

    private void bindDataToSpinners(){
        userService = new UserService();
        lookupService = new LookupService();

        List<OperationsUser> operators = userService.getOperators();
        List<OperationsUser> assistants = userService.getAssistants();
        List<SiteConfig> rig = lookupService.getRigsList();
        List<Shift> shift = lookupService.getShiftList();

        ArrayAdapter<SiteConfig> rigsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rig);
        rigsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rigs.setAdapter(rigsAdapter);

        ArrayAdapter<Shift> shiftsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shift);
        shiftsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shifts.setAdapter(shiftsAdapter);

        ArrayAdapter<OperationsUser> operatorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operators);
        operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorList.setAdapter(operatorAdapter);

        ArrayAdapter<OperationsUser> assistantAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assistants);
        assistantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assistantList.setAdapter(assistantAdapter);
    }

    private void bindViews(){

        shiftTopText = (TextView)findViewById(R.id.shiftTopText);
        rigTopText = (TextView)findViewById(R.id.rigTopText);

        rigs = (Spinner)findViewById(R.id.rigList);
        rigs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SiteConfig op = (SiteConfig)adapterView.getItemAtPosition(i);
                selectedRig = op.getSiteConfigId();
                rigText = op.getSiteName();
                rigTopText.setText(op.getSiteName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        operatorList = (Spinner) findViewById(R.id.operatorsList);
        operatorList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperationsUser op = (OperationsUser)adapterView.getItemAtPosition(i);
                selectedUser = op.getOperationsId();
                operatorName = op.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        assistantList = (Spinner) findViewById(R.id.userList); //selectedAssistant = item.getOperationsId();
        assistantList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperationsUser op = (OperationsUser)adapterView.getItemAtPosition(i);
                selectedAssistant = op.getOperationsId();
                operatorAssistant = op.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shifts = (Spinner)findViewById(R.id.shiftList);
        shifts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Shift op = (Shift)adapterView.getItemAtPosition(i);
                selectedShift = op.getShiftId();
                selectedShiftText = op.getShiftName();
                shiftTopText.setText(op.getShiftName() + " Shift");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,USBActivity.class));
            }
        });
        applicationCache = ApplicationCache.getInstance();
        applicationCache.setCurrentStatus("Shift Started");
        applicationCache.setCurrentActivity("Select Activity");
    }

    private void initListeners(){
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessLogic();
            }
        });
    }

    private void businessLogic(){

        if (selectedRig == 0 || selectedShift == 0){
            showAlertDialog(this,"Start Shift","Shift or Rig cannot be empty",false);
            return;
        }
        //if all good
        applicationCache.setCurrentAssistant(operatorAssistant);
        applicationCache.setCurrentOperator(operatorName);
        //applicationCache.setCachedIndex(getCachedModel());
        //if all good
        CachedIndex index = getCachedModel();
        CachedIndexService cachedIndexService = new CachedIndexService();
        cachedIndexService.saveCache(index);//save to database
        finish();
        startActivity(new Intent(LoginActivity.this,StartActivity.class));
    }

    private CachedIndex getCachedModel(){
        CachedIndex cachedIndex = new CachedIndex();
        cachedIndex.setRigTopText(rigText);
        cachedIndex.setShiftTopText(selectedShiftText);
        cachedIndex.setSelectedRig(selectedRig);
        cachedIndex.setSelectedAssistant(selectedAssistant);
        cachedIndex.setSelectedUser(selectedUser);
        cachedIndex.setSelectedShift(selectedShift);
        return cachedIndex;
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
}
