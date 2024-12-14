package org.mafisher.backend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mafisher.backend.dto.request.LoginRequest;
import org.mafisher.backend.dto.request.RegisterRequest;
import org.mafisher.backend.entity.UserEntity;
import org.mafisher.backend.repository.UserRepository;
import org.mafisher.backend.service.AuthService;
import org.mafisher.backend.service.CookieService;
import org.mafisher.backend.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CookieService cookieService;

    @Override
    public void register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }

    @Override
    public void login(LoginRequest request, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            String username = request.getUsername();
            Map<String, String> claims = new HashMap<>();
            claims.put("jwt", jwtService.generateToken(username));
            response.addCookie(cookieService.getNewCookie("jwt", claims.get("jwt")));
        }
    }

    @Override
    public void logout(HttpServletResponse response) {
        response.addCookie(cookieService.deleteCookie("jwt"));
    }


}
