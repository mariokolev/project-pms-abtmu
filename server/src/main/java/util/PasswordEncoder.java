package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
