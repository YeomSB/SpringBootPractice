package org.example.mySpringProj.repository.userRepository;

import org.example.mySpringProj.domain.userDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);

    Optional<User> findByUserNameOrEmailOrNickNameOrPhoneNumber(String userName, String email, String nickName,String phoneNumber);
    @Query("select u from User u where u.userName = :username")
    User findByUsername(@Param("username") String username);


}
