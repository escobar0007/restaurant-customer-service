package uz.pdp.restaurantcustomerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.restaurantcustomerservice.entity.Permission;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateDto {
    private String adminName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Permission> permissions;
}
