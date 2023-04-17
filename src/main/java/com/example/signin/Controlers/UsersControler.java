package com.example.signin.Controlers;

import com.example.signin.Dto.RegisterDto;
import com.example.signin.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UsersControler {

    @Autowired
    UsersService usersService;

    @PostMapping(path = "/save")
    public String saveUser(@RequestBody RegisterDto registerDto){
        return usersService.save(registerDto);
    }
}
