package uz.pdp.restaurantcustomerservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDto {
    private String firstName;
    private String lastName;

    @Pattern(regexp = "^[^\\s@.,#$%^&*() +!~]{5,14}$", message = "Invalid Username")
    private String username;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email")
    private String email;

    @Pattern(regexp = "^\\+998\\d{2}\\d{3}\\d{2}\\d{2}$", message = "Invalid phone number")
    private String phoneNumber;

//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*]{8,}$", message = "Invalid password")
    private String password;
}
