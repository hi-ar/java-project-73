package hexlet.code.config;

import hexlet.code.service.userDetailsService.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // Подключаем поддержку Spring Security
public class SecurityConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsManager;

    @Bean
        //Пытается аутентифицировать переданный объект аутентификации, возвращая
        // полностью заполненный объект аутентификации (включая предоставленные полномочия) в случае успеха.
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = http.getSharedObject(AuthenticationManagerBuilder.class).build();
        return authManager;
    }

    @Bean
    AuthenticationProvider authenticationProvider() { //было (AuthenticationManagerBuilder authManagerBuilder)
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsManager);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())   //межсайтовая подделка запроса
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers("/login").permitAll()  //на смену antMatchers(), было "/login/**"
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }
}
