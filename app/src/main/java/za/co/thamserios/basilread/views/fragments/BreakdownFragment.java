package za.co.thamserios.basilread.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.DialogInterface.OnClickListener;
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
import za.co.thamserios.basilread.constants.AppConstants;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.helper.HelperUtil;
import za.co.thamserios.basilread.models.BreakdownTypes;
import za.co.thamserios.basilread.models.CachedIndex;
import za.co.thamserios.basilread.services.LookupService;


public class BreakdownFragment extends Fragment {

    private MaterialSpinner maintananceList;
    private Spinner breakdownStatusList;
    private LookupService lookupService;
    private String status;
    private EditText breakdownReason;
    private TextInputLayout textInputLayout;
    private int selectedBreakdown;

    public BreakdownFragment() {
        // Required empty public constructor
    }

    public static BreakdownFragment newInstance(String param1, String param2) {
        BreakdownFragment fragment = new BreakdownFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breakdown, container, false);
        bindViews(view);
        bindListeners(view);
        ApplicationCache.getInstance().setStartTimeProd(new Date());
        return view;

    }

    private void bindViews(View view){
        maintananceList = (MaterialSpinner)view.findViewById(R.id.maintanceStatusList);
        textInputLayout = (TextInputLayout)view.findViewById(R.id.input_commentsEditText);
        breakdownReason = (EditText)view.findViewById(R.id.reasonEditText);
        breakdownStatusList = (Spinner)view.findViewById(R.id.breakdownStatusList);

        Resources res1 = getResources();
        String[] array1 = res1.getStringArray(R.array.planned_array);
        maintananceList.setItems(array1);
    }

    private static void showYesNo(Context context, int title, String message, OnClickListener yesListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Stay on Page", new DismissDialogListener())
                .setPositiveButton("Leave", yesListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static class DismissDialogListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class leaveOrStayOnPageListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            getActivity().finish();
            Intent intent = new Intent(AppConstants.MAIN_ACTIVITY_INTENT_FILTER);
            startActivity(intent);
        }
    }

    private void bindListeners(View view){
        lookupService = new LookupService();
        List<BreakdownTypes> breakdownTypes = lookupService.getBreakdownTypeList();
        ArrayAdapter<BreakdownTypes> breakdownTypesArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, breakdownTypes);
        breakdownTypesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breakdownStatusList.setAdapter(breakdownTypesArrayAdapter);
        final int title = R.string.dialog_breakdown_title;
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessCase();
            }
        });

        breakdownStatusList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BreakdownTypes op = (BreakdownTypes)adapterView.getItemAtPosition(i);
                status = op.getTypeText();
                selectedBreakdown = op.getBreakdownTypesId();
                if (selectedBreakdown == 13){
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
    }

    private void businessCase(){
        if (selectedBreakdown == 0){
            showAlertDialog(getActivity(),"Breakdown","Status is required.",false);
            return;
        }
        ApplicationCache.getInstance().setBreakdownOthersReason(selectedBreakdown == 13 ? breakdownReason.getText().toString() : "");
        ApplicationCache.getInstance().setSelectedBreakdownTypeId(selectedBreakdown);
        ApplicationCache.getInstance().setCurrentStatus(status);
        ApplicationCache.getInstance().setCurrentActivity("Breakdown");
        ApplicationCache.getInstance().setPreviousActivity("Breakdown");
        CachedIndex cachedIndex = ApplicationCache.getInstance().getCachedIndex();
        HelperUtil.sendNotification(cachedIndex.getSelectedUser(),HelperUtil.BREAKDOWN_COLOR_RED,status);
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
