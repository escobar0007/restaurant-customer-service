package uz.pdp.restaurantcustomerservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.restaurantcustomerservice.entity.Customer;
import uz.pdp.restaurantcustomerservice.repository.CustomerRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@NonNullApi
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository customerRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION);
        if (token != null && token.startsWith(BEARER)) {
            token = token.split(" ")[1];
            if (jwtTokenProvider.isValid(token)) {
                Claims claims = jwtTokenProvider.parseAllClaims(token);
                Optional<Customer> customer = customerRepository.findByUsername(claims.getSubject());
                if (customer.isPresent()) {
                    List<SimpleGrantedAuthority> grantedAuthority = customer.get().getPermissions().stream().map(r -> new SimpleGrantedAuthority(r.getValue())).toList();
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            grantedAuthority
                    ));
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
