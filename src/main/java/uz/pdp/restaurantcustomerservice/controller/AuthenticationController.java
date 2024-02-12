package uz.pdp.restaurantcustomerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
