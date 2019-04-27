package za.co.thamserios.basilread.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.adapters.BreakdownAdapter;
import za.co.thamserios.basilread.models.BreakdownLogs;
import za.co.thamserios.basilread.services.BreakdownService;


public class BreakdownHistoryFragment extends Fragment {

    private BreakdownService breakdownService;

    public BreakdownHistoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BreakdownHistoryFragment newInstance(String param1, String param2) {
        BreakdownHistoryFragment fragment = new BreakdownHistoryFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_history3, container, false);
        breakdownService = new BreakdownService();
        List<BreakdownLogs> logs = breakdownService.getBreakdownLogs();
        Collections.reverse(logs);
        BreakdownAdapter adapter = new BreakdownAdapter(getActivity(),logs);
        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);
        return view;
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
