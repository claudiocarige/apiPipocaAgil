package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public final class ContextCheckImpl{

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
}
