package uz.pdp.restaurantcustomerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.restaurantcustomerservice.dto.AdminCreateDto;
import uz.pdp.restaurantcustomerservice.dto.AdminUpdateDto;
import uz.pdp.restaurantcustomerservice.service.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('PER_OWNER','PER_CREATE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody AdminCreateDto adminCreateDto) {
        return ResponseEntity.ok(adminService.create(adminCreateDto));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adminService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AdminUpdateDto adminUpdateDto){
        return ResponseEntity.ok(adminService.update(adminUpdateDto));
    }
}
