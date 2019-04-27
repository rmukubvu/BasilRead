package za.co.thamserios.basilread.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import za.co.thamserios.basilread.framework.service.IBasilServices;
import za.co.thamserios.basilread.helper.HttpHelper;
import za.co.thamserios.basilread.models.ProductionRecord;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

/**
 * Created by robson on 2017/03/07.
 */

public class ProductionService extends PostMan implements IServices {

    private IBasilServices client;
    private ObjectMapper mapper;
    private ISpringRest springRest;

    public ProductionService(){
        mapper = new ObjectMapper();
        //client = ApiClient.getInstance().getApiClient();
        springRest = new RestEasy();
    }

    public void saveLocal(ProductionRecord model){
        postLocal(model);
    }

    public String saveToUsb(){
        //List<ProductionRecord> records = findAll(ProductionRecord.class);
        List<ProductionRecord> records = findSpecific("flagDown='no'",ProductionRecord.class);
        String jsonArray = "";
        try {
            jsonArray = mapper.writeValueAsString(records);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (final ProductionRecord record : records
                ) {
            //delete
            deleteRecord(ProductionRecord.class, record._id);
        }
        return jsonArray;
    }

    @Override
    public String getName() {
        return "%Production%";
    }

    public List<ProductionRecord> getProductionRecords(){
        return findAll(ProductionRecord.class);
    }

    @Override
    public void postToServer() {
       //List<ProductionRecord> records = findAll(ProductionRecord.class);
       List<ProductionRecord> records = findSpecific("flagDown='no'",ProductionRecord.class);
        for (final ProductionRecord record:records
             ) {
            //do post
            try {
                String response = springRest.POST("production",
                        HttpHelper.getHttpEntity(record));
                if (!response.isEmpty()){
                    //deleteRecord(ProductionRecord.class,record._id);
                    record.setFlagDown("yes");
                    //mark for deletion at end of shift
                    update(record);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*Call<ResponseMessage> call = client.createProduction(record);
            call.enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    ResponseMessage message = response.body();
                    if (message != null){
                        if ( !message.getError() ){
                            //delete
                            deleteRecord(ProductionRecord.class,record._id);
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
