package hexlet.code.controller;

import hexlet.code.dto.AuthRequestDto;
import hexlet.code.model.User;
import hexlet.code.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import io.jsonwebtoken
@RestController
@RequestMapping("${base-url}" + "/login")

public class AuthController {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager; //обрабатывает ауф-риквест, возвр заполненный О Ауф с полномоч

    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping()
    ResponseEntity<String> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        try {
            log.info("§§§AuthCont: got l/p: " + authRequestDto.getEmail() + " non hashed pwd:" + authRequestDto.getPassword());
            authenticationManager.authenticate( //см выше
            new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
            );
        } catch (Exception e) {
            log.info("§§§AuthCont: ivalid l/p");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid login/password");
        }

        return ResponseEntity.status(HttpStatus.OK).body(jwtUtils.generateToken(authRequestDto.getEmail()));
    }

}
























