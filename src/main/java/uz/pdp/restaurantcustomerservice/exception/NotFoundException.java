package uz.pdp.restaurantcustomerservice.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg + " not found.");
    }
}
