package com.bibliotek.web;

import com.bibliotek.domain.model.User;
import com.bibliotek.service.UserService;
import com.bibliotek.config.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping
    public ResponseEntity<JwtResponse> login(@RequestBody Credentials credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        User user = userService.getUserByUsername(credentials.getUsername());
        String token = jwtUtil.generateToken(user);
        log.info("Login successful for {}: {}", user.getUsername(), token);
        return ResponseEntity.ok(new JwtResponse(token, user));
    }
}
