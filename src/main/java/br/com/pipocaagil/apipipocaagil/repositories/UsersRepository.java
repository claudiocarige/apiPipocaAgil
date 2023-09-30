package br.com.pipocaagil.apipipocaagil.repositories;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
}
