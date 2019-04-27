package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/03/25.
 */

public class CryptoException extends Exception {
    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}