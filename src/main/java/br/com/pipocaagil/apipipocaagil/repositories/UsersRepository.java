package br.com.pipocaagil.apipipocaagil.repositories;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
}
