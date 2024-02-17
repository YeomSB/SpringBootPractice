package org.example.mySpringProj.service.loginService;

import org.example.mySpringProj.domain.userDomain.RefreshToken;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.userDto.TokenDto;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.userRepository.JwtRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.example.mySpringProj.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final JwtRepository jwtRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 60 * 60 * 1000L;
    private Long RefreshTokenexpireTimeMs = 24 * 60 * 60 * 1000L;

    @Override
    public TokenDto login(String userName, String password) { //로그인
        //유저 없음
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, userName + "가 없습니다.",null));
        //비번 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.BAD_REQUEST, "비밀번호가 틀렸습니다.",null);
        }
        //각 토큰 생성
        String accessToken = JwtTokenUtil.createToken
                (selectedUser.getUserName(),"accessToken", key, expireTimeMs);
        String refreshToken = JwtTokenUtil.createToken
                (selectedUser.getUserName(),"refreshToken", key, RefreshTokenexpireTimeMs);

        //리프레시 토큰 DB 저장 (이미 있는 유저는 갱신)
        RefreshToken existingToken = jwtRepository.findByUserName(userName);
        if (existingToken != null) {
            existingToken.setRefreshToken(refreshToken);
            jwtRepository.save(existingToken);
        }
        else {
            RefreshToken refreshTokenDB = RefreshToken.builder()
                    .userName(userName)
                    .refreshToken(refreshToken)
                    .build();
            jwtRepository.save(refreshTokenDB);
        }

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

}
