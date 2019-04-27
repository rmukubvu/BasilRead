package za.co.thamserios.basilread.services;

import android.util.Log;

import za.co.thamserios.basilread.helper.HttpHelper;
import za.co.thamserios.basilread.models.OperatorStatus;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;
import za.co.thamserios.basilread.utils.DateUtil;

/**
 * Created by robson on 2017/05/11.
 */

public class OperatorStatusUpdateService {

    private ISpringRest springRest;

    public OperatorStatusUpdateService() {
        springRest = new RestEasy();
    }

    public void postStatusToServer(int userId,String color,String status){
        OperatorStatus record = new OperatorStatus();
        record.setActualStatus(status);
        record.setOperatorId(userId);
        record.setPlantNumber(color);
        record.setLookUpId(1);
        record.setStatusDate(DateUtil.getTimeStamp());
        //do post
        try {
            String response = springRest.POST("realtime/operator/status",
                    HttpHelper.getHttpEntity(record));
            if (!response.isEmpty()){
                //deleteRecord(StandingLogs.class,record._id);
                Log.d("BasilReadStatus",response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}