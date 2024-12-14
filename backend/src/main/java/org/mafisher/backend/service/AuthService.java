package org.mafisher.backend.service;

import jakarta.servlet.http.HttpServletResponse;
import org.mafisher.backend.dto.request.LoginRequest;
import org.mafisher.backend.dto.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    void login(LoginRequest request, HttpServletResponse response);
    void logout(HttpServletResponse response);
}
