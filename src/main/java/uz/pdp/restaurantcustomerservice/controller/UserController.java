package uz.pdp.restaurantcustomerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.restaurantcustomerservice.dto.UserUpdateDto;
import uz.pdp.restaurantcustomerservice.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(userServiceImpl.getAll());
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userServiceImpl.update(userUpdateDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userServiceImpl.getById(id));
    }
}
