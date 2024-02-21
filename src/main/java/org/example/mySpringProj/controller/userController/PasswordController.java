package org.example.mySpringProj.controller.userController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.userDto.EmailRequest;
import org.example.mySpringProj.dto.userDto.UserPwdChangeRequest;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.service.MailService.MailServiceImpl;
import org.example.mySpringProj.service.findService.PasswordService;
import org.example.mySpringProj.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/find")
public class PasswordController {
    private final PasswordService passwordService;
    private final UserService userService;
    private final MailServiceImpl mailService;

    //EmailRequest DTO는 아이디와 이메일 입력으로 구성, 아이디 찾기의 경우 이메일만 입력해도 OK, 비번찾기는 아이디만 입력해도 OK

    @PostMapping("/id")
    public ResponseDTO findID(@Valid @RequestBody EmailRequest dto) {
        String userName = passwordService.findID(dto.getEmail());
        return ResponseDTO.success(HttpStatus.OK, dto.getEmail() +" 에 해당하는 ID는 [" + userName +"] 입니다",null);
    }

    @PostMapping("/password")
    public ResponseDTO findPWD(@Valid @RequestBody EmailRequest dto) {
        passwordService.findPWD(dto.getUserName());
        return ResponseDTO.success(HttpStatus.OK, mailService.joinEmail(dto.getEmail()) + " 에 인증번호를 전송했습니다",null);
    //해당 페이지에서 이메일 입력
    //이후 다음 페이지로 넘어가면(이메일 값을 넘기는걸로하고) 인증번호 인증을 시도하는 페이지
    //인증 성공하면 이후 다음 페이지로 넘어가면(이메일 값 넘기는걸로) 비번 바꾸는 페이지.
    }

    @PatchMapping("/changePW")
    public ResponseDTO changePWD(@Valid @RequestBody UserPwdChangeRequest dto) {
        String userName = passwordService.findID(dto.getEmail());
        passwordService.CheckPassword(userName,dto.getPassword());
        userService.modifyUser(userName,passwordService.ChangePassword(dto));

        return ResponseDTO.success(HttpStatus.OK, "비밀번호가 정상적으로 변경되었습니다",null);
    }

}
