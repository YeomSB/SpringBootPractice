package org.example.mySpringProj.service.userService;

import org.example.mySpringProj.domain.userDomain.CustomUserDetails;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUserName(username)
            .map(CustomUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
