package uz.pdp.restaurantcustomerservice.security.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import uz.pdp.restaurantcustomerservice.security.oauth.enums.AuthType;

@Component
public class OAuth2GithubPrinciple{

    private final OAuth2User oAuth2User;

    public OAuth2GithubPrinciple(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public String getProviderClientRegisterId() {
        return AuthType.GITHUB.getValue();
    }

    public String getUsername() {
        return oAuth2User.getAttribute("login");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
}
