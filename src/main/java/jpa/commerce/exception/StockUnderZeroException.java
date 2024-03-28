package jpa.commerce.exception;

public class StockUnderZeroException extends RuntimeException {

    public StockUnderZeroException() {

    }

    public StockUnderZeroException(String message) {
        super(message);
    }

    public StockUnderZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockUnderZeroException(Throwable cause) {
        super(cause);
    }





}
