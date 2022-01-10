package service;

import dto.UserDTO;
import entity.User;
import exception.BadRequestException;
import mapper.UserMapper;
import util.PasswordEncoder;

import java.util.HashMap;

public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public UserDTO login(HashMap<String, Object> parameters) {
        User user = null;

        try {
            user = userService.findByUserName((String) parameters.get("username"));
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }

        if (PasswordEncoder.matchPassword((String) parameters.get("password"), user.getPassword())) {
            return UserMapper.convertEntityToDto(user);
        } else {
            throw new BadRequestException("Wrong credentials, try again.");
        }
    }
}
