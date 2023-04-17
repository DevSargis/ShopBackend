package com.example.signin.Service;

import com.example.signin.Dto.RegisterDto;
import com.example.signin.Entities.User;
import com.example.signin.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String save(RegisterDto registerDto) {
        User user = User.builder().
                firstName(registerDto.getFirstName()).
                lastName(registerDto.getLastName()).
                email(registerDto.getEmail()).
                password(passwordEncoder.encode(registerDto.getPassword())).
                build();
        usersRepository.save(user);
        return user.getFirstName() + " " + user.getLastName();
    }
}
