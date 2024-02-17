package org.example.mySpringProj.service.loginService;

import org.example.mySpringProj.dto.userDto.TokenDto;

public interface LoginService {
    TokenDto login(String userName, String password);
}
