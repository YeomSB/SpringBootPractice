package org.example.mySpringProj.domain.commentDomain;

import jakarta.persistence.*;
import lombok.*;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.userDomain.User;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReComment {
    @Id
    @GeneratedValue
    @Column(name = "recomment_id")
    private Long id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Date regdate; //등록일자

}
