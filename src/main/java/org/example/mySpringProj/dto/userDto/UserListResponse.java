package org.example.mySpringProj.dto.userDto;

import org.example.mySpringProj.domain.userDomain.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserListResponse {
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private String nickName;
    private Role role;

}
