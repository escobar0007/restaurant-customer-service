package uz.pdp.restaurantcustomerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.restaurantcustomerservice.dto.UserLoginDto;
import uz.pdp.restaurantcustomerservice.dto.UserRegisterDto;
import uz.pdp.restaurantcustomerservice.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(userService.register(userRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(userService.login(userLoginDto));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) {
        if (userService.verify(token))
            return ResponseEntity.ok("Successfully verified");
        return ResponseEntity.badRequest().body("Error");
    }
}
