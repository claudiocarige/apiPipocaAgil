package br.com.pipocaagil.apipipocaagil.jwt;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users usuario = usuarioService.findByUsername(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String username) {
        UserPermissionType role = usuarioService.findByRoleFromUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
