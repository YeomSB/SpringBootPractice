package org.example.mySpringProj.service.userService;

import org.example.mySpringProj.dto.userDto.UserJoinRequest;
import org.example.mySpringProj.dto.userDto.UserListResponse;
import org.example.mySpringProj.dto.userDto.UserModifyRequest;

import java.util.List;


public interface UserService {


    void join(UserJoinRequest dto);

    void modifyUser(String userName, UserModifyRequest dto);

    UserListResponse getUser(String userName);

    List<UserListResponse> getALL();

    void deleteUser(String userName);


}