package br.com.pipocaagil.apipipocaagil.services.interfaces;

import br.com.pipocaagil.apipipocaagil.domain.entities.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.entities.Users;

import java.util.List;

public interface SignatureDataService {

    void save(SignatureData signatureData);
    List<Users> findUsersWithSignature();

    SignatureData findSignatureByUserId(Long userId);

    Long countUsersSignature();
}
