package org.example.mySpringProj.service.logoutService;

import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.userRepository.JwtRepository;
import org.example.mySpringProj.utils.JwtTokenUtil;
import org.example.mySpringProj.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService {

    private final JwtRepository jwtRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisUtil redisUtil;


    @Transactional
    public ResponseDTO logout(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null)
            throw new AppException(ErrorCode.UNAUTHORIZED,"인증 정보가 유효하지 않습니다.",null);

        String token = authorization.split(" ")[1];

        if (!jwtTokenUtil.validateToken(token)) {
            throw new AppException(ErrorCode.UNAUTHORIZED,"접근이 금지된 토큰입니다.",null);
        }

        String userName = jwtTokenUtil.getUserName(token);
        jwtRepository.deleteByUserName(userName);

        Long expiration = jwtTokenUtil.getExpiration(token);
        redisUtil.setBlackList(token, "access_token", expiration);

        SecurityContextHolder.clearContext();

        return ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent(userName + "님이 로그아웃 되었습니다.")
                .build();
    }
}
