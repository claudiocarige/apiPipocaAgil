package br.com.pipocaagil.apipipocaagil.repositories;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);

    @Query("SELECT u.role FROM Users u WHERE u.username like :username")
    UserPermissionType findRoleByUsername(@Param("username") String username);
    @Query("SELECT user FROM Users user WHERE LOWER(user.firstName) = LOWER(:firstName)")
    List<Users> findByNameIgnoreCase(String firstName);

    List<Users> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstNamePart, String lastNamePart);

    @Query("SELECT u FROM Users u WHERE u.birthday >= :startDate AND u.birthday <= :endDate")
    List<Users> findByBirthdayBetween(@Param("startDate") LocalDate startDate, @Param ("endDate") LocalDate endDate);

    @Modifying
    @Query("UPDATE Users u SET u.role = :role  WHERE u.id = :userId")
    void updateRoleToSigned(@Param("role") UserPermissionType role , @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Users u SET u.password = :password  WHERE u.username = :email")
    void resetPassword(@Param("password") String password, @Param("email")  String email);
}
