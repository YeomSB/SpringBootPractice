package org.example.mySpringProj.domain.userDomain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Terms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
    private Long id; //키 값

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean agreedToTerms1;
    private boolean agreedToTerms2;
    private boolean agreedToOptionalTerms;
}
