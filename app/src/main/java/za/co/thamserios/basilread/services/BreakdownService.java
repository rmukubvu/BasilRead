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
import za.co.thamserios.basilread.models.BreakdownLogs;
import za.co.thamserios.basilread.models.EndOfShift;
import za.co.thamserios.basilread.models.ResponseMessage;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

/**
 * Created by robson on 2017/03/07.
 */

public class BreakdownService extends PostMan implements IServices {

    private IBasilServices client;
    private ObjectMapper mapper;
    private ISpringRest springRest;

    public BreakdownService(){
        mapper = new ObjectMapper();
        //client = ApiClient.getInstance().getApiClient();
        springRest = new RestEasy();
    }

    public void saveLocal(BreakdownLogs model){
        postLocal(model);
    }

    public String saveToUsb(){
        //List<BreakdownLogs> records = findAll(BreakdownLogs.class);
        List<BreakdownLogs> records = findSpecific("flagDown='no'",BreakdownLogs.class);
        String jsonArray = "";
        try {
            jsonArray = mapper.writeValueAsString(records);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (final BreakdownLogs record : records
                ) {
            //delete
            deleteRecord(BreakdownLogs.class, record._id);
        }
        return jsonArray;
    }

    @Override
    public String getName() {
        return "%Breakdown%";
    }

    public List<BreakdownLogs> getBreakdownLogs(){
        return findAll(BreakdownLogs.class);
    }

    @Override
    public void postToServer() {
       //List<BreakdownLogs> records = findAll(BreakdownLogs.class);
       List<BreakdownLogs> records = findSpecific("flagDown='no'",BreakdownLogs.class);
        for (final BreakdownLogs record : records
             ) {
            try {
                String response = springRest.POST("breakdown",
                        HttpHelper.getHttpEntity(record));
                if (!response.isEmpty()){
                   // deleteRecord(BreakdownLogs.class,record._id);
                    record.setFlagDown("yes");
                    //mark for deletion at end of shift
                    update(record);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
