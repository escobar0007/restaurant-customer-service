package uz.pdp.restaurantcustomerservice.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.restaurantcustomerservice.dto.CustomResponseEntity;
import uz.pdp.restaurantcustomerservice.entity.Permission;
import uz.pdp.restaurantcustomerservice.entity.User;
import uz.pdp.restaurantcustomerservice.repository.UserRepository;
import uz.pdp.restaurantcustomerservice.security.RestaurantAuthentication;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenProvider;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/verify")
    public CustomResponseEntity<?> verify(@RequestParam String token) {
        if (token != null) {
            System.out.println(token);
            if (jwtTokenProvider.isValid(token)) {
                Claims claims = jwtTokenProvider.parseAllClaims(token);
                Optional<User> user = userRepository.findByUsername(claims.getSubject());
                if (user.isPresent()) {
                    User user1 = user.get();
                    return CustomResponseEntity.ok(new RestaurantAuthentication(user1.getUsername(),
                            null,
                            user1.getPermissions().stream().map(Permission::getValue).toList()));
                }
            }
        }
        return CustomResponseEntity.ok(null);
    }
}
