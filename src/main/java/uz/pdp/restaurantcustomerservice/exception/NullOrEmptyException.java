package uz.pdp.restaurantcustomerservice.exception;

public class NullOrEmptyException extends RuntimeException {
    public NullOrEmptyException(String msg) {
        super(msg + " is null or empty");
    }
}
