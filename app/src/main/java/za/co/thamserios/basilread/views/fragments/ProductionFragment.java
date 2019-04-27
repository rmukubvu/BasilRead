package za.co.thamserios.basilread.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import za.co.thamserios.basilread.models.ProductionCache;
import za.co.thamserios.basilread.models.ProductionType;
import za.co.thamserios.basilread.services.AutocompleteService;
import za.co.thamserios.basilread.services.LookupService;


public class ProductionFragment extends Fragment {

    private MaterialSpinner drillingTypeList;
    private Spinner productionStatusList;
    private MaterialSpinner bitsizeSpinner;
    private MaterialSpinner rodSizeSpinner;
    private LookupService lookupService;
    private AutocompleteService autocompleteService;
    private String status;

    private AutoCompleteTextView benchEditText;
    //private AutoCompleteTextView hammerEditText;
    private EditText holeNumberEditText;
    private EditText holeDepthEditText;

    public ProductionFragment() {
        // Required empty public constructor
    }

    public static ProductionFragment newInstance(String param1, String param2) {
        ProductionFragment fragment = new ProductionFragment();
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
        lookupService = new LookupService();
        autocompleteService = new AutocompleteService();
        View view = inflater.inflate(R.layout.fragment_production, container, false);
        benchEditText = (AutoCompleteTextView)view.findViewById(R.id.benchEditText);
        //hammerEditText = (AutoCompleteTextView)view.findViewById(R.id.hammerEditText);
        holeNumberEditText = (EditText)view.findViewById(R.id.holeNumberEditText);
        holeDepthEditText = (EditText)view.findViewById(R.id.holeDepthEditText);

        drillingTypeList = (MaterialSpinner)view.findViewById(R.id.drillTypeList);
        Resources res = getResources();
        String[] drills = res.getStringArray(R.array.drills_array);
        drillingTypeList.setItems(drills);

        productionStatusList = (Spinner)view.findViewById(R.id.productionStatusList);
        productionStatusList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductionType op = (ProductionType)adapterView.getItemAtPosition(i);
                status = op.getTypeText();
                ApplicationCache.getInstance().setSelectedProductionTypeId(op.getProductionTypeId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<ProductionType> productionTypes = lookupService.getProductionTypeList();
        ArrayAdapter<ProductionType> productionTypeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, productionTypes);
        productionTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productionStatusList.setAdapter(productionTypeArrayAdapter);

        bitsizeSpinner = (MaterialSpinner)view.findViewById(R.id.bitSizeSpinner);
        Resources res2 = getResources();
        String[] array2 = res2.getStringArray(R.array.bit_size);
        bitsizeSpinner.setItems(array2);

        rodSizeSpinner = (MaterialSpinner)view.findViewById(R.id.rodeSizeSpinner);
        Resources res3 = getResources();
        String[] array3 = res3.getStringArray(R.array.rod_size);
        rodSizeSpinner.setItems(array3);

        ApplicationCache.getInstance().setStartTimeProd(new Date());
        //bit_size

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
            }
        });

        //autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, autocompleteService.getAutoCompleteList());
        benchEditText.setAdapter(adapter);
        //hammerEditText.setAdapter(adapter);

        return view;
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

    private void onSave(){
        ApplicationCache.getInstance().setCurrentStatus(status);
        ApplicationCache.getInstance().setCurrentActivity("Production");
        ApplicationCache.getInstance().setPreviousActivity("Production");
        ProductionCache productionCache = getProductionCache();
        if (productionCache.getBenchNumber().isEmpty() ||
                holeDepthEditText.getText().toString().isEmpty() ||
                holeDepthEditText.getText().toString().isEmpty()){
            showAlertDialog(getActivity(),"Production Capture","Bench number , hole number and hole depth are required",false);
            return;
        }
        List<String> autocompletes = autocompleteService.getAutoCompleteList();
        if (!autocompletes.contains(productionCache.getBenchNumber())){
            autocompleteService.saveCommonWords(productionCache.getBenchNumber());
        }
        /*if (!autocompletes.contains(productionCache.getHammer())){
            autocompleteService.saveCommonWords(productionCache.getHammer());
        }*/
        ApplicationCache.getInstance().setProductionCache(productionCache);
        CachedIndex cachedIndex = ApplicationCache.getInstance().getCachedIndex();
        HelperUtil.sendNotification(cachedIndex.getSelectedUser(),HelperUtil.PRODUCTION_COLOR_GREEN,status);
        OperatorStatusCapture.getInstance().saveButton();
    }

    private ProductionCache getProductionCache(){
        ProductionCache productionCache = new ProductionCache();
        productionCache.setBenchNumber(benchEditText.getText().toString());
        productionCache.setHoleDepthRequired(holeDepthEditText.getText().toString());
        productionCache.setHoleNumber(holeNumberEditText.getText().toString());
        //productionCache.setHammer(hammerEditText.getText().toString());
        productionCache.setDrillType(drillingTypeList.getText().toString());
        productionCache.setRodSize(rodSizeSpinner.getText().toString());
        productionCache.setBitSize(bitsizeSpinner.getText().toString());
        return productionCache;
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
