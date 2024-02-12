package uz.pdp.restaurantcustomerservice.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String msg) {
        super(msg + " already exist.");
    }
}
