package my.android.client.repository;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.Conversation;
import my.android.client.model.User;

public class ConversationRepository {

    public List<Conversation> findAll() {
        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation = new Conversation();
        conversation.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "user1"));
        users.add(new User("user2", "user1"));
        conversation.setUsers(users);

        Conversation conversation1 = new Conversation();
        conversation.setId(2L);
        List<User> users1 = new ArrayList<>();
        users1.add(new User("user3", "user3"));
        users1.add(new User("user1", "user1"));
        conversation1.setUsers(users1);

        conversations.add(conversation);
        conversations.add(conversation1);

        return conversations;
    }
}
