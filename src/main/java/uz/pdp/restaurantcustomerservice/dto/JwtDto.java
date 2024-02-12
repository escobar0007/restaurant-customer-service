package uz.pdp.restaurantcustomerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class JwtDto{

    private String accessToken;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
