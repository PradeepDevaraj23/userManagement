package com.prdtech.com.usermanagement.service;

import com.prdtech.com.usermanagement.dto.UserDto;
import com.prdtech.com.usermanagement.exceptionHandling.ResourceNotFoundException;
import com.prdtech.com.usermanagement.model.User;
import com.prdtech.com.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto) {

        User user = toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.password()));
        return toUserDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUserDto).toList();
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(this::toUserDto).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    public UserDto updateUser(UserDto userDto) {
      User user = userRepository.findById(userDto.id()).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        if(!userDto.userName().isBlank() && !userDto.userName().equals(user.getUserName())){
           throw new RuntimeException("User Name Cannot be Changed");
        }
      if(!userDto.email().isBlank() && !userDto.email().equals(user.getEmail())){
          user.setEmail(userDto.email());
      }
         return toUserDto(userRepository.save(user));
    }


    public String deleteUserById(Long id) {
        return userRepository.findById(id).map(e -> {
            userRepository.delete(e);
            return "user is deleted";
        }).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }
    private User toUser(UserDto userDto ){
        log.info("the value of record {}",userDto);
        return new User(userDto.id(),userDto.userName(),userDto.email(),userDto.password());
    }

    private UserDto toUserDto(User user){
        log.info("the value of entity {}",user);
        return new UserDto(user.getId(),user.getUserName(),user.getEmail(),user.getPassword());
    }
}
