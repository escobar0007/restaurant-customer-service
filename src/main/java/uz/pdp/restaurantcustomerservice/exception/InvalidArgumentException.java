package uz.pdp.restaurantcustomerservice.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String msg) {
        super(msg + " is invalid");
    }
}
