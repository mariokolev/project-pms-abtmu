package service;

import entity.User;
import repository.UserRepository;

public class AuthenticationService {

    private static AuthenticationService instance = null;
    private User authenticatedUser = null;

    private AuthenticationService() {

    }

    public static AuthenticationService getInstance() {
        if (AuthenticationService.instance == null) {
            AuthenticationService.instance = new AuthenticationService();
        }

        return AuthenticationService.instance;
    }

    public User getLoggedUser() {
        return authenticatedUser;
    }

    public Boolean authenticate(String username, String password) throws Throwable {

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        try {
            this.authenticatedUser = userService.findByUsernameAndPassword(username, password);
            return true;
        } catch (IllegalArgumentException e) {

            System.out.println("Invalid username or password.");
        }

        return false;
    }
}
