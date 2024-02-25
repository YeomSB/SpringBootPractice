package org.example.mySpringProj.domain.boardDomain;

import jakarta.persistence.*;
import lombok.*;
import org.example.mySpringProj.domain.categoryDomain.Category;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.userDomain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private long userId;
    private String title;
    private String contents;
    private int likeCount;
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public void updateLikeCount() {
        this.likeCount ++;
    }
    public void downLikeCount() {
        this.likeCount--;
    }
    public void updateViewCount() {
        this.viewCount ++;
    }
}