package uz.pdp.restaurantcustomerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerRegisterClientId;

    private String firstName;

    private String lastName;

    @Pattern(regexp = "^[^\\s@.,#$%^&*() +!~]{5,14}$", message = "Invalid Username")
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email")
    private String email;

    @Column(unique = true)
    @Pattern(regexp = "^\\+998\\d{2}\\d{3}\\d{2}\\d{2}$", message = "Invalid phone number")
    private String phoneNumber;

//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$", message = "Invalid password")
    private String password;

    @Column(unique = true)
    private String cardNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;
}