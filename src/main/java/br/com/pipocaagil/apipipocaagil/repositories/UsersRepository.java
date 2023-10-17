package br.com.pipocaagil.apipipocaagil.repositories;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);

    @Query("SELECT user FROM Users user WHERE LOWER(user.firstName) = LOWER(:firstName)")
    List<Users> findByNameIgnoreCase(String firstName);

    List<Users> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstNamePart, String lastNamePart);

}
