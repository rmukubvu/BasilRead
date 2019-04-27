package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2016/12/27.
 */

public class ResponseMessage {
    String responseMessage;

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    Boolean isError;
    @Override
    public String toString() {
        return(responseMessage);
    }
}
