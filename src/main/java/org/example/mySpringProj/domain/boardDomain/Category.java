package org.example.mySpringProj.domain.boardDomain;

import jakarta.persistence.*;
import lombok.*;

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
        private List<Board> boards;

}
