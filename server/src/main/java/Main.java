import dto.MessageDTO;
import entity.Conversation;
import entity.User;
import org.json.JSONObject;
import repository.ConnectionRepository;
import repository.ConversationRepository;
import repository.MessageRepository;
import repository.UserRepository;
import service.ConversationService;
import service.MessageService;
import service.UserService;

import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        /**
         * TODO
         * create EntityManager in every method and close it.
         * remove it from constructor and field
         *
         */
//        MessageRepository messageRepository = new MessageRepository();
//
//        List<Message> messages = messageRepository.findAllByConversationId(1L);
//
        UserRepository userRepository = new UserRepository();

//        Function<Long, User> sasa = ;
//        User user = new User();
        System.out.println(userRepository.find(1L) + "tuk");
        //        User user = new User();
//        user.setUsername("generic_user");
//        user.setPassword("$2a$10$n/O98js8FDXNpu3fcXLHBu7oHGw/46VETmyPSb7NEbyg2ck5ygDNq");
//        System.out.println(userRepository.find(1L));

//
//        User user = userService.findByUserName("first_user1");
//
//        ConnectionRepository connectionRepository = new ConnectionRepository();
//        List<User> users = connectionRepository.findAllByUserId(1L, ConnectionStatus.ACCEPTED.toString());
//
        ConversationRepository conversationRepository = new ConversationRepository();
        ConversationService conversationService = new ConversationService(conversationRepository);

        Long[] faker = new Long[2];
        faker[0] = 1L;
        faker[1] = 2L;

        JSONObject json = new JSONObject().put("receivers", faker);

        //        List<Conversation> conversations = conversationRepository.findAllByUserId(1L);

//        MessageRepository messageRepository = new MessageRepository();
//        MessageService messageService = new MessageService(messageRepository, userRepository);
//        List<MessageDTO> messageDTOS = messageService.findAllByConversationId(1L);


        System.out.println("yabadabadu");
        System.out.println("sasa");

    }
}
