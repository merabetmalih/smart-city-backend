package com.example.springmvcrest.security;

import com.example.springmvcrest.user.domain.Role;
import com.example.springmvcrest.user.domain.RoleContext;
import com.example.springmvcrest.user.domain.User;
import com.example.springmvcrest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("error.user.not-found"));
        List<SimpleGrantedAuthority> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(RoleContext::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        com.example.springmvcrest.security.User userSec=new com.example.springmvcrest.security.User(user.getEmail(),
                user.getPassWord(), roles);
        userSec.setId(user.getId());

        return  userSec;
    }

    public User getUser(String username) {
        return userService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("error.user.not-found"));
    }
}