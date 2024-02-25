package uz.pdp.restaurantcustomerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.restaurantcustomerservice.dto.UserUpdateDto;
import uz.pdp.restaurantcustomerservice.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class CustomerController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.update(userUpdateDto));
    }
}
