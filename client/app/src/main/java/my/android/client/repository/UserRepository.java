package my.android.client.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.User;
import my.android.client.tcp.TCPClient;
import my.android.client.util.JSONConverter;

public class UserRepository {
    private JSONConverter jsonConverter = new JSONConverter();

    public User login(String username, String password) throws JSONException {
        TCPClient tcpClient = TCPClient.getInstance();
        JSONObject request = new JSONObject();
        List<Long> receivers = new ArrayList<>();
        receivers.add(1L);
        request
                .put("endpoint", "login")
                .put("action", "get")
                .put("senderId", 1L)
                .put("receivers", new JSONArray(receivers))
                .put("parameters", new JSONObject()
                        .put("username", username)
                        .put("password", password)
                );

        System.out.println(request.toString() + " tuk");
        tcpClient.sendMessage(request.toString() + "\n");
        return jsonConverter.convertJsonToUser(new JSONObject(tcpClient.receive()));
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "user1"));
        users.add(new User("user2", "user2"));
        return users;
    }

}
