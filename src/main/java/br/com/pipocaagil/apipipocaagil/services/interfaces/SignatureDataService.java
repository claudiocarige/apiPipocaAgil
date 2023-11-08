package br.com.pipocaagil.apipipocaagil.services.interfaces;

import br.com.pipocaagil.apipipocaagil.domain.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.Users;

import java.util.List;

public interface SignatureDataService {

    void save(SignatureData signatureData);
    List<Users> findUsersWithSignature();

    SignatureData findSignatureByUserId(Long userId);

    Long countUsersSignature();
}
