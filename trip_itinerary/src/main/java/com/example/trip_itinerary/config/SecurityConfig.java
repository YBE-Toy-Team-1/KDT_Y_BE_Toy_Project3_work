package com.example.trip_itinerary.config;

import com.example.trip_itinerary.member.jwt.JwtAuthenticationFilter;
import com.example.trip_itinerary.member.jwt.JwtTokenProvider;
import com.example.trip_itinerary.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private JwtTokenProvider tokenProvider;
    private MemberDetailsService memberDetailsService;




@Bean //todo 수정 3 최근버전에서 configure 는 deprecated됨  아래가 새로운...
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            .csrf().disable()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//            .exceptionHandling()
//            .authenticationEntryPoint(new RestAuthenticationEntryPoint()).and()
//            .authorizeRequests()
//            .antMatchers(
//                    "/",
//                    "/favicon.ico",
//                    "/**/*.png",
//                    "/**/*.gif",
//                    "/**/*.svg",
//                    "/**/*.jpg",
//                    "/**/*.html",
//                    "/**/*.css",
//                    "/**/*.js"
//            ).permitAll()
//            .antMatchers("/api/auth/**").permitAll()
//            .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
//            .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**").permitAll()
//            .anyRequest().authenticated();
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((request) -> request
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
//            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
}


//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }

}
