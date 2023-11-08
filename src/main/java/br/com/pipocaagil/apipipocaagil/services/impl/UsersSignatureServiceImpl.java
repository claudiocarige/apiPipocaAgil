package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersSignatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersSignatureServiceImpl implements UsersSignatureService {

    private final UsersService userService;

    @Override
    public void signatureToUser(String email){
        var id = userService.findByUsername(email).getId();
        userService.updateRoleToSigned(UserPermissionType.ROLE_SIGNED, id);
        log.info("User has been changed to subscriber");
    }
}
