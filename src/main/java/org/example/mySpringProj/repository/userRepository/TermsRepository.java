package org.example.mySpringProj.repository.userRepository;

import org.example.mySpringProj.domain.userDomain.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {
    void deleteByUserName(String nickName);

}
