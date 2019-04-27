package za.co.thamserios.basilread.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.adapters.BreakdownAdapter;
import za.co.thamserios.basilread.services.BreakdownService;


public class BreakdownLogsFragment extends Fragment {

    private BreakdownService breakdownService;

    public BreakdownLogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreakdownLogsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BreakdownLogsFragment newInstance(String param1, String param2) {
        BreakdownLogsFragment fragment = new BreakdownLogsFragment();
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
        View v = inflater.inflate(R.layout.fragment_breakdown_logs, container, false);
        breakdownService = new BreakdownService();
        BreakdownAdapter adapter = new BreakdownAdapter(getActivity(),breakdownService.getBreakdownLogs());
        ListView list = (ListView) v.findViewById(R.id.list);
        list.setAdapter(adapter);
        return v;
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
