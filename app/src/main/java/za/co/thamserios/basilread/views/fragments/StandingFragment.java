package za.co.thamserios.basilread.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;
import java.util.List;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.helper.HelperUtil;
import za.co.thamserios.basilread.models.CachedIndex;
import za.co.thamserios.basilread.models.Crews;
import za.co.thamserios.basilread.models.StandingTypes;
import za.co.thamserios.basilread.services.LookupService;


public class
StandingFragment extends Fragment {

    private Spinner standingList;
    private LookupService lookupService;
    private String status;
    private int selectedStanding;
    private EditText standingReason;
    private TextInputLayout textInputLayout;

    public StandingFragment() {
        // Required empty public constructor
    }


    public static StandingFragment newInstance(String param1, String param2) {
        StandingFragment fragment = new StandingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_standing, container, false);
        // Inflate the layout for this fragment
        lookupService = new LookupService();
        bindViews(view);
        bindListeners(view);
        ApplicationCache.getInstance().setStartTimeProd(new Date());
        return view;
    }

    private void bindViews(View view){
        standingList = (Spinner)view.findViewById(R.id.standingStatusList);
        textInputLayout = (TextInputLayout)view.findViewById(R.id.input_commentsEditText);
        standingReason = (EditText)view.findViewById(R.id.reasonEditText);

    }

    private void bindListeners(View view){
        standingList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StandingTypes op = (StandingTypes)adapterView.getItemAtPosition(i);
                status = op.getTypeName();
                selectedStanding = op.getStandingTypesId();
                if (selectedStanding == 12){
                    //show hidden edit text
                    textInputLayout.setVisibility(View.VISIBLE);
                }else{
                    textInputLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessCase();
            }
        });

        List<StandingTypes> standingTypesList = lookupService.getStandingTypeList();
        ArrayAdapter<StandingTypes> standingTypesArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, standingTypesList);
        standingTypesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        standingList.setAdapter(standingTypesArrayAdapter);
    }

    private void businessCase(){
        if (selectedStanding == 0){
            showAlertDialog(getActivity(),"Standing Time","Status is required.",false);
            return;
        }
        ApplicationCache.getInstance().setBreakdownOthersReason(selectedStanding == 12 ? standingReason.getText().toString() : "");
        ApplicationCache.getInstance().setSelectedStandingTypeId(selectedStanding);
        ApplicationCache.getInstance().setCurrentStatus(status);
        ApplicationCache.getInstance().setCurrentActivity("Standing Time");
        ApplicationCache.getInstance().setPreviousActivity("Standing Time");
        CachedIndex cachedIndex = ApplicationCache.getInstance().getCachedIndex();
        HelperUtil.sendNotification(cachedIndex.getSelectedUser(),HelperUtil.STANDING_COLOR_BLUE,status);

        OperatorStatusCapture.getInstance().saveButton();
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
