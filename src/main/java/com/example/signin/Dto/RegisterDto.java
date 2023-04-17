package com.example.signin.Dto;

import lombok.*;

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
}
