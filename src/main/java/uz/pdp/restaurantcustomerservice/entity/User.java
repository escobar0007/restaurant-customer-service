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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerRegisterClientId;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String password;

    @Column(unique = true)
    private String cardNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Permission> permissions;
}