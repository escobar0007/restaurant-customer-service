package uz.pdp.restaurantcustomerservice.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.pdp.restaurantcustomerservice.dto.JwtDto;
import uz.pdp.restaurantcustomerservice.entity.Customer;
import uz.pdp.restaurantcustomerservice.exception.AlreadyExistException;
import uz.pdp.restaurantcustomerservice.repository.CustomerRepository;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;
    private final OAuth2GithubPrinciple userGitHubPrinciple;
    private final OAuth2GooglePrinciple userGooglePrinciple;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof OAuth2AuthenticationToken token) {
            if (token.getAuthorizedClientRegistrationId().equals(userGitHubPrinciple.getProviderClientRegisterId())) {
                if(customerRepository.findByUsername(userGitHubPrinciple.getUsername()).isPresent())
                    throw new AlreadyExistException("Customer");
                Customer customer = customerRepository.save(Customer.builder()
                        .providerRegisterClientId(userGitHubPrinciple.getProviderClientRegisterId())
                        .username(userGitHubPrinciple.getUsername())
                        .email(userGitHubPrinciple.getEmail())
                        .build());

                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(new JwtDto(jwtTokenProvider.generateToken(customer))));
            } else if (token.getAuthorizedClientRegistrationId().equals(userGitHubPrinciple.getProviderClientRegisterId())) {
                if (customerRepository.findByUsername(userGooglePrinciple.getUsername()).isPresent())
                    throw new AlreadyExistException("Customer");
                Customer customer = customerRepository.save(Customer.builder()
                        .providerRegisterClientId(userGitHubPrinciple.getProviderClientRegisterId())
                        .username(userGitHubPrinciple.getUsername())
                        .email(userGitHubPrinciple.getEmail())
                        .build());
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(new JwtDto(jwtTokenProvider.generateToken(customer))));
            }
        }
    }
}
