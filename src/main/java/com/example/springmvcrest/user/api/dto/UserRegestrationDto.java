package com.example.springmvcrest.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegestrationDto  {
    private Long id;
    private String email;
    private String userName;
    private String response;

}
