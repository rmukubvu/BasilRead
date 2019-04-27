package za.co.thamserios.basilread.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.models.BreakdownLogs;
import za.co.thamserios.basilread.models.BreakdownTypes;
import za.co.thamserios.basilread.models.OperationsUser;
import za.co.thamserios.basilread.services.BreakdownService;
import za.co.thamserios.basilread.services.StandingTimeService;
import za.co.thamserios.basilread.services.UserService;
import za.co.thamserios.basilread.utils.DateUtil;

import static za.co.thamserios.basilread.helper.HelperUtil.getDateDiff;

public class BreakdownCommentFragment extends Fragment {

    private final Context context = getContext();
    private Spinner mechanicSpinner;
    private TextView startTimeProd;
    private TextView endTimeProd;
    private TextView totalTimeProd;
    private UserService userService;
    private EditText breakdownReason;
    private ApplicationCache cache;

    public BreakdownCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreakdownCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BreakdownCommentFragment newInstance(String param1, String param2) {
        BreakdownCommentFragment fragment = new BreakdownCommentFragment();
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
        View v = inflater.inflate(R.layout.fragment_breakdown_comment, container, false);
        userService = new UserService();
        cache = ApplicationCache.getInstance();
        breakdownReason = (EditText)v.findViewById(R.id.reasonEditText);
        startTimeProd = (TextView)v.findViewById(R.id.startTimeBreak);
        endTimeProd = (TextView)v.findViewById(R.id.endTimeBreak);
        totalTimeProd = (TextView)v.findViewById(R.id.totalTimeBreak);
        mechanicSpinner = (Spinner) v.findViewById(R.id.mechanicsList);
        List<OperationsUser> mechanics = userService.getMechanics();

        ArrayAdapter<OperationsUser> mechanicAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mechanics);
        mechanicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mechanicSpinner.setAdapter(mechanicAdapter);

        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessCase();
            }
        });
        OperatorStatusCapture.getInstance().hideFabButton();
        setDateDisplay();
        return v;
    }


    private void businessCase(){
        if (breakdownReason.getText().toString().isEmpty()){
            showAlertDialog(getActivity(),"Breakdown","Reason is required.",false);
            return;
        }
        ApplicationCache.getInstance().setCurrentStatus("Breakdown resolved");
        ApplicationCache.getInstance().setCurrentActivity("Select Activity");
        ApplicationCache.getInstance().setPreviousActivity("Breakdown");
        BreakdownService service = new BreakdownService();
        service.saveLocal(getBreakdownLogsModel());
        cache.setBreakdownOthersReason("");
        OperatorStatusCapture.getInstance().closeFragment();
    }

    private void setDateDisplay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startTime = ApplicationCache.getInstance().getStartTimeProd();
        Date endTime = new Date();
        long totalMinutes = getDateDiff(startTime,endTime);
        startTimeProd.setText(simpleDateFormat.format(startTime));
        endTimeProd.setText(simpleDateFormat.format(endTime));
        totalTimeProd.setText(String.valueOf(totalMinutes));
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

    private BreakdownLogs getBreakdownLogsModel(){
        Date startTime = ApplicationCache.getInstance().getStartTimeProd();
        Date endTime = new Date();
        BreakdownLogs breakdownLogs = new BreakdownLogs();
        breakdownLogs.setStartTime(startTime.getTime());
        breakdownLogs.setEndTime(endTime.getTime());
        breakdownLogs.setDateInt(DateUtil.getCurrentDay());
        breakdownLogs.setRigId(cache.getCachedIndex().getSelectedRig());
        breakdownLogs.setBreakdownTypeId(cache.getSelectedBreakdownTypeId());
        breakdownLogs.setOperatorSheetId(cache.getCachedIndex().getSelectedUser());
        breakdownLogs.setCreatedDate(DateUtil.getTimeStamp());
        breakdownLogs.setOptionalText(breakdownReason.getText().toString());
        breakdownLogs.setLookUpDataId(1);
        breakdownLogs.setBreakdownText(cache.getBreakdownOthersReason());
        breakdownLogs.setFlagDown("no");
        return breakdownLogs;
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
