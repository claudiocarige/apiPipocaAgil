package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContextCheckImpl{

    private final UsersService userService;
    public void checkUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        log.info("Checking user {} ", username);
        if (username != null ) {
            var userId = userService.findByUsername(username);
            if (!id.equals(userId.getId())) {
                throw new AccessDeniedException("Access denied, You're not authorized to modify this User");
            }
        }
    }
    public UserLoginRepresentation checkUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        log.info("Checking user {} ", username);
        var user = userService.findByUsername(username);
        if (username == null){
            throw new AccessDeniedException("Access Denied. You must be logged in for this operation!");
        }
        return new UserLoginRepresentation(user.getUsername(), user.getPassword());
    }
}
