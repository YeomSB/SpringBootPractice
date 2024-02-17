package org.example.mySpringProj.domain.userDomain;

import org.example.mySpringProj.dto.userDto.UserListResponse;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Valid
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; //키 값

    @NotBlank
    @Column(nullable = false, unique = true, length=50)
    private String userName; //가입 ID

    @NotBlank
    @Column(nullable = false, length=200)
    private String password; //가입 PW

    @NotBlank
    @Column(nullable = false, length=50)
    private String name; //이름

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length=50)
    private String email; //이메일

    @NotBlank
    @Column(nullable = false, unique = true, length=50)
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false, unique = true, length=50)
    private String nickName; //닉네임

    @CreationTimestamp
    private Date regdate; //등록일자

    @UpdateTimestamp
    private Date updatedate; //갱신일자

    @Enumerated(EnumType.STRING)
    private Role role; //권한

    @OneToOne(mappedBy = "user")
    private Terms terms;


    public UserListResponse toDto() {
        return new UserListResponse(userName, name, email, phoneNumber, nickName, role);
    }


}
