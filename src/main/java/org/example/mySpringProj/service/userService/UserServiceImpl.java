package org.example.mySpringProj.service.userService;

import org.example.mySpringProj.domain.userDomain.Terms;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.userDto.UserJoinRequest;
import org.example.mySpringProj.dto.userDto.UserListResponse;
import org.example.mySpringProj.dto.userDto.UserModifyRequest;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.userRepository.TermsRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public String join(UserJoinRequest dto){
        duplicateCheck(dto.getUserName(),dto.getEmail(),dto.getNickName(),dto.getPhoneNumber());

        User savedUser = userRepository.save(User.builder()
                .userName(dto.getUserName())
                .password(encoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .nickName(dto.getNickName())
                .name(dto.getName())
                .role(dto.getRole())
                .build());

        termsRepository.save(Terms.builder()
                .agreedToTerms1(dto.isAgreedToTerms1())
                .agreedToTerms2(dto.isAgreedToTerms2())
                .agreedToOptionalTerms(dto.isAgreedToOptionalTerms())
                .user(savedUser)
                .build());

        return "successJoin";
    }

    @Override
    public void modifyUser(String userName, UserModifyRequest dto){
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.",dto));

        duplicateCheck(userName, dto.getEmail(), dto.getNickName(), dto.getPhoneNumber());

        if(dto.getNewPassword() != null) {
            if(encoder.matches(dto.getNewPassword(),user.getPassword()))
                throw new AppException(HttpStatus.CONFLICT, "이미 같은 비밀번호로 설정되어 있습니다.",dto);
            user.setPassword(encoder.encode(dto.getNewPassword()));
        }

        if (dto.getNickName() != null) {
            if (user.getNickName().equals(dto.getNickName()))
                throw new AppException(HttpStatus.CONFLICT, "이미 같은 닉네임으로 설정되어 있습니다.",dto);
            user.setNickName(dto.getNickName());
        }

        if(dto.getEmail() != null){
            if(user.getEmail().equals(dto.getEmail())) {
                throw new AppException(HttpStatus.CONFLICT, "이미 같은 Email로 설정되어 있습니다.",dto);
            }
            user.setEmail(dto.getEmail());
        }

        if(dto.getPhoneNumber() != null){
            if(user.getPhoneNumber().equals(dto.getPhoneNumber())) {
                throw new AppException(HttpStatus.CONFLICT, "이미 같은 번호로 설정되어있습니다.",dto);
            }
            user.setEmail(dto.getEmail());
        }

        if (dto.getRole() != null) {
            if (user.getRole().equals(dto.getRole())) {
                throw new AppException(HttpStatus.CONFLICT, "이미 같은 권한을 소지하고 있습니다.",dto);
            }
            user.setRole(dto.getRole());
        }

        if (dto.getName() != null) {
            if (user.getName().equals(dto.getName())) {
                throw new AppException(HttpStatus.CONFLICT, "이미 같은 이름으로 설정되어 있습니다.",dto);
            }
            user.setName(dto.getName());
        }

        userRepository.save(user);
    }

    @Override
    public UserListResponse getUser(String userName){

        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "해당" +userName + "가 없습니다.",userName));

        UserListResponse userListResponse = UserListResponse.builder()
                .userName(selectedUser.getUserName())
                .name(selectedUser.getName())
                .nickName(selectedUser.getNickName())
                .email(selectedUser.getEmail())
                .phoneNumber(selectedUser.getPhoneNumber())
                .role(selectedUser.getRole())
                .build();

        return  userListResponse;
    }

    @Override
    public List<UserListResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(String userName){
        User user = userRepository.findByUsername(userName);
        termsRepository.deleteByUserId(user.getId());
        userRepository.delete(user);
    }

    private void duplicateCheck(String userName, String email, String nickName, String phoneNumber) {
        userRepository.findByUserNameOrEmailOrNickNameOrPhoneNumber(userName, email, nickName, phoneNumber)
                .ifPresent(user -> {
                    if (user.getUserName().equals(userName)) {
                        throw new AppException(HttpStatus.CONFLICT, "USERNAME : " + userName + "는 이미 있습니다", null);
                    } else if (user.getEmail().equals(email)) {
                        throw new AppException(HttpStatus.CONFLICT, email + "은 이미 사용중인 이메일입니다.", null);
                    } else if (user.getNickName().equals(nickName)) {
                        throw new AppException(HttpStatus.CONFLICT, nickName + "은 이미 사용중인 닉네임입니다.", null);
                    } else if (user.getPhoneNumber().equals(phoneNumber)) {
                        throw new AppException(HttpStatus.CONFLICT, phoneNumber + "은 이미 사용중인 번호입니다.", null);
                    }
                });
    }

}
