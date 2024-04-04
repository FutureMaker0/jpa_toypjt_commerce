package jpa.commerce.exception;

public class StockUnderZeroException extends RuntimeException {

    public StockUnderZeroException() {
    }

    public StockUnderZeroException(String message) {
        super(message);
    }

    public StockUnderZeroException(Throwable cause) {
        super(cause);
    }

    public StockUnderZeroException(String message, Throwable cause) {
        super(message, cause);
    }
}
