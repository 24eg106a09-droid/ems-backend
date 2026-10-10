package com.example.busbooking.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.busbooking.entity.User;
import com.example.busbooking.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    public AuthController(UserRepository users){this.users=users;}
   @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody User u) {

    if (u.getName() == null || u.getName().isBlank()) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "Name is required"));
    }

    if (u.getEmail() == null || u.getEmail().isBlank()) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "Email is required"));
    }

    if (u.getPassword() == null || u.getPassword().isBlank()) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "Password is required"));
    }

    if (users.findByEmail(u.getEmail()).isPresent()) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "Email already registered"));
    }

    u.setRole("USER");

    User saved = users.save(u);

    return ResponseEntity.ok(
            Map.of(
                    "message", "Registration successful",
                    "userId", saved.getId()
            )
    );
}
    @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Map<String,String> body,HttpSession session){
        User u=users.findByEmail(body.get("email")).orElse(null);
        if(u==null || !u.getPassword().equals(body.get("password"))) return ResponseEntity.status(401).body(Map.of("message","Invalid email or password"));
        session.setAttribute("userId",u.getId()); session.setAttribute("role",u.getRole());
        return ResponseEntity.ok(Map.of("message","Login successful","name",u.getName(),"role",u.getRole(),"userId",u.getId()));
    }
    @PostMapping("/logout") public Map<String,String> logout(HttpSession session){session.invalidate();return Map.of("message","Logged out");}
    @GetMapping("/me") public ResponseEntity<?> me(HttpSession s){Object id=s.getAttribute("userId"); if(id==null)return ResponseEntity.status(401).body(Map.of("message","Not logged in")); return ResponseEntity.ok(Map.of("userId",id,"role",s.getAttribute("role")));}
}
