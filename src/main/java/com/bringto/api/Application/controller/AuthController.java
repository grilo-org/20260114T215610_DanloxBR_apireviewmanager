package com.bringto.api.Application.config.controller;

import com.bringto.api.Application.config.dto.AuthRequest;
import com.bringto.api.Application.config.dto.AuthResponse;
import com.bringto.api.Application.config.model.User;
import com.bringto.api.Application.config.service.UserService;
import com.bringto.api.Application.config.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService us, JwtUtil jwtUtil) {
        this.userService = us;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest req) {
        if (userService.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Email já cadastrado"));
        }
        User u = userService.register(req.getEmail(), req.getPassword());
        String token = jwtUtil.generateToken(u.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return userService.findByEmail(req.getEmail()).map(u -> {
            if (userService.checkPassword(u, req.getPassword())) {
                String token = jwtUtil.generateToken(u.getEmail());
                return ResponseEntity.ok(new AuthResponse(token, null));
            } else {
                return ResponseEntity.status(401).body(new AuthResponse(null, "Credenciais inválidas"));
            }
        }).orElse(ResponseEntity.status(401).body(new AuthResponse(null, "Credenciais inválidas")));
    }
}
