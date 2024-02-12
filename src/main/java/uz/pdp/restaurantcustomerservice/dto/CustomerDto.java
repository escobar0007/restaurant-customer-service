package uz.pdp.restaurantcustomerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.restaurantcustomerservice.entity.Customer;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    String firstName;
    String lastName;
    String username;
    String email;
    String phoneNumber;

    public CustomerDto(Customer customer) {
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
    }
}