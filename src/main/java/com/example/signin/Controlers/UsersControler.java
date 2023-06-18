package com.example.signin.Controlers;

import com.example.signin.Config.EmailConfiguration;
import com.example.signin.Config.UserAuthProvider;
import com.example.signin.Dto.AuthCredentialsDto;
import com.example.signin.Dto.RegisterDto;
import com.example.signin.Dto.UserDto;
import com.example.signin.Enums.Role;
import com.example.signin.Responses.ServerResponse;
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

    @Autowired
    EmailConfiguration emailConfiguration;

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
    public ResponseEntity<ServerResponse<UserDto>> register(@RequestBody RegisterDto registerDto) {
        try {
            UserDto createdUser = usersService.register(registerDto, Role.USER);
            createdUser.setToken(userAuthenticationProvider.createToken(registerDto.getEmail()));
            return ResponseEntity.ok(ServerResponse.<UserDto>builder().data(createdUser).message("").build());
        } catch (RuntimeException ru) {
            throw ru;
        }
    }

    @GetMapping("/test")
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

    @PostMapping ("/superUser")
    public ResponseEntity<Boolean> createSuperUser(HttpServletRequest request) {
        usersService.register(RegisterDto.builder().
                firstName("Sargis").
                lastName("Margaryan").
                email("sargismargaryan1998@gmail.com").
                phoneNumber("+37477753508").
                password("test1234").build(), Role.ADMIN);
        usersService.register(RegisterDto.builder().
                firstName("Arayik").
                lastName("Petrosyan").
                email("arayik.petrosian@mail.ru").
                phoneNumber("+37477420241").
                password("test1234").build(), Role.ADMIN);
       return ResponseEntity.ok(true);
    }

    @PostMapping ("/asda")
    public boolean aasd() {

        emailConfiguration.sendMessage("saq@mailinator.com", "zxvc");
       return true;
    }






}
