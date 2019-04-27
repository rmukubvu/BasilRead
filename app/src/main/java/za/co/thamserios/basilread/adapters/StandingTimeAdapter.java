package za.co.thamserios.basilread.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.models.BreakdownLogs;
import za.co.thamserios.basilread.models.StandingLogs;
import za.co.thamserios.basilread.models.StandingTypes;
import za.co.thamserios.basilread.services.LookupService;
import za.co.thamserios.basilread.utils.DateUtil;

/**
 * Created by robson on 2017/03/12.
 */

public class StandingTimeAdapter extends BaseAdapter {

    private List<StandingLogs> captureLogs;
    private Context mContext;
    private LookupService lookupService;

    public StandingTimeAdapter(Context context, List<StandingLogs> captureLogs){
        mContext = context;
        lookupService = new LookupService();
        this.captureLogs = captureLogs;
    }

    @Override
    public int getCount() {
        return captureLogs.size();
    }

    @Override
    public StandingLogs getItem(int position) {
        return captureLogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.standing_items_history, null);
            holder = new ViewHolder();
            holder.date = (TextView)convertView.findViewById(R.id.date);
            holder.startTime = (TextView) convertView.findViewById(R.id.startTime);
            holder.endTime = (TextView) convertView.findViewById(R.id.endTime);
            holder.totalTime = (TextView) convertView.findViewById(R.id.totalTime);
            holder.reason = (TextView) convertView.findViewById(R.id.reason);
            convertView.setTag(holder);
        }else {
            // get view holder back
            holder = (ViewHolder) convertView.getTag();
        }
        StandingLogs captureLog = getItem(position);
        if (captureLog!=null) {
            // bind text with view holder content view for efficient use
            holder.date.setText(DateUtil.getFormattedDateFromLong(captureLog.getCreatedDate()));
            holder.startTime.setText(DateUtil.getTimeFromLong(captureLog.getStartTime()));
            holder.endTime.setText(DateUtil.getTimeFromLong(captureLog.getEndTime()));
            //do the duration here
            long diff = captureLog.getEndTime() - captureLog.getStartTime();
            holder.totalTime.setText(DateUtil.getFormattedTime(diff));
            StandingTypes standingTypes = lookupService.getStandingTypeTextFromId(captureLog.getStandingTypeId());
            holder.reason.setText(standingTypes.getTypeName());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView startTime;
        TextView endTime;
        TextView totalTime;
        TextView reason;
    }
}
