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
        JSONArray conversationsJSONArray = json.getJSONArray("conversations");

        for (int jsonConversatino = 0; jsonConversatino < conversationsJSONArray.length(); jsonConversatino++) {
            JSONObject conversationsJson = conversationsJSONArray.getJSONObject(jsonConversatino);
            Conversation conversation = new Conversation();
            conversation.setId(conversationsJson.getLong("id"));

            JSONArray jsonUsers = new JSONArray(conversationsJson.getString("users"));

            List<User> users = new ArrayList<>();
            for (int jsonUser = 0; jsonUser < jsonUsers.length(); jsonUser++) {
                User user = convertJsonToUser(jsonUsers.getJSONObject(jsonUser));
                users.add(user);
            }
            conversation.setUsers(users);
            conversations.add(conversation);
        }

        for (int i = 0; i < conversations.size(); i++) {
            for (int j = 0; j < conversations.get(i).getUsers().size(); j++) {
                System.out.println(conversations.get(i).getUsers().get(j).getUsername() );
            }
        }
        return conversations;
    }
}
