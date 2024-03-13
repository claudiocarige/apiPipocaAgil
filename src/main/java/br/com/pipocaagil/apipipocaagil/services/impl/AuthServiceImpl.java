package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.jwt.JwtToken;
import br.com.pipocaagil.apipipocaagil.jwt.JwtUserDetailsService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    @Override
    public JwtToken loginUser(String username, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,password);
        authenticationManager.authenticate(authenticationToken);
        return jwtUserDetailsService.getTokenAuthenticated(username);
    }
}
