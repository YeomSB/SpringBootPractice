package org.example.mySpringProj.service.authService;

import org.example.mySpringProj.domain.userDomain.RefreshToken;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.userRepository.JwtRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.example.mySpringProj.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtRepository jwtRepository;
    private final UserRepository userRepository;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 6000 * 5l;

    @Override
    public String reissue(String refreshToken) {
        String newAccessToken;
        RefreshToken dbTokenObj = jwtRepository.findByRefreshToken(refreshToken);
        if (!JwtTokenUtil.getTokenType(refreshToken,key).equals("refreshToken"))
            throw new AppException(ErrorCode.BAD_REQUEST, "제공하신 토큰은 RefreshToken이 아닙니다.",refreshToken);

        if (dbTokenObj == null || !refreshToken.equals(dbTokenObj.getRefreshToken())) {
            throw new AppException(ErrorCode.NOT_FOUND, "해당 토큰이 없습니다.",refreshToken);
        } else {
            Optional<User> user = userRepository.findByUserName(dbTokenObj.getUserName());
            User userData = user.orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다.",null));
            newAccessToken = JwtTokenUtil.createToken(userData.getUserName(),"accessToken", key, expireTimeMs);
        }

        return newAccessToken;
    }



}
