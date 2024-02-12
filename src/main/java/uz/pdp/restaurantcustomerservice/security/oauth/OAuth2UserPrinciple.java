package uz.pdp.restaurantcustomerservice.security.oauth;

import org.springframework.stereotype.Component;

@Component
public interface OAuth2UserPrinciple {
    String getProviderClientRegisterId();
    String getUsername();
    String getEmail();
}
