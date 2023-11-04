package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
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

    public void signatureToUser(UserLoginRepresentation userLoginRepresentation, UserPermissionType role){
        userService.updateRoleToSigned(userLoginRepresentation, UserPermissionType.ROLE_SIGNED);
        log.info("Signature to user");
    }

    public void toPayment(){
        log.info("Payment");

    }

}
