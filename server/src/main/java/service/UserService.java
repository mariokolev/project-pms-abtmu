package service;

import common.Messages;
import entity.User;
import exception.BadRequestException;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsernameAndPassword(String username, String password) {
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new BadRequestException(Messages.INVALID_USERNAME_PASSWORD);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(Messages.INVALID_USERNAME_PASSWORD));

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new BadRequestException(Messages.INVALID_USERNAME_PASSWORD);
        }

        return user;
    }
}
