package hexlet.code.config;

import hexlet.code.service.userDetailsService.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
@EnableWebSecurity // Подключаем поддержку Spring Security
public class SecurityConfig {

    @Autowired
    JwtDecoder jwtDecoder; //priv final

    @Autowired
    PasswordEncoder passwordEncoder; //priv final

    @Autowired
    UserDetailsServiceImpl userDetailsManager; //priv

    @Bean
        //Пытается аутентифицировать переданный объект аутентификации, возвращая
        // полностью заполненный объект аутентификации (включая предоставленные полномочия) в случае успеха.
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = http.getSharedObject(AuthenticationManagerBuilder.class).build();
        return authManager;
    }

    @Bean
    //Указывает, что класс может обрабатывать определенную реализацию аутентификации.
    AuthenticationProvider authenticationProvider() { //было (AuthenticationManagerBuilder authManagerBuilder)
        var authProvider = new DaoAuthenticationProvider(); //data access object authentication provider
        authProvider.setUserDetailsService(userDetailsManager);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {

        //RequestMatcher, который использует Spring MVC-шный, HMI обработчик сопоставления интроспектора,
        // для сопоставления пути и извлечения переменных.
        MvcRequestMatcher.Builder mvcRequestMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

// Note that the matchers are considered in order.
// Therefore, the following is invalid because the first matcher matches every request
// and will never get to the second mapping:
        httpSecurity
                .csrf(csrf -> csrf.disable())   //межсайтовая подделка запроса
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll() //получить список юзеров
                        // /api/users/* разрешит открывать /api/users/1   api/users/** разрешает любую последоват
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() //регистрация нового
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()  //на смену antMatchers()
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(server -> server.jwt(jwt -> jwt.decoder(jwtDecoder)))
                .httpBasic(Customizer.withDefaults());
        
        return httpSecurity.build();
    }
}
