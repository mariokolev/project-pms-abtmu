package my.android.client.repository;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.User;

public class UserRepository {

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "user1"));
        users.add(new User("user2", "user2"));
        return users;
    }

}
