package uz.pdp.restaurantcustomerservice.security.oauth.enums;

import lombok.Getter;

@Getter
public enum AuthType {
    GOOGLE("google"),
    GITHUB("github");

    private final String value;

    AuthType(String value) {
        this.value = value;
    }
}
