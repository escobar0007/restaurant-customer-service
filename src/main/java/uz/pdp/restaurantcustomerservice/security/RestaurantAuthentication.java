package uz.pdp.restaurantcustomerservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RestaurantAuthentication {
    private Object principal;
    private Object credentials;
    private List<String> permissions;
}
