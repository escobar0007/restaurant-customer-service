package uz.pdp.restaurantcustomerservice.security.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import uz.pdp.restaurantcustomerservice.security.oauth.enums.AuthType;

@Component
public class OAuth2GooglePrinciple implements OAuth2UserPrinciple{

    private final OAuth2User oAuth2User;

    public OAuth2GooglePrinciple(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public String getProviderClientRegisterId() {
        return AuthType.GOOGLE.getValue();
    }

    @Override
    public String getUsername() {
        return oAuth2User.getAttribute("full_name");
    }

    @Override
    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
}
