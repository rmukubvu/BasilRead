package za.co.thamserios.basilread.services;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.thamserios.basilread.framework.service.ApiClient;
import za.co.thamserios.basilread.framework.service.IBasilServices;
import za.co.thamserios.basilread.models.BreakdownTypes;
import za.co.thamserios.basilread.models.Crews;
import za.co.thamserios.basilread.models.ProductionType;
import za.co.thamserios.basilread.models.Shift;
import za.co.thamserios.basilread.models.SiteConfig;
import za.co.thamserios.basilread.models.StandingTypes;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

/**
 * Created by robson on 2017/03/07.
 */

public class LookupService extends PostMan {

    private IBasilServices client = ApiClient.getInstance().getApiClient();
    private ISpringRest springRest = new RestEasy();

    public void getAllLookups(){
        getBreakdownTypes();
        getCrews();
        getProductionType();
        getShifts();
        getStandingTypes();
        getSiteConfig();
    }

    public StandingTypes getStandingTypeTextFromId(int id){
        return findSpecific(String.format("standingTypesId=%d",id),StandingTypes.class).get(0);
    }

    public ProductionType getProductionTypeTextFromId(int id){
        return findSpecific(String.format("productionTypeId=%d",id),ProductionType.class).get(0);
    }

    private void getShifts(){
        try {
            Shift[] shifts = springRest.GET("shifts",Shift[].class);
            List<Shift> shiftList = Arrays.asList(shifts);
            replace(shiftList,Shift.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Call<List<Shift>> users = client.listAllShifts();
        users.enqueue(new Callback<List<Shift>>() {
            @Override
            public void onResponse(Call<List<Shift>> call, Response<List<Shift>> response) {
                if (response.isSuccessful()) {
                    List<Shift> result = response.body();
                    replace(result,Shift.class);
                }
            }
            @Override
            public void onFailure(Call<List<Shift>> call, Throwable t) {

            }
        });*/
    }

    private void getCrews(){
        try {
            Crews[] records = springRest.GET("crews/all",Crews[].class);
            List<Crews> list = Arrays.asList(records);
            replace(list,Crews.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Call<List<Crews>> users = client.listAllCrews();
        users.enqueue(new Callback<List<Crews>>() {
            @Override
            public void onResponse(Call<List<Crews>> call, Response<List<Crews>> response) {
                if (response.isSuccessful()) {
                    List<Crews> result = response.body();
                    replace(result,Crews.class);
                }
            }
            @Override
            public void onFailure(Call<List<Crews>> call, Throwable t) {

            }
        });*/
    }


    private void getProductionType(){
        try {
            ProductionType[] records = springRest.GET("lookup/productiontypes",ProductionType[].class);
            List<ProductionType> list = Arrays.asList(records);
            replace(list,ProductionType.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Call<List<ProductionType>> users = client.listAllProductionTypes();
        users.enqueue(new Callback<List<ProductionType>>() {
            @Override
            public void onResponse(Call<List<ProductionType>> call, Response<List<ProductionType>> response) {
                if (response.isSuccessful()) {
                    List<ProductionType> result = response.body();
                    replace(result,ProductionType.class);
                }
            }
            @Override
            public void onFailure(Call<List<ProductionType>> call, Throwable t) {

            }
        });*/
    }


    private void getStandingTypes(){
        try {
            StandingTypes[] records = springRest.GET("lookup/standingtypes",StandingTypes[].class);
            List<StandingTypes> list = Arrays.asList(records);
            replace(list,StandingTypes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Call<List<StandingTypes>> users = client.listAllStandingTimeTypes();
        users.enqueue(new Callback<List<StandingTypes>>() {
            @Override
            public void onResponse(Call<List<StandingTypes>> call, Response<List<StandingTypes>> response) {
                if (response.isSuccessful()) {
                    List<StandingTypes> result = response.body();
                    replace(result,StandingTypes.class);
                }
            }
            @Override
            public void onFailure(Call<List<StandingTypes>> call, Throwable t) {

            }
        });*/
    }


    private void getBreakdownTypes(){
        try {
            BreakdownTypes[] records = springRest.GET("lookup/breakdowntypes",BreakdownTypes[].class);
            List<BreakdownTypes> list = Arrays.asList(records);
            replace(list,BreakdownTypes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Call<List<BreakdownTypes>> users = client.listAllBreakdownTypes();
        users.enqueue(new Callback<List<BreakdownTypes>>() {
            @Override
            public void onResponse(Call<List<BreakdownTypes>> call, Response<List<BreakdownTypes>> response) {
                if (response.isSuccessful()) {
                    List<BreakdownTypes> result = response.body();
                    replace(result,BreakdownTypes.class);
                }
            }
            @Override
            public void onFailure(Call<List<BreakdownTypes>> call, Throwable t) {

            }
        });*/
    }

    private void getSiteConfig(){
        try {
            SiteConfig[] records = springRest.GET("config/rigs",SiteConfig[].class);
            List<SiteConfig> list = Arrays.asList(records);
            replace(list,SiteConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Call<List<SiteConfig>> users = client.listAllRigs();
        users.enqueue(new Callback<List<SiteConfig>>() {
            @Override
            public void onResponse(Call<List<SiteConfig>> call, Response<List<SiteConfig>> response) {
                if (response.isSuccessful()) {
                    List<SiteConfig> result = response.body();
                    replace(result,SiteConfig.class);
                }
            }
            @Override
            public void onFailure(Call<List<SiteConfig>> call, Throwable t) {

            }
        });*/
    }

    public List<Shift> getShiftList(){
        return findAll(Shift.class);
    }

    public List<SiteConfig> getRigsList(){
        return findAll(SiteConfig.class);
    }

    public List<ProductionType> getProductionTypeList(){
        return findAll(ProductionType.class);
    }

    public List<BreakdownTypes> getBreakdownTypeList(){
        return findAll(BreakdownTypes.class);
    }

    public List<StandingTypes> getStandingTypeList(){
        return findAll(StandingTypes.class);
    }

    public List<Crews> getCrewsList(){
        return findAll(Crews.class);
    }

    @Override
    public void postToServer() {

    }
}
