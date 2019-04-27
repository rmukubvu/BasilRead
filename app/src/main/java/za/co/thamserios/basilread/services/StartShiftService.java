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
import za.co.thamserios.basilread.models.StartOfShift;
import za.co.thamserios.basilread.models.ResponseMessage;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

/**
 * Created by robson on 2017/03/07.
 */

public class StartShiftService extends PostMan implements IServices {

    private IBasilServices client;
    private ObjectMapper mapper;
    private ISpringRest springRest;

    public StartShiftService(){
        mapper = new ObjectMapper();
        //client = ApiClient.getInstance().getApiClient();
        springRest = new RestEasy();
    }

    public void saveLocal(StartOfShift model){
        postLocal(model);
    }

    public String saveToUsb() {
        List<StartOfShift> records = findAll(StartOfShift.class);
        String jsonArray = "";
        try {
            jsonArray = mapper.writeValueAsString(records);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (final StartOfShift record : records
                ) {
            //delete
            deleteRecord(StartOfShift.class, record._id);
        }
        return jsonArray;
    }

    @Override
    public String getName() {
        return "%StartService%";
    }

    @Override
    public void postToServer() {
       List<StartOfShift> records = findAll(StartOfShift.class);
        for (final StartOfShift record:records
             ) {
            //do post
            try {
                String response = springRest.POST("startofshift",
                        HttpHelper.getHttpEntity(record));
                if (!response.isEmpty()){
                    deleteRecord(StartOfShift.class,record._id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
