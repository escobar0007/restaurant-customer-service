package uz.pdp.restaurantcustomerservice.exception;

public class OAuth2Exception extends RuntimeException {
    public OAuth2Exception() {
        super("This user registered with OAuth2 authentication");
    }
}
