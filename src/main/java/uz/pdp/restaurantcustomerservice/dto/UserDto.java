package uz.pdp.restaurantcustomerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.restaurantcustomerservice.entity.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    String phoneNumber;
    List<NestedPermissionDto> permissions;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.permissions = user.getPermissions().stream()
                .map(permission -> new NestedPermissionDto(permission.getValue()))
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class NestedPermissionDto implements Serializable {
        private String value;
    }
}