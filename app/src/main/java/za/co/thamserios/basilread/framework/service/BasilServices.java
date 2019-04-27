package za.co.thamserios.basilread.framework.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by robson on 2016/12/26.
 */

public class BasilServices {

    //private static final String BASE_URL = "http://10.0.2.2:4567/";
    public static final String BASE_URL = "http://marblevolume.dedicated.co.za/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(logging);  // <-- this is the important line!*/
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    //.addConverterFactory(GsonConverterFactory.create())
                    //.client(httpClient)
                    .build();
        }
        return retrofit;
    }
}


