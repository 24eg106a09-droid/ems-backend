package com.example.busbooking.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable=false)
    private String password;
    @Column(nullable=false) private String role = "USER";
    private LocalDateTime createdAt = LocalDateTime.now();
    public User() {}
    public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getName(){return name;} public void setName(String v){name=v;} public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;} public LocalDateTime getCreatedAt(){return createdAt;}
}
