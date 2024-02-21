package org.example.mySpringProj.service.loginlogoutService;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.userDto.TokenDto;

public interface LoginLogoutService {
    TokenDto login(String userName, String password);
    ResponseDTO logout(HttpServletRequest request);
}
