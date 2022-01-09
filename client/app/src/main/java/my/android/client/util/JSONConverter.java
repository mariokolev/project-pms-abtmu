package my.android.client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.Conversation;
import my.android.client.model.User;

public class JSONConverter {

    public User convertJsonToUser(JSONObject json) throws JSONException {
        User user = new User();
        user.setId(json.getLong("id"));
        user.setUsername(json.getString("username"));
        return user;
    }

    public List<Conversation> convertJsonToConversations(JSONObject json) throws JSONException {
        List<Conversation> conversations  = new ArrayList<>();
        JSONArray jsonArray = json.getJSONArray("conversations");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject conversationsJson = jsonArray.getJSONObject(i);
            Conversation conversation = new Conversation();
            conversation.setId(conversationsJson.getLong("id"));

            JSONArray jsonUsers = new JSONArray(conversationsJson.getString("users"));

            List<User> users = new ArrayList<>();
            for (int j = 0; j < jsonUsers.length(); j++) {
                User user = convertJsonToUser(jsonUsers.getJSONObject(i));
                users.add(user);
            }
            conversation.setUsers(users);
            conversations.add(conversation);
        }
        return conversations;
    }
}
