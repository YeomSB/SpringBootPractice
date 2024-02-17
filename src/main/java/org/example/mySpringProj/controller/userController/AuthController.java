package org.example.mySpringProj.controller.userController;

import jakarta.validation.Valid;
import org.example.mySpringProj.dto.userDto.TokenDto;
import org.example.mySpringProj.dto.userDto.UserLoginRequest;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.service.authService.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.service.loginService.LoginService;
import org.example.mySpringProj.service.logoutService.LogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final LoginService loginService;
    private final LogoutService logoutService;

    @GetMapping("/reissue")
    public ResponseEntity<ResponseDTO> reissue(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String refreshToken = authorization.split(" ")[1];
        String newAccessToken = authService.reissue(refreshToken);
        TokenDto tokenDto = new TokenDto(newAccessToken,null);
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("토큰 재발급 성공")
                .Data(tokenDto)
                .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody UserLoginRequest dto){
        TokenDto tokenDto = loginService.login(dto.getUserName(), dto.getPassword());
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent(dto.getUserName()+"님이 로그인 되었습니다. 환영합니다.")
                .Data(tokenDto)
                .build()
        );
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(HttpServletRequest request) {
        return ResponseEntity.ok(logoutService.logout(request));
    }

}
