package uz.pdp.restaurantcustomerservice.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.restaurantcustomerservice.dto.*;
import uz.pdp.restaurantcustomerservice.entity.User;
import uz.pdp.restaurantcustomerservice.exception.AlreadyExistException;
import uz.pdp.restaurantcustomerservice.exception.InvalidDataException;
import uz.pdp.restaurantcustomerservice.exception.NotFoundException;
import uz.pdp.restaurantcustomerservice.exception.NullOrEmptyException;
import uz.pdp.restaurantcustomerservice.exception.OAuth2Exception;
import uz.pdp.restaurantcustomerservice.repository.UserRepository;
import uz.pdp.restaurantcustomerservice.repository.PermissionRepository;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenProvider;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PermissionRepository permissionRepository;
    public String register(UserRegisterDto userRegisterDto) {
        if (userRegisterDto == null)
            throw new NullOrEmptyException("UserRegisterDto");
        if (userRegisterDto.getFirstName() == null)
            throw new NullOrEmptyException("First Name");
        if (userRegisterDto.getLastName() == null)
            throw new NullOrEmptyException("Last Name");
        if (userRegisterDto.getUsername() == null)
            throw new NullOrEmptyException("Username");
        if (userRegisterDto.getPassword() == null)
            throw new NullOrEmptyException("Password");
        if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent())
            throw new AlreadyExistException("Username");
        if (userRegisterDto.getEmail() != null) {
            if (userRegisterDto.getEmail().isEmpty())
                throw new NullOrEmptyException("Email");
            if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent())
                throw new AlreadyExistException("Email");
            emailService.sendEmailVerificationMessage(userRegisterDto.getUsername(), userRegisterDto.getEmail());
        }
        if (userRegisterDto.getPhoneNumber() != null) {
            if (userRegisterDto.getPhoneNumber().isEmpty())
                throw new NullOrEmptyException("Phone number");
            if (userRepository.findByPhoneNumber(userRegisterDto.getPhoneNumber()).isPresent())
                throw new AlreadyExistException("Phone number");
        }

        User user = User.builder()
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .username(userRegisterDto.getUsername())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .cardNumber(null)
                .permissions(List.of(permissionRepository.findByValue("PER_CUSTOMER").get()))
                .build();

        userRepository.save(user);

        return "Successfully registered, Please login now";
    }

    public JwtDto login(UserLoginDto userLoginDto) {
        User user;
        if(userLoginDto.getUsername() != null && userLoginDto.getEmail() == null) {
            user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(
                    () -> new NotFoundException("User")
            );
            if (user.getProviderRegisterClientId() != null)
                throw new OAuth2Exception();
        } else if (userLoginDto.getUsername() == null && userLoginDto.getEmail() != null) {
            user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(
                    () -> new NotFoundException("User")
            );
            if (user.getProviderRegisterClientId() != null)
                throw new OAuth2Exception();
        } else {
            user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(
                    () -> new NotFoundException("User"));
            if (user.getProviderRegisterClientId() != null)
                throw new OAuth2Exception();
        }
        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return new JwtDto(jwtTokenProvider.generateToken(user));
        }
        throw new InvalidDataException("Password");
    }


    public UserDto update(UserUpdateDto userUpdateDto) {
        if (userUpdateDto == null)
            throw new NullOrEmptyException("CustomerUpdate");
        if (userUpdateDto.getId() == null)
            throw new NullOrEmptyException("Id");
        if (userUpdateDto.getUsername() != null && userRepository.findByUsername(userUpdateDto.getUsername()).isPresent())
            throw new AlreadyExistException("Username");
        if (userUpdateDto.getEmail() != null && userRepository.findByEmail(userUpdateDto.getEmail()).isPresent())
            throw new AlreadyExistException("Email");
        if (userUpdateDto.getPhoneNumber() != null && userRepository.findByPhoneNumber(userUpdateDto.getPhoneNumber()).isPresent())
            throw new AlreadyExistException("Phone number");

        User user = userRepository.findById(userUpdateDto.getId()).orElseThrow(
                () -> new NotFoundException("User")
        );

        if (userUpdateDto.getEmail() != null)
            emailService.sendEmailVerificationMessage(userUpdateDto.getUsername(), userUpdateDto.getEmail());
        return new UserDto(userRepository.save(User.builder()
                .firstName(Objects.requireNonNullElse(userUpdateDto.getFirstName(), user.getFirstName()))
                .lastName(Objects.requireNonNullElse(userUpdateDto.getLastName(), user.getLastName()))
                .username(Objects.requireNonNullElse(userUpdateDto.getUsername(), user.getUsername()))
                .email(Objects.requireNonNullElse(userUpdateDto.getEmail(), user.getEmail()))
                .password(Objects.requireNonNullElse(userUpdateDto.getPassword() == null ? user.getPassword() : passwordEncoder.encode(userUpdateDto.getPassword()), user.getPassword()))
                .phoneNumber(Objects.requireNonNullElse(userUpdateDto.getPhoneNumber(), user.getPhoneNumber()))
                .build()));
    }

    public UserDto getById(Long id) {
        if (id == null) {
            throw new NullOrEmptyException("Id");
        }
        return new UserDto(userRepository.findById(id).get());
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }

    public boolean verify(String token) {
        if (token == null || token.isEmpty())
            return false;
        Claims claims = jwtTokenProvider.parseAllClaims(token);
        if (jwtTokenProvider.isValid(token)) {
            String email = claims.getSubject();
            String username = claims.get("username", String.class);
            if (userRepository.findByUsername(username).isPresent()) {
                User user = userRepository.findByUsername(username).get();
                user.setEmail(email);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
