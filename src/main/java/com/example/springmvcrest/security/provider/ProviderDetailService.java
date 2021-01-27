package com.example.springmvcrest.security.provider;

import com.example.springmvcrest.user.provider.domain.Provider;
import com.example.springmvcrest.user.user.domain.Role;
import com.example.springmvcrest.user.user.domain.RoleContext;
import com.example.springmvcrest.user.provider.service.ProviderService;
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
public class ProviderDetailService implements UserDetailsService {
    private final ProviderService providerService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Provider user = providerService.findProviderByEmail(email)
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

    /*public com.example.springmvcrest.user.domain.User getUser(String username) {
        return providerService.findProviderByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("error.user.not-found"));
    }*/
}
