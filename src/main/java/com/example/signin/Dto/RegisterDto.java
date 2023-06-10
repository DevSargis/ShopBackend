package com.example.signin.Dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private Long createdAt = new Date().getTime();
}
