package br.com.pipocaagil.apipipocaagil.services.interfaces;

import br.com.pipocaagil.apipipocaagil.domain.SignatureData;

import java.util.List;
import java.util.Optional;

public interface SignatureDataService {

    SignatureData save(SignatureData signatureData);
    Optional<SignatureData> findById(Long id);
    List<SignatureData> findAll();
}
