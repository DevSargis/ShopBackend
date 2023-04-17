package com.example.signin.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthCredentialsDto {
    private String userLogin;
    private String userPassword;
}
