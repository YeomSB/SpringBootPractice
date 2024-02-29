package org.example.mySpringProj.service;

import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UtilFunc {

    public static void hasPermission(String tarName, String reqName){
        if(!tarName.equals(reqName))
            throw new AppException(HttpStatus.BAD_REQUEST,"권한이 없습니다",null);
    }

}
