package com.example.springmvcrest.security;

import com.example.springmvcrest.security.simple.UserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtils {

    public static final String USER_AUTH_URL = "/api/user/login";
    public static final String PROVIDER_AUTH_URL = "/api/provider/login";
    public static final String USER_REGISTER_URL = "/api/user/register";
    public static final String PROVIDER_REGISTER_URL = "/api/provider/register";
    public static final String FORGOT_PASSWORD_URL = "/forgot-password/**";
    public static final String TOKE_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    private final UserDetailService userDetailService;

    /*public User getCurrentConnectedUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();
                return userDetailService.getUser(username);
            } else {
                if (authentication.getPrincipal() instanceof String) {
                    return userDetailService.getUser((String) authentication.getPrincipal());
                }
            }
        }
        return null;
    }*/
}