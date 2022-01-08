package tcp;

import dto.MessageDTO;
import exception.BadRequestException;
import org.json.JSONObject;
import org.json.JSONStringer;
import repository.ConversationRepository;
import repository.MessageRepository;
import repository.UserRepository;
import service.ConversationService;
import service.LoginService;
import service.MessageService;
import service.UserService;
import util.JSONConverter;

import java.util.HashMap;
import java.util.Iterator;

public class Dispatcher {

    private String endpoint;
    private String action;
    private HashMap<String, Object> parameters;
    private JSONConverter jsonConverter;

    public Dispatcher(String stream) {
        JSONObject obj = new JSONObject(stream);
        this.endpoint = obj.getString("endpoint");
        this.action = obj.getString("action");    // get, add, update
        jsonConverter = new JSONConverter();

        this.parameters = jsonConverter.turnJSONObjectToHashMap(obj.getJSONObject("parameters")); // connection, message, user

    }

    public JSONObject dispatch() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        ConversationRepository conversationRepository = new ConversationRepository();
        ConversationService conversationService = new ConversationService(conversationRepository);
        MessageRepository messageRepository = new MessageRepository();
        MessageService messageService = new MessageService(messageRepository, userService, conversationService);
        LoginService loginService = new LoginService(new UserService(new UserRepository()));

        switch (endpoint) {
            case "message":

                switch (action) {
                    case "add":
                        MessageDTO messageDTO = new MessageDTO(parameters);
                        return jsonConverter.convert(messageService.save(messageDTO));
                    case "get":
                        return jsonConverter.convert("messages", messageService.findAllByConversationId(Long.parseLong(JSONStringer.valueToString(parameters.get("conversation_id")))));
                }

                break;
            case "conversation":

                switch (action) {
                    case "add":
                        break;
                    case "get":
                        return jsonConverter.convert("conversations", conversationService.findAllByUserId(Long.parseLong(JSONStringer.valueToString(parameters.get("user_id")))));
                }

            case "connection":
                // ConnectionService(action, parameters) {}
                break;

            case "login":

                switch (action) {
                    case "add":
                        break;
                    case "get":
                        return jsonConverter.convert(loginService.login(parameters));
                }
                break;
        }

        System.out.println(endpoint);
        System.out.println(action);
        return null;
    }
}
