package za.co.thamserios.basilread.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.models.EndOfShift;
import za.co.thamserios.basilread.navigation.NavigationHelper;
import za.co.thamserios.basilread.services.EndShiftService;
import za.co.thamserios.basilread.utils.DateUtil;


public class EOSFragment extends Fragment {

    private EndShiftService endShiftService;
    private EditText commentsEditText;
    private EditText closeHoursMachine;
    private EditText closetrammingHours;

    public EOSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EOSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EOSFragment newInstance(String param1, String param2) {
        EOSFragment fragment = new EOSFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_eo, container, false);
        endShiftService = new EndShiftService();
        commentsEditText = (EditText)v.findViewById(R.id.commentsEditText);
        closeHoursMachine = (EditText)v.findViewById(R.id.closeHoursMachine);
        closetrammingHours = (EditText)v.findViewById(R.id.closetrammingHours);
        // Inflate the layout for this fragment
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessCase();
            }
        });
        OperatorStatusCapture.getInstance().hideFabButton();
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
        String m_machineOpenHours = closeHoursMachine.getText().toString();
        String m_trammingOpenHours = closetrammingHours.getText().toString();

        if (m_machineOpenHours.isEmpty() || m_trammingOpenHours.isEmpty()){
            showAlertDialog(getActivity(),"End Shift","Machine hours or Tramming hours needed.",false);
            return;
        }

        ApplicationCache.getInstance().setCurrentStatus("Shift Change");
        ApplicationCache.getInstance().setCurrentActivity("No activity selected");
        EndShiftService startShiftService = new EndShiftService();
        startShiftService.saveLocal(getEndOfShiftModel());
        OperatorStatusCapture.getInstance().closeFragmentWithAction(NavigationHelper.END_SHIFT);
    }

    private EndOfShift getEndOfShiftModel(){
        ApplicationCache cache = ApplicationCache.getInstance();
        EndOfShift endOfShift = new EndOfShift();
                /* get from input */
        String m_machineCloseHours = closeHoursMachine.getText().toString();
        String m_trammingOpenHours = closetrammingHours.getText().toString();
        endOfShift.setMachineClosing(Double.parseDouble(m_machineCloseHours));
        endOfShift.setTrammingClosing(Double.parseDouble(m_trammingOpenHours));
        endOfShift.setDateInt(DateUtil.getCurrentDay());
        endOfShift.setEndTime(DateUtil.getTimeStamp());
        endOfShift.setReason(commentsEditText.getText().toString());
        endOfShift.setRigId(cache.getCachedIndex().getSelectedRig());
        endOfShift.setOperatorId(cache.getCachedIndex().getSelectedUser());
        return endOfShift;
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
