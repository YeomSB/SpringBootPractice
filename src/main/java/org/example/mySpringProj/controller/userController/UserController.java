package org.example.mySpringProj.controller.userController;

import jakarta.validation.Valid;
import org.example.mySpringProj.domain.userDomain.Role;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.userDto.UserJoinRequest;
import org.example.mySpringProj.dto.userDto.UserListResponse;
import org.example.mySpringProj.dto.userDto.UserModifyRequest;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.service.logoutService.LogoutService;
import org.example.mySpringProj.service.MailService.MailService;
import org.example.mySpringProj.service.passwordService.PasswordService;
import org.example.mySpringProj.service.userService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final LogoutService logoutService;
    private final MailService mailService;
    private final PasswordService passwordService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> join(@Valid @RequestBody UserJoinRequest dto) {
        if (!dto.isAgreedToTerms1() || !dto.isAgreedToTerms2())
            throw new AppException(ErrorCode.BAD_REQUEST, "이용 약관의 필수 항목을 체크해주세요.",dto);


        boolean isAuthVerified = mailService.CheckAuthNum(dto.getEmail(), dto.getAuthNum());

        if (!isAuthVerified)
            throw new AppException(ErrorCode.BAD_REQUEST, "인증번호가 일치하지 않습니다.",dto);

        userService.join(dto);
        mailService.DeleteAuthNum(dto.getAuthNum());

        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("님이 회원가입 되었습니다")
                .Data(dto)
                .build()
        );
    }

    @PatchMapping("/modify")
    public ResponseEntity<ResponseDTO> modify(@Valid @RequestBody UserModifyRequest dto, Authentication authentication) {
        String userName = authentication.getName();
        passwordService.CheckPassword(userName,dto.getPassword());
        userService.modifyUser(userName,dto);
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("회원 정보가 정상적으로 변경되었습니다.")
                .Data(dto)
                .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(name = "search") String target, Authentication authentication){
        // all 모두 (관리자권한만) //유저이름 유저데이터 반환 //빈칸이거나 공백이면 오류반환
        String reqUser = authentication.getName();
        UserListResponse user = userService.getUser(reqUser);
        if(target.equals("All")) {
            if (user.getRole().equals(Role.ADMIN))
                return ResponseEntity.ok().body(userService.getALL());
            else
                throw new AppException(ErrorCode.BAD_REQUEST, "해당 접근에 대한 권한이 없습니다.",null);
        }
        else{
            UserListResponse respUser = userService.getUser(target);
            return ResponseEntity.ok().body(respUser);
        }
    }

    @DeleteMapping("deleteUser/{userName}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("userName") String userName, Authentication authentication, HttpServletRequest request) {
        String reqUser = authentication.getName();
        Role targetRole = userService.getUser(reqUser).getRole();

        if (targetRole.equals(Role.ADMIN) || reqUser.equals(userName)) {
            userService.deleteUser(userName);
            logoutService.logout(request);

            return ResponseEntity.ok(ResponseDTO.builder()
                    .successStatus(HttpStatus.OK)
                    .successContent(userName + "님의 회원 탈퇴가 완료되었습니다.")
                    .build()
            );
        }
        else
            throw new AppException(ErrorCode.BAD_REQUEST, "해당 유저를 삭제할 권한이 없습니다.",null);
    }


}
