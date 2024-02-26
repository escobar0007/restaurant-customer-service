package uz.pdp.restaurantcustomerservice.service;

import org.springframework.stereotype.Service;
import uz.pdp.restaurantcustomerservice.dto.AdminCreateDto;
import uz.pdp.restaurantcustomerservice.dto.AdminUpdateDto;
import uz.pdp.restaurantcustomerservice.dto.UserDto;

import java.util.List;

@Service
public interface AdminService {
    UserDto create(AdminCreateDto adminCreateDto);
    List<UserDto> getAll();

    UserDto getById(Long id);

    UserDto update(AdminUpdateDto adminUpdateDto);
}
