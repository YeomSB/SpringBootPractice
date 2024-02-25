package org.example.mySpringProj.domain.userDomain;

import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.dto.userDto.UserListResponse;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Valid
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; //키 값

    @Column(nullable = false, unique = true, length=50)
    private String userName; //가입 ID

    @Column(nullable = false, length=200)
    private String password; //가입 PW

    @Column(nullable = false, length=50)
    private String name; //이름

    @Column(nullable = false, unique = true, length=50)
    private String email; //이메일

    @Column(nullable = false, unique = true, length=50)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length=50)
    private String nickName; //닉네임

    @CreationTimestamp
    private Date regdate; //등록일자

    @UpdateTimestamp
    private Date updatedate; //갱신일자

    @Enumerated(EnumType.STRING)
    private Role role; //권한

    public UserListResponse toDto() {
        return new UserListResponse(userName, name, email, phoneNumber, nickName, role);
    }


}
