package za.co.thamserios.basilread.framework.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import za.co.thamserios.basilread.models.BreakdownLogs;
import za.co.thamserios.basilread.models.BreakdownTypes;
import za.co.thamserios.basilread.models.Crews;
import za.co.thamserios.basilread.models.EndOfShift;
import za.co.thamserios.basilread.models.OperationsUser;
import za.co.thamserios.basilread.models.StartOfShift;
import za.co.thamserios.basilread.models.ProductionRecord;
import za.co.thamserios.basilread.models.ProductionType;
import za.co.thamserios.basilread.models.ResponseMessage;
import za.co.thamserios.basilread.models.Shift;
import za.co.thamserios.basilread.models.SiteConfig;
import za.co.thamserios.basilread.models.StandingLogs;
import za.co.thamserios.basilread.models.StandingTypes;

/**
 * Created by robson on 2016/12/26.
 */

public interface IBasilServices {

    /* operations */
    @GET("site/users/all")
    Call<List<OperationsUser>> listAllUsers();

    /* look ups */
    @GET("shifts")
    Call<List<Shift>> listAllShifts();

    @GET("crews/all")
    Call<List<Crews>> listAllCrews();

    @GET("lookup/productiontypes")
    Call<List<ProductionType>> listAllProductionTypes();

    @GET("lookup/standingtypes")
    Call<List<StandingTypes>> listAllStandingTimeTypes();

    @GET("lookup/breakdowntypes")
    Call<List<BreakdownTypes>> listAllBreakdownTypes();

    @GET("config/rigs")
    Call<List<SiteConfig>> listAllRigs();

    /* posts */
    @POST("breakdown")
    Call<ResponseMessage> createBreakdown(@Body BreakdownLogs model);

    @POST("standingtime")
    Call<ResponseMessage> createStandingTime(@Body StandingLogs model);

    @POST("production")
    Call<ResponseMessage> createProduction(@Body ProductionRecord model);

    @POST("startofshift")
    Call<ResponseMessage> createStartOfShift(@Body StartOfShift model);

    @POST("endofshift")
    Call<ResponseMessage> createEndOfShift(@Body EndOfShift model);
}

