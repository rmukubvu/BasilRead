package za.co.thamserios.basilread.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import za.co.thamserios.basilread.framework.service.IBasilServices;
import za.co.thamserios.basilread.helper.HttpHelper;
import za.co.thamserios.basilread.models.BreakdownLogs;
import za.co.thamserios.basilread.models.CachedIndex;
import za.co.thamserios.basilread.models.EndOfShift;
import za.co.thamserios.basilread.models.ProductionRecord;
import za.co.thamserios.basilread.models.StandingLogs;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

/**
 * Created by robson on 2017/03/07.
 */

public class EndShiftService extends PostMan implements IServices {

    private IBasilServices client;
    private ObjectMapper mapper;
    private ISpringRest springRest;

    public EndShiftService(){
        mapper = new ObjectMapper();
        springRest = new RestEasy();
    }

    public void saveLocal(EndOfShift model){
        postLocal(model);
        //clear the rest
        deleteSpecificRecords(ProductionRecord.class);
        deleteSpecificRecords(StandingLogs.class);
        deleteSpecificRecords(BreakdownLogs.class);
        //delete
        deleteAllRecords(CachedIndex.class);
    }

    public String saveToUsb(){
        List<EndOfShift> records = findAll(EndOfShift.class);
        String jsonArray = "";
        try {
            jsonArray = mapper.writeValueAsString(records);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (final EndOfShift record : records
                ) {
            //delete
            deleteRecord(EndOfShift.class, record._id);
        }
        return jsonArray;
    }

    @Override
    public String getName() {
        return "%EndOfShift%";
    }

    @Override
    public void postToServer() {
       List<EndOfShift> records = findAll(EndOfShift.class);
        for (final EndOfShift record : records
             ) {
            try {
                String response = springRest.POST("endofshift",
                        HttpHelper.getHttpEntity(record));
                if (!response.isEmpty()){
                    deleteRecord(EndOfShift.class,record._id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*Call<ResponseMessage> call = client.createEndOfShift(record);
            call.enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    ResponseMessage message = response.body();
                    if (message != null){
                        if ( !message.getError() ){
                            //delete
                            deleteRecord(EndOfShift.class,record._id);
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
