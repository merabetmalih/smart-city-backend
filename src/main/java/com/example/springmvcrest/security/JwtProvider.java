package com.example.springmvcrest.security;

import com.example.springmvcrest.user.domain.Role;
import com.example.springmvcrest.user.domain.RoleContext;
import com.example.springmvcrest.user.domain.User;
import com.example.springmvcrest.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private String SECRET_KEY = "secret";
    @Autowired
    private UserService userService;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .compact();
    }

    public String getUsername(String token) {
        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replace("Bearer ", ""));
        return parsedToken
                .getBody()
                .getSubject();
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("error.user.not-found"));
        List<SimpleGrantedAuthority> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(RoleContext::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());



        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassWord(), roles);
    }
}