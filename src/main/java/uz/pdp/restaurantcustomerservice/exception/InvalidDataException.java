package uz.pdp.restaurantcustomerservice.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String msg) {
        super("Invalid" + msg);
    }
}
