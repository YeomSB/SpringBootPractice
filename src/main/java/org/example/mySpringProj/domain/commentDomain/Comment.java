package org.example.mySpringProj.domain.commentDomain;

import jakarta.persistence.*;
import lombok.*;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.userDomain.User;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "comment")
    private List<ReComment> reComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Date regdate; //등록일자
}
