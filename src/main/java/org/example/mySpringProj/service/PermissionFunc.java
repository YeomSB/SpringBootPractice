package org.example.mySpringProj.service;

import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;

public class PermissionFunc {
    public static void hasPermission(String tarName, String reqName){
        if(!tarName.equals(reqName))
            throw new AppException(ErrorCode.BAD_REQUEST,"권한이 없습니다",null);
    }
}
