package org.example.mySpringProj.service;

import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;

public class UtilFunc {

    public static void hasPermission(String tarName, String reqName){
        if(!tarName.equals(reqName))
            throw new AppException(ErrorCode.BAD_REQUEST,"권한이 없습니다",null);
    }

//    public static User findUser(String userName) {
//        userRepository.findByNickName(userNickName)
//                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다.", userNickName));
//
//    }

}
