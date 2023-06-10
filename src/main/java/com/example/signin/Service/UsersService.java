package com.example.signin.Service;

import com.example.signin.Config.EmailConfiguration;
import com.example.signin.Dto.AuthCredentialsDto;
import com.example.signin.Dto.RegisterDto;
import com.example.signin.Dto.UserDto;
import com.example.signin.Entities.MailVerifier;
import com.example.signin.Entities.User;
import com.example.signin.Enums.Role;
import com.example.signin.Exceptions.AppException;
import com.example.signin.Mappers.UserMapper;
import com.example.signin.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @Autowired
    EmailConfiguration emailConfiguration;

    @Autowired
    MailVerificationService mailVerificationService;

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

    public UserDto register(RegisterDto registerDto, Role roleUser) throws RuntimeException{
        Optional<User> optionalUser = usersRepository.findByEmail(registerDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        User user = UserMapper.INSTANCE.signUpToUser(registerDto);
        user.setCreatedAt(registerDto.getCreatedAt());
        user.setRoleId(roleService.getRole(roleUser.getValue()));
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));
        User savedUser = usersRepository.save(user);
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase(Locale.ROOT);
        mailVerificationService.save(MailVerifier.builder().user(savedUser).Code(code).createdAt(new Date().getTime()).build());
        emailConfiguration.sendMessage(savedUser.getEmail(),
                "                    Welcome to ArSaKa                 \n\n" +
                        "Your code for verification " + code
        );
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
