package uz.pdp.restaurantcustomerservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.restaurantcustomerservice.dto.AdminCreateDto;
import uz.pdp.restaurantcustomerservice.dto.AdminUpdateDto;
import uz.pdp.restaurantcustomerservice.dto.UserDto;
import uz.pdp.restaurantcustomerservice.entity.Permission;
import uz.pdp.restaurantcustomerservice.entity.User;
import uz.pdp.restaurantcustomerservice.exception.AlreadyExistException;
import uz.pdp.restaurantcustomerservice.exception.NotFoundException;
import uz.pdp.restaurantcustomerservice.repository.PermissionRepository;
import uz.pdp.restaurantcustomerservice.repository.UserRepository;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenProvider;
import uz.pdp.restaurantcustomerservice.service.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    @Override
    public UserDto create(AdminCreateDto adminCreateDto) {
        User user = userRepository.findById(adminCreateDto.getUserId()).orElseThrow(
                () -> new NotFoundException("User")
        );
        for (Permission permission : user.getPermissions()) {
            if (Objects.equals(permission.getValue(), "PER_ADMIN")) {
                throw new AlreadyExistException("Admin");
            }
        }
        user.setPermissions(List.of(permissionRepository.findByValue("PER_ADMIN").get()));
        return new UserDto(userRepository.save(user));
    }


    @Override
    public List<UserDto> getAll() {
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            for (Permission permission : user.getPermissions()) {
                if (Objects.equals(permission.getValue(), "PER_ADMIN")) {
                    users.add(user);
                }
            }
        }
        return users.stream().map(UserDto::new).toList();
    }

    @Override
    public UserDto getById(Long id) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User")
        );
        for (Permission permission : user.getPermissions()) {
            if (Objects.equals(permission.getValue(), "PER_ADMIN")) {
                return new UserDto(user);
            }
        }
        return null;
    }

    @Override
    public UserDto update(AdminUpdateDto adminUpdateDto) {
        return null;
    }
}
