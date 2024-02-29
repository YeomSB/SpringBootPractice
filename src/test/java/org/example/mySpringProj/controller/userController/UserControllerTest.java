//package org.example.mySpringProj.controller.userController;
//
//import org.example.mySpringProj.domain.userDomain.Role;
//import org.example.mySpringProj.domain.userDomain.User;
//import org.example.mySpringProj.dto.userDto.UserJoinRequest;
//import org.example.mySpringProj.repository.userRepository.UserRepository;
//import org.example.mySpringProj.service.loginlogoutService.LoginLogoutService;
//import org.example.mySpringProj.service.userService.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerTest {
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    UserService userService;
//
//    @Test
//    void joinTest() {
//        UserJoinRequest dto = new UserJoinRequest();
//        dto.setUserName("testid");
//        dto.setPassword("testpassword");
//        dto.setEmail("test1@email.com");
//        dto.setPhoneNumber("010-1234-1234");
//        dto.setNickName("testnickname");
//        dto.setName("홍길동");
//        dto.setRole(Role.ADMIN);
//
//        User user = User.builder()
//                .userName(dto.getUserName())
//                .password(dto.getPassword())
//                .email(dto.getEmail())
//                .phoneNumber(dto.getPhoneNumber())
//                .nickName(dto.getNickName())
//                .name(dto.getName())
//                .role(dto.getRole())
//                .build();
//
//        //given
//        given(userRepository.save(any())).willReturn(user);
//
//        //when
//        String result = userService.join(dto);
//
//        //then
//        assertThat(result).isEqualTo("successJoin");
//
//    }
//
//}