package org.example.mySpringProj.service.findService;

import org.example.mySpringProj.dto.userDto.UserModifyRequest;
import org.example.mySpringProj.dto.userDto.UserPwdChangeRequest;

public interface PasswordService {
    //Boolean CheckPassword(String selectedUserName, String selectedUserPassword);
    void CheckPassword(String userName, String userPassword);

    String findID(String email);
    void findPWD(String userName);

    UserModifyRequest ChangePassword(UserPwdChangeRequest dto);
}
