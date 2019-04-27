package za.co.thamserios.basilread.services;

import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.thamserios.basilread.ContextUtil;
import za.co.thamserios.basilread.framework.service.ApiClient;
import za.co.thamserios.basilread.framework.service.IBasilServices;
import za.co.thamserios.basilread.helper.HttpHelper;
import za.co.thamserios.basilread.models.ResponseMessage;
import za.co.thamserios.basilread.models.StandingLogs;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

/**
 * Created by robson on 2017/03/07.
 */

public class StandingTimeService extends PostMan implements IServices {

    private IBasilServices client;
    private ObjectMapper mapper;
    private ISpringRest springRest;


    public StandingTimeService(){
        mapper = new ObjectMapper();
        //client = ApiClient.getInstance().getApiClient();
        springRest = new RestEasy();
    }

    public void saveLocal(StandingLogs model){
        postLocal(model);
    }

    public String saveToUsb(){
        //List<StandingLogs> records = findAll(StandingLogs.class);
        List<StandingLogs> records = findSpecific("flagDown='no'",StandingLogs.class);
        String jsonArray = "";
        try {
            jsonArray = mapper.writeValueAsString(records);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (final StandingLogs record : records
                ) {
            //delete
            deleteRecord(StandingLogs.class, record._id);
        }
        return jsonArray;
    }

    @Override
    public String getName() {
        return "%StandingTime%";
    }

    public List<StandingLogs> getStandingTimeLogs(){
        return findAll(StandingLogs.class);
    }

    @Override
    public void postToServer() {
       //List<StandingLogs> records = findAll(StandingLogs.class);
       List<StandingLogs> records = findSpecific("flagDown='no'",StandingLogs.class);
        for (final StandingLogs record : records
             ) {
           //do post
            try {
                String response = springRest.POST("standingtime",
                        HttpHelper.getHttpEntity(record));
                if (!response.isEmpty()){
                    //deleteRecord(StandingLogs.class,record._id);
                    record.setFlagDown("yes");
                    //mark for deletion at end of shift
                    update(record);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*Call<ResponseMessage> call = client.createStandingTime(record);
            call.enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    ResponseMessage message = response.body();
                    if (message != null){
                        if ( !message.getError() ){
                            //delete
                            deleteRecord(StandingLogs.class,record._id);
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseMessage> call, Throwable t) {
                    Toast.makeText(ContextUtil.getContext(), "Service Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });*/
        }
    }
}
