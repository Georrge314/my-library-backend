package com.bibliotek.web;

import com.bibliotek.domain.dto.Credentials;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.mapper.UserViewMapper;
import com.bibliotek.domain.model.User;
import com.bibliotek.service.UserService;
import com.bibliotek.config.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private UserViewMapper userViewMapper;

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid Credentials credentials) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword()));

            User user = (User) authenticate.getPrincipal();
            log.info("Login successful for: {}", user.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user))
                    .body(userViewMapper.toUserView(user));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
//        User user = userService.getUserByUsername(credentials.getUsername());
//        String token = jwtUtil.generateToken(user);
//        log.info("Login successful for {}: {}", user.getUsername(), token);
//        return ResponseEntity.ok(new JwtResponse(token, user));
    }

    @PostMapping("register")
    public UserView register(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }
}
