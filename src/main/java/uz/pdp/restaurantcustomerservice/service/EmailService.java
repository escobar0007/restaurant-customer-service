package uz.pdp.restaurantcustomerservice.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmailVerificationMessage(String username, String email);
}
