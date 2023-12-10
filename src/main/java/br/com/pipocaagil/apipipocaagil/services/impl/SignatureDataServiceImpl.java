package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.entities.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import br.com.pipocaagil.apipipocaagil.repositories.SignatureDataRepository;
import br.com.pipocaagil.apipipocaagil.services.interfaces.SignatureDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignatureDataServiceImpl implements SignatureDataService {

    private final SignatureDataRepository signatureDataRepository;

    @Transactional
    public void save(SignatureData signatureData) {
        signatureDataRepository.save(signatureData);
        log.info("SignatureData saved.");
    }

    @Override
    public SignatureData findSignatureByUserId(Long id) {
        return signatureDataRepository.findSignatureByUserId(id);
    }

    @Override
    public List<Users> findUsersWithSignature() {
        return signatureDataRepository.findUsersWithSignature();
    }

    public Long countUsersSignature() {
        return signatureDataRepository.countUsersSignature();
    }
}
