package za.co.thamserios.basilread.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.contracts.OperatorStatusCapture;
import za.co.thamserios.basilread.helper.RelativeTimeTextView;
import za.co.thamserios.basilread.navigation.NavigationHelper;


public class DefaultFragment extends Fragment {

    private ApplicationCache applicationCache;
    private Button buttonFinished;
    private RelativeTimeTextView timeTextView;
    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    private Parcelable savedState;

    public DefaultFragment() {
        // Required empty public constructor
    }

    public static DefaultFragment newInstance(String param1, String param2) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            timeTextView.setReferenceTime(new Date().getTime());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onPause() {
        savedState = timeTextView.onSaveInstanceState();
        super.onPause();
    }

    @Override
    public void onResume() {
        timeTextView.onRestoreInstanceState(savedState);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default, container, false);
        applicationCache = ApplicationCache.getInstance();
        timeTextView = (RelativeTimeTextView) view.findViewById(R.id.statusTimeTextView);
        buttonFinished = (Button) view.findViewById(R.id.buttonFinished);

        buttonFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperatorStatusCapture.getInstance().activityClicked(getFragmentIndex());
            }
        });

        TextView statusText = (TextView) view.findViewById(R.id.statusTextView);
        TextView activityTextView = (TextView) view.findViewById(R.id.activityTextView);

        statusText.setText(applicationCache.getCurrentStatus());
        activityTextView.setText(applicationCache.getCurrentActivity());
        activityTextView.setTextColor(getResources().getColor(R.color.primary_isid_red));

        if (applicationCache.getCurrentActivity().startsWith("Production") ||
                applicationCache.getCurrentActivity().startsWith("Standing") ||
                applicationCache.getCurrentActivity().startsWith("Breakdown")) {
            buttonFinished.setVisibility(View.VISIBLE);
            timeTextView.setReferenceTime(new Date().getTime());
            activityTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else if(applicationCache.getCurrentStatus().startsWith("Shift Change")){
            buttonFinished.setText("Sign In");
            buttonFinished.setVisibility(View.VISIBLE);
            timeTextView.setReferenceTime(new Date().getTime());
        }
        else{
            buttonFinished.setVisibility(View.GONE);
        }
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);
        return view;
    }

    private int getFragmentIndex(){
        int index = 0;
        if (applicationCache.getCurrentActivity().startsWith("Production") )
            index = NavigationHelper.PRODUCTION_COMMENT;
        else if (applicationCache.getCurrentActivity().startsWith("Standing") )
            index = NavigationHelper.STANDING_COMMENT;
        else if (applicationCache.getCurrentActivity().startsWith("Breakdown"))
            index = NavigationHelper.BREAKDOWN_COMMENT;
        else if (applicationCache.getCurrentStatus().startsWith("Shift Change"))
            index = NavigationHelper.END_SHIFT;
        return index;
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
