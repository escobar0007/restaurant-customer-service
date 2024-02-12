package uz.pdp.restaurantcustomerservice.security.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import uz.pdp.restaurantcustomerservice.security.oauth.enums.AuthType;

@Component
public class OAuth2GithubPrinciple implements OAuth2UserPrinciple{

    private final OAuth2User oAuth2User;

    public OAuth2GithubPrinciple(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public String getProviderClientRegisterId() {
        return AuthType.GITHUB.getValue();
    }

    @Override
    public String getUsername() {
        return oAuth2User.getAttribute("login");
    }

    @Override
    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
}
