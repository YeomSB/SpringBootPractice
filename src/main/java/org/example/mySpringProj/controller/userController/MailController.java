package org.example.mySpringProj.controller.userController;

import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.userDto.EmailCheckDTO;
import org.example.mySpringProj.dto.userDto.EmailRequest;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.service.MailService.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    @PostMapping("/Send")
    public ResponseDTO mailSend(@RequestBody @Valid EmailRequest dto) {
        mailService.joinEmail(dto.getEmail());
        return ResponseDTO.success(HttpStatus.OK,dto.getEmail()+"\n해당 메일에 인증번호가 정상 전송되었습니다.",dto);
    }

    @PostMapping("/AuthCheck")
    public ResponseDTO AuthCheck(@RequestBody @Valid EmailCheckDTO dto){
        Boolean Checked=mailService.CheckAuthNum(dto.getEmail(),dto.getAuthNum());
        if(Checked){
            return ResponseDTO.success(HttpStatus.OK,"인증이 완료되었습니다",dto);
        }
        else{
            throw new AppException(ErrorCode.BAD_REQUEST,"인증 번호가 맞지 않습니다.",dto);
        }
    }

}