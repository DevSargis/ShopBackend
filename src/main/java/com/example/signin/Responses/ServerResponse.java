package com.example.signin.Responses;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ServerResponse<T> {
    T data;
    String message;
    int status = 200;
}
