package uz.pdp.restaurantcustomerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.restaurantcustomerservice.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}