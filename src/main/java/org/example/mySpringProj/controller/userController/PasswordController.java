package org.example.mySpringProj.controller.userController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.userDto.EmailRequest;
import org.example.mySpringProj.dto.userDto.UserModifyRequest;
import org.example.mySpringProj.dto.userDto.UserPwdChangeRequest;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.service.MailService.MailServiceImpl;
import org.example.mySpringProj.service.passwordService.PasswordService;
import org.example.mySpringProj.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/find")
public class PasswordController {
    private final PasswordService passwordService;
    private final UserService userService;
    private final MailServiceImpl mailService;

    @PostMapping("/id")
    public ResponseEntity<ResponseDTO> findID(@Valid @RequestBody EmailRequest dto) {
        String userName = passwordService.findID(dto.getEmail());

        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent(dto.getEmail() +" 에 해당하는 ID는 [" + userName +"] 입니다")
                .build()
        );
    }

    @PostMapping("/password")
    public ResponseEntity<ResponseDTO> findPWD(@Valid @RequestBody EmailRequest dto) {
        passwordService.findPWD(dto.getUserName());

        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent(mailService.joinEmail(dto.getEmail()))
                .build()
        );
    }

    @PatchMapping("/changePW")
    public ResponseEntity<ResponseDTO> changePWD(@Valid @RequestBody UserPwdChangeRequest dto) {
        String userName = passwordService.findID(dto.getEmail());
        passwordService.CheckPassword(userName,dto.getPassword());

        if(!dto.getNewPassword().equals(dto.getNewPasswordAgain()))
            throw new AppException(ErrorCode.BAD_REQUEST, "변경하려는 새 비밀번호의 입력값이 서로 다릅니다.",dto);

        UserModifyRequest userModifyRequest = UserModifyRequest.builder()
                .newPassword(dto.getNewPassword())
                .build();

        userService.modifyUser(userName,userModifyRequest);

        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("비밀번호가 정상적으로 변경되었습니다.")
                .Data(userModifyRequest)
                .build()
        );
    }

}
