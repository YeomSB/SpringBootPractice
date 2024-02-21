package org.example.mySpringProj.service.findService;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.userDto.UserModifyRequest;
import org.example.mySpringProj.dto.userDto.UserPwdChangeRequest;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordServiceImpl implements PasswordService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    @Override
    public void CheckPassword(String selectedUserName, String selectedUserPassword){
        User user = checkUser(selectedUserName);

            if (!encoder.matches(selectedUserPassword, user.getPassword()))
                throw new AppException(ErrorCode.BAD_REQUEST, "잘못된 비밀번호 입력입니다.",selectedUserPassword);
    }

    @Override
    public String findID(String email) {
//        Optional<User> targetUser = userRepository.findByEmail(email);
//
//        if (targetUser.isPresent()) { //찾는 유저가 있으면
//            User user = targetUser.get();
//            return user.getUserName();
//        }
//        else { //찾는 유저가 없으면
//            throw new AppException(ErrorCode.NOT_FOUND,"해당 Email로 가입된 유저가 없습니다.",email);
//        }

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND, "해당 Email로 가입된 유저가 없습니다.",email))
                .getUserName();


    }

    @Override
    public void findPWD(String userName){
        checkUser(userName);
    }

    @Override
    public UserModifyRequest ChangePassword(UserPwdChangeRequest dto) {
        if(!dto.getNewPassword().equals(dto.getNewPasswordAgain()))
            throw new AppException(ErrorCode.BAD_REQUEST, "변경하려는 새 비밀번호의 입력값이 서로 다릅니다.",dto);

        return UserModifyRequest.builder()
                .newPassword(dto.getNewPassword())
                .build();
    }

    private User checkUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 " + userName + " 가 없습니다.",userName));
    }

}
