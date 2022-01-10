package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matchPassword(String candidate, String password) {
        return BCrypt.checkpw(candidate, password);
    }
}
