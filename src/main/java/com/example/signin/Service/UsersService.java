package com.example.signin.Service;

import com.example.signin.Dto.AuthCredentialsDto;
import com.example.signin.Dto.RegisterDto;
import com.example.signin.Dto.UserDto;
import com.example.signin.Entities.User;
import com.example.signin.Exceptions.AppException;
import com.example.signin.Mappers.UserMapper;
import com.example.signin.Repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.CharBuffer;
import java.util.Optional;

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

    public UserDto findByEmail(String email) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return UserMapper.INSTANCE.toUserDto(user);
    }

    public UserDto register(RegisterDto registerDto) {
        Optional<User> optionalUser = usersRepository.findByEmail(registerDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        User user = UserMapper.INSTANCE.signUpToUser(registerDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));

        User savedUser = usersRepository.save(user);

        return UserMapper.INSTANCE.toUserDto(savedUser);
    }

    public UserDto login(AuthCredentialsDto credentialsDto) {
        User user = usersRepository.findByEmail(credentialsDto.getUserLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getUserPassword()), user.getPassword())) {
            return UserMapper.INSTANCE.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
