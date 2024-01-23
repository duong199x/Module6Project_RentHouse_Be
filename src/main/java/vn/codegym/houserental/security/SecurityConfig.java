package vn.codegym.houserental.security;

import vn.codegym.houserental.security.jwt.CustomAccessDeniedHandler;
import vn.codegym.houserental.security.jwt.JwtAuthenticationFilter;
import vn.codegym.houserental.security.jwt.RestAuthenticationEntryPoint;
import vn.codegym.houserental.service.UserService;
import vn.codegym.houserental.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
    

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/login", "/auth/register", "/hello","/**").permitAll()
                                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers("/users/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.GET).hasAnyRole("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                        .requestMatchers(HttpMethod.DELETE, "/categories",
//                                "/typeOfQuestions",
//                                "/questions",
//                                "/answers",
//                                "/quizzes",
//                                "/hello").hasAnyAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/users").hasAnyAuthority("ROLE_USER")
                )
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}