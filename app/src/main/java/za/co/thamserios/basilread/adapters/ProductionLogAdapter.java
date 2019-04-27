package za.co.thamserios.basilread.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.models.CaptureLog;
import za.co.thamserios.basilread.models.ProductionRecord;
import za.co.thamserios.basilread.utils.DateUtil;

/**
 * Created by robson on 2017/01/23.
 */

public class ProductionLogAdapter extends BaseAdapter {

    private List<ProductionRecord> captureLogs;
    private Context mContext;

    public ProductionLogAdapter(Context context, List<ProductionRecord> captureLogs){
        mContext = context;
        this.captureLogs = captureLogs;
    }

    @Override
    public int getCount() {
        return captureLogs.size();
    }

    @Override
    public ProductionRecord getItem(int i) {
        return captureLogs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_single, null);
            holder = new ViewHolder();
            holder.date = (TextView)view.findViewById(R.id.date);
            holder.hole = (TextView)view.findViewById(R.id.holeNumber);
            holder.startTime = (TextView) view.findViewById(R.id.startTime);
            holder.endTime = (TextView) view.findViewById(R.id.endTime);
            holder.totalTime = (TextView) view.findViewById(R.id.totalTime);
            holder.holeRequired = (TextView) view.findViewById(R.id.holeRequired);
            holder.holeDrilled = (TextView) view.findViewById(R.id.holeDrilled);
            view.setTag(holder);
        }else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }


        ProductionRecord captureLog = getItem(i);
        if (captureLog!=null) {
            // bind text with view holder content view for efficient use
            holder.date.setText(DateUtil.getFormattedDateFromLong(captureLog.getCreatedDate()));
            holder.hole.setText(captureLog.getHoleNumber());
            holder.startTime.setText(DateUtil.getTimeFromLong(captureLog.getStartTime()));
            holder.endTime.setText(DateUtil.getTimeFromLong(captureLog.getEndTime()));
            long diff = captureLog.getEndTime() - captureLog.getStartTime();
            holder.totalTime.setText(DateUtil.getFormattedTime(diff));
            holder.holeRequired.setText(captureLog.getHoleDepthRequired());
            holder.holeDrilled.setText(String.valueOf(captureLog.getMeters()));
        }
        return view;
    }

    static class ViewHolder {
        TextView date;
        TextView hole;
        TextView startTime;
        TextView endTime;
        TextView totalTime;
        TextView holeRequired;
        TextView holeDrilled;
    }
}
