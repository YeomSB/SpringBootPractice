package org.example.mySpringProj.service.passwordService;

public interface PasswordService {
    //Boolean CheckPassword(String selectedUserName, String selectedUserPassword);
    void CheckPassword(String selectedUserName, String selectedUserPassword);

    String findID(String email);
    void findPWD(String userName);
}
