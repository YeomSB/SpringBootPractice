package org.example.mySpringProj.controller.userController;

import jakarta.validation.Valid;
import org.example.mySpringProj.dto.userDto.TokenDto;
import org.example.mySpringProj.dto.userDto.UserLoginRequest;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.service.authService.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.service.loginlogoutService.LoginLogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final LoginLogoutService loginLogoutService;

    @GetMapping("/reissue")
    public ResponseDTO reissue(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String refreshToken = authorization.split(" ")[1];
        String newAccessToken = authService.reissue(refreshToken);
        TokenDto tokenDto = new TokenDto(newAccessToken,null);

        return ResponseDTO.success(HttpStatus.OK, "토큰 재발급 성공", tokenDto);
    }

    @PostMapping("/login")
    public ResponseDTO login(@Valid @RequestBody UserLoginRequest dto){
        TokenDto tokenDto = loginLogoutService.login(dto.getUserName(), dto.getPassword());
        return ResponseDTO.success(HttpStatus.OK, dto.getUserName()+"님이 로그인 되었습니다. 환영합니다.", tokenDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(HttpServletRequest request) {
        return ResponseEntity.ok(loginLogoutService.logout(request));
    }

}
