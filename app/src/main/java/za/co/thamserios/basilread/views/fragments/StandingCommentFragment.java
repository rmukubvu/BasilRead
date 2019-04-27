package za.co.thamserios.basilread.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.models.StandingLogs;
import za.co.thamserios.basilread.services.StandingTimeService;
import za.co.thamserios.basilread.utils.DateUtil;

import static za.co.thamserios.basilread.helper.HelperUtil.getDateDiff;


public class StandingCommentFragment extends Fragment {

    private final Context context = getContext();
    private TextView startTimeProd;
    private TextView endTimeProd;
    private TextView totalTimeProd;
    private EditText standingTimeReason;
    private ApplicationCache cache;

    public StandingCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StandingCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StandingCommentFragment newInstance(String param1, String param2) {
        StandingCommentFragment fragment = new StandingCommentFragment();
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
        View v = inflater.inflate(R.layout.fragment_standing_comment, container, false);
        standingTimeReason = (EditText)v.findViewById(R.id.reasonEditText);
        startTimeProd = (TextView)v.findViewById(R.id.startTimeStand);
        endTimeProd = (TextView)v.findViewById(R.id.endTimeStand);
        totalTimeProd = (TextView)v.findViewById(R.id.totalTimeStand);
        cache = ApplicationCache.getInstance();

        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationCache.getInstance().setCurrentStatus("Standing time finished");
                ApplicationCache.getInstance().setCurrentActivity("Select Activity");
                ApplicationCache.getInstance().setPreviousActivity("Standing Time");
                StandingTimeService service = new StandingTimeService();
                service.saveLocal(getStandingTimeModel());
                OperatorStatusCapture.getInstance().closeFragment();
            }
        });
        OperatorStatusCapture.getInstance().hideFabButton();
        setDateDisplay();
        return v;
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

    private StandingLogs getStandingTimeModel(){
        String combinedReason = standingTimeReason.getText().toString();
        Date startTime = ApplicationCache.getInstance().getStartTimeProd();
        Date endTime = new Date();
        StandingLogs standingLogs = new StandingLogs();
        standingLogs.setStartTime(startTime.getTime());
        standingLogs.setEndTime(endTime.getTime());
        standingLogs.setDateInt(DateUtil.getCurrentDay());
        standingLogs.setRigId(cache.getCachedIndex().getSelectedRig());
        standingLogs.setStandingTypeId(cache.getSelectedStandingTypeId());
        standingLogs.setOperatorSheetId(cache.getCachedIndex().getSelectedUser());
        standingLogs.setCreatedDate(DateUtil.getTimeStamp());
        if (!cache.getBreakdownOthersReason().isEmpty())
            combinedReason += " - " + cache.getBreakdownOthersReason();
        standingLogs.setStandingReason(combinedReason);
        standingLogs.setFlagDown("no");
        return standingLogs;
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
