package za.co.thamserios.basilread.helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by robson on 2017/03/11.
 */

public class HttpHelper {
    public static <T> HttpEntity<T> getHttpEntity(T data){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application","json"));
        HttpEntity<T> requestEntity = new HttpEntity<>(data, requestHeaders);
        return requestEntity;
    }
}
