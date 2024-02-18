package org.example.mySpringProj.configuration;

import org.example.mySpringProj.service.logoutService.LogoutService;
import org.example.mySpringProj.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .cors((Customizer<CorsConfigurer<HttpSecurity>>) (cors) -> {
//                    cors.configurationSource(corsConfigurationSource());
//                })
                .cors(c -> c.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.addAllowedOrigin("http://localhost:3000");
                    configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
                    configuration.setAllowCredentials(true);
                    return configuration;}
                ))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.POST,"/find/**").permitAll() //아이디 찾기, 비밀번호 찾기, 비밀번호 변경 로직
                        .requestMatchers(HttpMethod.POST,"/users/join","/auth/login").permitAll() //회원가입, 로그인 로직
                        .requestMatchers(HttpMethod.POST,"/mail/**").permitAll() // 이메일 보내기 로직
                        .requestMatchers("/swagger-ui/**","/swagger/**","/swagger-resources/**","/v3/api-docs/**","/").permitAll() //swagger 풀어두기
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)

//                .logout((logout) -> logout
//                        .logoutUrl("/logout")
//                        .addLogoutHandler(logoutService)
//                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())))

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


//    @Bean
//    CsrfConfigurer csrfConfigurer() {
////        CsrfConfigurer csrfConfigurer = new CsrfConfigurer();
////        csrfConfigurer.disable();
////        return csrfConfigurer;
//    }

}