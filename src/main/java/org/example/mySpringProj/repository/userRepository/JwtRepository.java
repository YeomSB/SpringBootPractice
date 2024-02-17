package org.example.mySpringProj.repository.userRepository;

import org.example.mySpringProj.domain.userDomain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserName(String userName);
    RefreshToken findByRefreshToken(String refreshToken);
    void deleteByUserName(String userName);

}
