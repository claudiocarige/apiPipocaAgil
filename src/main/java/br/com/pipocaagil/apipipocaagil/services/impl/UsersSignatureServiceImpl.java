package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.Users;
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
    public void signatureToUser(String email) {
        Users user = userService.findByUsername(email);
        if (!user.getRole().equals(UserPermissionType.ROLE_ADMIN)) {
            userService.updateRoleToSigned(UserPermissionType.ROLE_SIGNED, user.getId());
            log.info("User has been changed to subscriber");
        }
        log.info("User hasn't been changed to subscriber");
    }
}
