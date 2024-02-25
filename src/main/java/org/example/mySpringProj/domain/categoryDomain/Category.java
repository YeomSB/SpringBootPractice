package org.example.mySpringProj.domain.categoryDomain;

import jakarta.persistence.*;
import lombok.*;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.userDomain.User;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        private Long id;

        private String name;

        @OneToMany(mappedBy = "category")
        private List<Tag> tags;

        @OneToMany(mappedBy = "category")
        private List<Board> boards;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

}
