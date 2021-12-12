package service;

import common.Messages;
import entity.User;
import exception.BadRequestException;
import repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {

        User user = null;

        try {
            user = userRepository.findByUsername(username);
        } catch(BadRequestException e) {
            throw new BadRequestException(Messages.USER_NOT_FOUND_USERNAME, username);
        }

        return user;
    }
}
