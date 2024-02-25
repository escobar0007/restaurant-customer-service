package uz.pdp.restaurantcustomerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;

}
