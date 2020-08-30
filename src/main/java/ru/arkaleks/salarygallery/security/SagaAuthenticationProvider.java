package ru.arkaleks.salarygallery.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.arkaleks.salarygallery.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SagaAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    public SagaAuthenticationProvider(@Lazy BCryptPasswordEncoder encoder, @Lazy UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails user = userService.loadUserByUsername(username);
        log.warn(user.getUsername() + " is authenticate!");
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("1000");
        }
        List<GrantedAuthority> roles = new ArrayList<>(user.getAuthorities());
        for (GrantedAuthority role : roles) {
            log.warn(role.getAuthority() + "real role");
        }
        return new UsernamePasswordAuthenticationToken(username, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}