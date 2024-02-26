package uz.pdp.restaurantcustomerservice.service;

import org.springframework.stereotype.Service;
import uz.pdp.restaurantcustomerservice.dto.*;

@Service
public interface UserService {
    String register(UserRegisterDto userRegisterDto);
    JwtDto login(UserLoginDto userLoginDto);

    UserDto update(UserUpdateDto userUpdateDto);
    UserDto getById(Long id);
    boolean verify(String token);
}
