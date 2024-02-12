package uz.pdp.restaurantcustomerservice.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.restaurantcustomerservice.dto.*;
import uz.pdp.restaurantcustomerservice.entity.Customer;
import uz.pdp.restaurantcustomerservice.exception.AlreadyExistException;
import uz.pdp.restaurantcustomerservice.exception.InvalidDataException;
import uz.pdp.restaurantcustomerservice.exception.NotFoundException;
import uz.pdp.restaurantcustomerservice.exception.NullOrEmptyException;
import uz.pdp.restaurantcustomerservice.exception.OAuth2Exception;
import uz.pdp.restaurantcustomerservice.repository.CustomerRepository;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenProvider;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    public String register(CustomerRegisterDto customerRegisterDto) {
        if (customerRegisterDto == null)
            throw new NullOrEmptyException("UserRegisterDto");
        if (customerRegisterDto.getFirstName() == null)
            throw new NullOrEmptyException("First Name");
        if (customerRegisterDto.getLastName() == null)
            throw new NullOrEmptyException("Last Name");
        if (customerRegisterDto.getUsername() == null)
            throw new NullOrEmptyException("Username");
        if (customerRegisterDto.getPassword() == null)
            throw new NullOrEmptyException("Password");
        if (customerRepository.findByUsername(customerRegisterDto.getUsername()).isPresent())
            throw new AlreadyExistException("Username");
        if (customerRegisterDto.getEmail() != null) {
            if (customerRegisterDto.getEmail().isEmpty())
                throw new NullOrEmptyException("Email");
            if (customerRepository.findByEmail(customerRegisterDto.getEmail()).isPresent())
                throw new AlreadyExistException("Email");
            emailService.sendEmailVerificationMessage(customerRegisterDto.getUsername(), customerRegisterDto.getEmail());
        }
        if (customerRegisterDto.getPhoneNumber() != null) {
            if (customerRegisterDto.getPhoneNumber().isEmpty())
                throw new NullOrEmptyException("Phone number");
            if (customerRepository.findByPhoneNumber(customerRegisterDto.getPhoneNumber()).isPresent())
                throw new AlreadyExistException("Phone number");
        }

        Customer customer = Customer.builder()
                .firstName(customerRegisterDto.getFirstName())
                .lastName(customerRegisterDto.getLastName())
                .username(customerRegisterDto.getUsername())
                .phoneNumber(customerRegisterDto.getPhoneNumber())
                .password(passwordEncoder.encode(customerRegisterDto.getPassword()))
                .cardNumber(null)
                .build();

        customerRepository.save(customer);

        return "Successfully registered, Please login now";
    }

    public JwtDto login(CustomerLoginDto customerLoginDto) {
        Customer customer;
        if(customerLoginDto.getUsername() != null && customerLoginDto.getEmail() == null) {
            customer = customerRepository.findByUsername(customerLoginDto.getUsername()).orElseThrow(
                    () -> new NotFoundException("Customer")
            );
            if (customer.getProviderRegisterClientId() != null)
                throw new OAuth2Exception();
        } else if (customerLoginDto.getUsername() == null && customerLoginDto.getEmail() != null) {
            customer = customerRepository.findByEmail(customerLoginDto.getEmail()).orElseThrow(
                    () -> new NotFoundException("Customer")
            );
            if (customer.getProviderRegisterClientId() != null)
                throw new OAuth2Exception();
        } else {
            customer = customerRepository.findByUsername(customerLoginDto.getUsername()).orElseThrow(
                    () -> new NotFoundException("Customer"));
            if (customer.getProviderRegisterClientId() != null)
                throw new OAuth2Exception();
        }
        if (passwordEncoder.matches(customerLoginDto.getPassword(), customer.getPassword())) {
            return new JwtDto(jwtTokenProvider.generateToken(customer));
        }
        throw new InvalidDataException("Password");
    }


    public CustomerDto update(CustomerUpdateDto customerUpdateDto) {
        if (customerUpdateDto == null)
            throw new NullOrEmptyException("CustomerUpdate");
        if (customerUpdateDto.getId() == null)
            throw new NullOrEmptyException("Id");
        if (customerUpdateDto.getUsername() != null && customerRepository.findByUsername(customerUpdateDto.getUsername()).isPresent())
            throw new AlreadyExistException("Username");
        if (customerUpdateDto.getEmail() != null && customerRepository.findByEmail(customerUpdateDto.getEmail()).isPresent())
            throw new AlreadyExistException("Email");
        if (customerUpdateDto.getPhoneNumber() != null && customerRepository.findByPhoneNumber(customerUpdateDto.getPhoneNumber()).isPresent())
            throw new AlreadyExistException("Phone number");

        Customer customer = customerRepository.findById(customerUpdateDto.getId()).orElseThrow(
                () -> new NotFoundException("Customer")
        );

        if (customerUpdateDto.getEmail() != null)
            emailService.sendEmailVerificationMessage(customerUpdateDto.getUsername(), customerUpdateDto.getEmail());
        return new CustomerDto(customerRepository.save(Customer.builder()
                .firstName(Objects.requireNonNullElse(customerUpdateDto.getFirstName(), customer.getFirstName()))
                .lastName(Objects.requireNonNullElse(customerUpdateDto.getLastName(), customer.getLastName()))
                .username(Objects.requireNonNullElse(customerUpdateDto.getUsername(), customer.getUsername()))
                .email(Objects.requireNonNullElse(customerUpdateDto.getEmail(), customer.getEmail()))
                .password(Objects.requireNonNullElse(customerUpdateDto.getPassword() == null ? customer.getPassword() : passwordEncoder.encode(customerUpdateDto.getPassword()), customer.getPassword()))
                .phoneNumber(Objects.requireNonNullElse(customerUpdateDto.getPhoneNumber(), customer.getPhoneNumber()))
                .build()));
    }

    public CustomerDto getById(Long id) {
        if (id == null) {
            throw new NullOrEmptyException("Id");
        }
        return new CustomerDto(customerRepository.findById(id).get());
    }

    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream().map(CustomerDto::new).toList();
    }

    public boolean verify(String token) {
        if (token == null || token.isEmpty())
            return false;
        Claims claims = jwtTokenProvider.parseAllClaims(token);
        if (jwtTokenProvider.isValid(token)) {
            String email = claims.getSubject();
            String username = claims.get("username", String.class);
            if (customerRepository.findByUsername(username).isPresent()) {
                Customer customer = customerRepository.findByUsername(username).get();
                customer.setEmail(email);
                customerRepository.save(customer);
                return true;
            }
        }
        return false;
    }
}
