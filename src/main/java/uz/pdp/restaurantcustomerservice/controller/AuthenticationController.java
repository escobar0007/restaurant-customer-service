package uz.pdp.restaurantcustomerservice.controller;

import com.nimbusds.oauth2.sdk.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restaurantcustomerservice.dto.CustomerLoginDto;
import uz.pdp.restaurantcustomerservice.dto.CustomerRegisterDto;
import uz.pdp.restaurantcustomerservice.service.CustomerService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerRegisterDto customerRegisterDto) {
        return ResponseEntity.ok(customerService.register(customerRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginDto customerLoginDto) {
        return ResponseEntity.ok(customerService.login(customerLoginDto));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) {
        if (customerService.verify(token))
            return ResponseEntity.ok("Successfully verified");
        return ResponseEntity.badRequest().body("Error");
    }
}
