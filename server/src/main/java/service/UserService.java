package service;

import entity.User;
import exception.BadRequestException;
import repository.UserRepository;

public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) throws BadRequestException{
        return userRepository.findByUsername(username);
    }

    public User find(Long id) {
        return userRepository.find(id);
    }
}
