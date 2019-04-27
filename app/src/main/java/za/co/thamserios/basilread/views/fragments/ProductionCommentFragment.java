package za.co.thamserios.basilread.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.models.ProductionCache;
import za.co.thamserios.basilread.models.ProductionRecord;
import za.co.thamserios.basilread.services.AutocompleteService;
import za.co.thamserios.basilread.services.ProductionService;
import za.co.thamserios.basilread.utils.DateUtil;

import static za.co.thamserios.basilread.helper.HelperUtil.getDateDiff;


public class ProductionCommentFragment extends Fragment {

    private TextView startTimeProd;
    private TextView endTimeProd;
    private TextView totalTimeProd;
    private TextView benchNumber;
    private TextView holeNumber;
    private TextView holeDepthRequired;
    private EditText holeDepthActual;
    private EditText reasons;
    private AutoCompleteTextView benchEndNumber;
    private ApplicationCache cache;
    private AutocompleteService autocompleteService;

    public ProductionCommentFragment() {
        // Required empty public constructor
    }


    public static ProductionCommentFragment newInstance(String param1, String param2) {
        ProductionCommentFragment fragment = new ProductionCommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OperatorStatusCapture.getInstance().hideFabButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_production_comment, container, false);
        cache = ApplicationCache.getInstance();
        autocompleteService = new AutocompleteService();
        benchNumber = (TextView) v.findViewById(R.id.productionBenchNumber);
        holeNumber = (TextView) v.findViewById(R.id.productionHoleNumber);
        holeDepthRequired = (TextView) v.findViewById(R.id.requiredMeters);
        holeDepthActual = (EditText) v.findViewById(R.id.holeDrilled);
        reasons = (EditText) v.findViewById(R.id.commentsEditText);
        benchEndNumber = (AutoCompleteTextView)v.findViewById(R.id.benchEndNumber);
        startTimeProd = (TextView)v.findViewById(R.id.startTimeProd);
        endTimeProd = (TextView)v.findViewById(R.id.endTimeProd);
        totalTimeProd = (TextView)v.findViewById(R.id.totalTimeProd);
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessCase();
            }
        });
        //autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, autocompleteService.getAutoCompleteList());
        benchEndNumber.setAdapter(adapter);
        OperatorStatusCapture.getInstance().hideFabButton();
        setDateDisplay();
        return v;
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


    private void businessCase(){
        if (holeDepthActual.getText().toString().isEmpty() || benchEndNumber.getText().toString().isEmpty()){
            showAlertDialog(getActivity(),"Production Capture","Actual hole depth and Bench end number required",false);
            return;
        }

        ApplicationCache.getInstance().setCurrentStatus("Hole drilled finished");
        ApplicationCache.getInstance().setCurrentActivity("Select Activity");
        ApplicationCache.getInstance().setPreviousActivity("Production");
        ProductionService productionService = new ProductionService();
        productionService.saveLocal(getProductionModel());
        List<String> autocompletes = autocompleteService.getAutoCompleteList();
        String benchEndNum = benchEndNumber.getText().toString();
        if (!autocompletes.contains(benchEndNum)){
            autocompleteService.saveCommonWords(benchEndNum);
        }
        OperatorStatusCapture.getInstance().closeFragment();
    }

    private void setDateDisplay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startTime = ApplicationCache.getInstance().getStartTimeProd();
        Date endTime = new Date();
        long totalMinutes = getDateDiff(startTime,endTime);
        ProductionCache productionCache = cache.getProductionCache();
        benchNumber.setText(productionCache.getBenchNumber());
        holeNumber.setText(productionCache.getHoleNumber());
        holeDepthRequired.setText(productionCache.getHoleDepthRequired());
        startTimeProd.setText(simpleDateFormat.format(startTime));
        endTimeProd.setText(simpleDateFormat.format(endTime));
        totalTimeProd.setText(String.valueOf(totalMinutes));
    }

    private ProductionRecord getProductionModel(){
        ProductionCache productionCache = cache.getProductionCache();
        ProductionRecord productionRecord = new ProductionRecord();
        Date startTime = ApplicationCache.getInstance().getStartTimeProd();
        Date endTime = new Date();
        productionRecord.setStartTime(startTime.getTime());
        productionRecord.setEndTime(endTime.getTime());
        productionRecord.setDateInt(DateUtil.getCurrentDay());
        productionRecord.setRigId(cache.getCachedIndex().getSelectedRig());
        productionRecord.setDrillType(productionCache.getDrillType());
        productionRecord.setBenchNumber(productionCache.getBenchNumber());
        productionRecord.setCreatedDate(DateUtil.getTimeStamp());
        productionRecord.setHoleNumber(productionCache.getHoleNumber());
        productionRecord.setHoleDepthRequired(productionCache.getHoleDepthRequired());
        productionRecord.setStatusId(cache.getSelectedProductionTypeId());
        productionRecord.setBitSize(productionCache.getBitSize());
        productionRecord.setHoles(1);
        productionRecord.setBenchEndNumber(benchEndNumber.getText().toString());
        productionRecord.setFlagDown("no");
        productionRecord.setRodSize(productionCache.getRodSize());
        BigDecimal meters = new BigDecimal(holeDepthActual.getText().toString());
        productionRecord.setMeters(meters);
        productionRecord.setOperatorId(cache.getCachedIndex().getSelectedUser());

        return productionRecord;
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
