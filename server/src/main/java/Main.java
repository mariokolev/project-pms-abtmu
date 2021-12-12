import common.ConnectionStatus;
import config.EntityManagerConfig;
import entity.Conversation;
import entity.Message;
import entity.User;
import repository.ConnectionRepository;
import repository.ConversationRepository;
import repository.MessageRepository;
import repository.UserRepository;
import service.UserService;

import javax.persistence.EntityManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /**
         * TODO
         * create EntityManager in every method and close it.
         * remove it from constructor and field
         *
         */
        MessageRepository messageRepository = new MessageRepository();

        List<Message> messages = messageRepository.findAllByConversationId(1L);

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        User user = userService.findByUserName("first_user1");

        ConnectionRepository connectionRepository = new ConnectionRepository();
        List<User> users = connectionRepository.findAllByUserId(1L, ConnectionStatus.ACCEPTED.toString());

        ConversationRepository conversationRepository = new ConversationRepository();
        List<Conversation> conversations = conversationRepository.findAllByUserId(1L);


        System.out.println("yabadabadu");

    }
}
