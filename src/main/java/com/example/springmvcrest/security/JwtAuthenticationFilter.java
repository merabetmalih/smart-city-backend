package com.example.springmvcrest.security;

import com.example.springmvcrest.security.JwtProvider;
import com.example.springmvcrest.security.User;
import com.example.springmvcrest.security.UserDetailsImp;
import com.example.springmvcrest.user.api.dto.UserLoginDto;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.example.springmvcrest.security.SecurityUtils.*;
import static com.example.springmvcrest.security.SecurityUtils.TOKE_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private Gson gson = new Gson();


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider,String authUrl) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(authUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getParameter("email"),
                request.getParameter("password")));

          /* try {
               UserDto creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassWord()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User credentials = ((User) authResult.getPrincipal());

        UserDetailsImp userDetails = new UserDetailsImp();
        userDetails.setUsername(credentials.getUsername());
        String token = jwtProvider.generateToken(userDetails);
        response.setStatus(HttpStatus.OK.value());
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader(TOKEN_HEADER, TOKE_PREFIX + token);

        //System.out.println(credentials.getUsername());
        try {
            UserLoginDto userLoginDto =new UserLoginDto(credentials.getId(),credentials.getUsername(),"successfully login",TOKE_PREFIX + token);
            String userLoginDtoString = this.gson.toJson(userLoginDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(userLoginDtoString);
            out.flush();
        } catch (IOException ex) {
            logger.error("can't print response token");
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
