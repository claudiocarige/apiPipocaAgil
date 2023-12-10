package br.com.pipocaagil.apipipocaagil.repositories;

import br.com.pipocaagil.apipipocaagil.domain.entities.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SignatureDataRepository extends JpaRepository<SignatureData, Long> {

    @Query("SELECT COUNT(s) FROM SignatureData s")
    Long countUsersSignature();

    @Query("SELECT u FROM Users u JOIN SignatureData s ON u.id = s.user.id")
    List<Users> findUsersWithSignature();

    @Query("SELECT s FROM SignatureData s WHERE s.user.id = :userId")
    SignatureData findSignatureByUserId(@Param("userId") Long userId);
}
