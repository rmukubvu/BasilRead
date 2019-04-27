package za.co.thamserios.basilread.resteasy;

import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by robson on 2017/03/11.
 */

public class RestEasy implements ISpringRest {

    private static final String TAG = RestEasy.class.getSimpleName();
    private RestTemplate restTemplate;
    //private static final String BASE_URL = "10.0.0.2:4567/";
    private static final String BASE_URL = "http://104.214.26.198:4567/";
     //public static final String BASE_URL = "http://marblevolume.dedicated.co.za/";

    @Override
    public <T> String POST(String url, HttpEntity<T> requestEntity) throws Exception {
        // Create a new RestTemplate instance
        restTemplate = new RestTemplate();
        String fullPath = BASE_URL + url;
        // Add the Jackson and String message converters
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
        ResponseEntity<String> responseEntity = restTemplate.exchange(fullPath, HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public <T> T GET(String url, Class<T> class1) throws Exception {
        try {
            String fullPath = BASE_URL + url;
            restTemplate = new RestTemplate();
            Log.d("RESTEASY", restTemplate.getClass().getSimpleName());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(fullPath, class1);
        } catch (Exception e) {
            throw e;
        }
    }
}
