package za.co.thamserios.basilread.resteasy;

import org.springframework.http.HttpEntity;


/**
 * Created by robson on 2017/03/11.
 */

public interface ISpringRest {
   <T> String POST(String url, HttpEntity<T> requestEntity) throws  Exception;
   <T> T GET(String url, Class<T> class1) throws  Exception;
}
