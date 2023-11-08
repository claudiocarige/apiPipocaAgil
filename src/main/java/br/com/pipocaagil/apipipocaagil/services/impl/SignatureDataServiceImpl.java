package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.repositories.SignatureDataRepository;
import br.com.pipocaagil.apipipocaagil.services.exceptions.NoSuchElementException;
import br.com.pipocaagil.apipipocaagil.services.interfaces.SignatureDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<SignatureData> signatureData = signatureDataRepository.findSignatureByUserId(id);
        return signatureData.orElseThrow(() -> new NoSuchElementException("Signature not found!"));
    }

    @Override
    public List<Users> findUsersWithSignature() {
        return signatureDataRepository.findUsersWithSignature();
    }

    public Long countUsersSignature() {
        return signatureDataRepository.countUsersSignature();
    }
}
