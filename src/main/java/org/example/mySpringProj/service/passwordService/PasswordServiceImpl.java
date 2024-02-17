package org.example.mySpringProj.service.passwordService;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordServiceImpl implements PasswordService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    @Override
    public void CheckPassword(String selectedUserName, String selectedUserPassword){
        Optional<User> selectedUser = userRepository.findByUserName(selectedUserName);

        if (selectedUser.isPresent()) {
            User user = selectedUser.get();
            if (!encoder.matches(selectedUserPassword, user.getPassword()))
                throw new AppException(ErrorCode.BAD_REQUEST, "잘못된 비밀번호 입력입니다.",selectedUserPassword);
        }
        else
            throw new AppException(ErrorCode.NOT_FOUND, "해당 인증 정보로 구성된 유저 정보가 없습니다.",selectedUserName);
    }

    @Override
    public String findID(String email) {
        Optional<User> targetUser = userRepository.findByEmail(email);

        if (targetUser.isPresent()) { //찾는 유저가 있으면
            User user = targetUser.get();
            return user.getUserName();
        }
        else { //찾는 유저가 없으면
            throw new AppException(ErrorCode.NOT_FOUND,"해당 Email로 가입된 유저가 없습니다.",email);
        }
    }

    @Override
    public void findPWD(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당" +userName + "가 없습니다.",userName));
    }

}
