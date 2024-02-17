package org.example.mySpringProj.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

    private String userName;

    @Email
    @NotBlank(message = "이메일을 입력해 주세요")
    private String email;
}