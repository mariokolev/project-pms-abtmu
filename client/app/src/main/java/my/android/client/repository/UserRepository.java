package my.android.client.repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.User;
import my.android.client.tcp.TCPClient;

public class UserRepository {

    public User login(String username, String password) throws JSONException {
        TCPClient tcpClient = TCPClient.getInstance();
        JSONObject request = new JSONObject();
        request.put("username", username)
                .put("password", password);
        tcpClient.sendMessage(request.toString() + "\n");
        tcpClient.receive();
        User user = new User();
        return user;
    }
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "user1"));
        users.add(new User("user2", "user2"));
        return users;
    }

}
