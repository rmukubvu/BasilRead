package za.co.thamserios.basilread.services;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.thamserios.basilread.constants.AppConstants;
import za.co.thamserios.basilread.framework.service.ApiClient;
import za.co.thamserios.basilread.framework.service.IBasilServices;
import za.co.thamserios.basilread.helper.DatabaseHelper;
import za.co.thamserios.basilread.models.OperationsUser;
import za.co.thamserios.basilread.resteasy.ISpringRest;
import za.co.thamserios.basilread.resteasy.RestEasy;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by robson on 2017/03/06.
 */

public class UserService extends PostMan {

    public UserService(){
    }

    /*public void getAllUsers() {
        IBasilServices client = ApiClient.getInstance().getApiClient();
        Call<List<OperationsUser>> users = client.listAllUsers();
        users.enqueue(new Callback<List<OperationsUser>>() {
            @Override
            public void onResponse(Call<List<OperationsUser>> call, Response<List<OperationsUser>> response) {
                if (response.isSuccessful()) {
                    List<OperationsUser> result = response.body();
                    replace(result, OperationsUser.class);
                }
            }

            @Override
            public void onFailure(Call<List<OperationsUser>> call, Throwable t) {
                Log.d("UserService",t.getMessage());
            }
        });
    }*/

      public void getAllUsers(){
        ISpringRest rest = new RestEasy();
        try {
            OperationsUser[] users = rest.GET("site/users/all",OperationsUser[].class);
            if (users != null && users.length > 0)
                replace(Arrays.asList(users),OperationsUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OperationsUser> getAll(){
        return findAll(OperationsUser.class);
    }

    public List<OperationsUser> getOperators(){
        String criteria = String.format("roleId=%d", AppConstants.OPERATOR);
        return findSpecific(criteria,OperationsUser.class);
    }

    public List<OperationsUser> getAssistants(){
        String criteria = String.format("roleId=%d", AppConstants.ASSISTANT);
        return findSpecific(criteria,OperationsUser.class);
    }

    public List<OperationsUser> getMechanics(){
        String criteria = String.format("roleId=%d", AppConstants.MECHANIC);
        return findSpecific(criteria,OperationsUser.class);
    }

    public List<OperationsUser> getForeman(){
        String criteria = String.format("roleId=%d", AppConstants.FOREMAN);
        return findSpecific(criteria,OperationsUser.class);
    }

    public List<OperationsUser> getSupervisor(){
        String criteria = String.format("roleId=%d", AppConstants.SUPERVISOR);
        return findSpecific(criteria,OperationsUser.class);
    }

    @Override
    public void postToServer() {

    }
}
