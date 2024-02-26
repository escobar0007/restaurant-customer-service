package uz.pdp.restaurantcustomerservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenFilter;
import uz.pdp.restaurantcustomerservice.security.oauth.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {

    private final JwtTokenFilter jwtTokenFilter;
    private final OAuth2AuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(registry ->
                registry.requestMatchers("/api/v1/auth/**", "/login/oauth2/**", "/verify","/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
        )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(oauth ->
//                        oauth.successHandler(successHandler)
//                        );
        return http.build();
    }
}
