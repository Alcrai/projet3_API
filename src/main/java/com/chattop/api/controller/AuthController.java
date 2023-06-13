package com.chattop.api.controller;

import com.chattop.api.entity.AuthRequest;
import com.chattop.api.entity.AuthResponse;
import com.chattop.api.entity.User;
import com.chattop.api.service.UserService;
import com.chattop.api.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Log4j2
@RestController
public class AuthController {
    @Autowired(required=true)
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    UserService userService;

    private User userPrincipal= new User();

    @GetMapping("/api/auth/me")
    public User getMe(){
        return userPrincipal;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            this.userPrincipal= user;
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
            log.info("User identify and token send");
            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        User userAded = userService.create(user);
        if (userAded.getEmail()!=null){
            this.userPrincipal=userService.add(userAded);
            log.info("User added"+ userAded.toString());
            try {
                Authentication authentication = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(), user.getPassword())
                );
                User userC = (User) authentication.getPrincipal();
                this.userPrincipal= userC;
                String accessToken = jwtUtil.generateAccessToken(userC);
                AuthResponse response = new AuthResponse(userC.getEmail(), accessToken);
                log.info("User identify and token send");
                return ResponseEntity.ok().body(response);

            } catch (BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }else {
            return ResponseEntity.ok().body("User is not created");
        }
    }



}

