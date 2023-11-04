package br.com.pipocaagil.apipipocaagil.repositories;

import br.com.pipocaagil.apipipocaagil.domain.SignatureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SignatureDataRepository extends JpaRepository<SignatureData, Long> {

    @Query("SELECT COUNT(s) FROM SignatureData s")
    Long countUsers();
}
