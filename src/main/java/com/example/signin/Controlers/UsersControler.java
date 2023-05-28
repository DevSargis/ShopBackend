package com.example.signin.Controlers;

import com.example.signin.Config.UserAuthProvider;
import com.example.signin.Dto.AuthCredentialsDto;
import com.example.signin.Dto.RegisterDto;
import com.example.signin.Dto.UserDto;
import com.example.signin.Service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UsersControler {

    @Autowired
    UsersService usersService;

    private final UserAuthProvider userAuthenticationProvider;

    @PostMapping(path = "/save")
    public String saveUser(@RequestBody RegisterDto registerDto){
        return usersService.save(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody AuthCredentialsDto credentialsDto) {
        UserDto userDto = usersService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) {
        UserDto createdUser = usersService.register(registerDto);
        createdUser.setToken(userAuthenticationProvider.createToken(registerDto.getEmail()));
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/asd")
    public ResponseEntity<Boolean> asd(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        boolean s = false;
        String[] elements = header.split(" ");
        if(elements.length == 2 && "Bearer".equals(elements[0])){
            try {
                s = userAuthenticationProvider.validateToken(elements[1]).isAuthenticated();
            }catch (RuntimeException e){
                SecurityContextHolder.clearContext();
                throw e;
            }
        }
        return ResponseEntity.ok(s);
    }


}
