package com.example.springmvcrest.security.simple;

import com.example.springmvcrest.user.user.domain.Role;
import com.example.springmvcrest.user.user.domain.RoleContext;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
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
    private final SimpleUserService simpleUserService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SimpleUser user = simpleUserService.findSimpleUserByEmail(email)
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

    /*public User getUser(String username) {
        return simpleUserService.findSimpleUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("error.user.not-found"));
    }*/
}