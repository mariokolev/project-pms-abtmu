package tcp;

import dto.ConversationDTO;
import dto.MessageDTO;
import exception.BadRequestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import repository.ConnectionRepository;
import repository.ConversationRepository;
import repository.MessageRepository;
import repository.UserRepository;
import service.*;
import util.JSONConverter;

import java.util.HashMap;
import java.util.logging.Logger;

public class Dispatcher {

    private String endpoint;
    private String action;
    private HashMap<String, Object> parameters;
    // Id of user making request
    private Long senderId;
    private JSONConverter jsonConverter;
    private final Logger logger = Logger.getLogger(Dispatcher.class.getName());

    public Dispatcher(String stream) {
        try {
            JSONObject obj = new JSONObject(stream);
            this.endpoint = obj.getString("endpoint");
            this.action = obj.getString("action");
            this.senderId = obj.getLong("senderId");
            jsonConverter = new JSONConverter();
            this.parameters = jsonConverter.turnJSONObjectToHashMap(obj.getJSONObject("parameters"));
        } catch (JSONException e) {
            logger.info(e.toString());
            throw e;
        }
    }

    public JSONObject dispatch() throws BadRequestException {

        logger.info(String.format("[endpoint]: %s", endpoint));
        logger.info(String.format("[action]: %s", action));
        logger.info(String.format("[parameters]: %s", parameters));

        // Inject Dependencies
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        ConnectionRepository connectionRepository = new ConnectionRepository();
        ConversationRepository conversationRepository = new ConversationRepository();
        ConversationService conversationService = new ConversationService(conversationRepository);
        MessageRepository messageRepository = new MessageRepository();
        MessageService messageService = new MessageService(messageRepository, userService, conversationService);
        LoginService loginService = new LoginService(new UserService(new UserRepository()));
        ConnectionService connectionService = new ConnectionService(connectionRepository);

        switch (endpoint) {
            case "message":
                MessageDTO messageDTO = new MessageDTO(parameters);
                switch (action) {
                    case "add":
                        return jsonConverter.convert(messageService.save(messageDTO));
                    case "get":
                        return jsonConverter.convert("messages", messageService.findAllByConversationId(Long.parseLong(JSONStringer.valueToString(parameters.get("conversationId")))));
                    case "edit":
                        return jsonConverter.convert("messages", messageService.update(messageDTO));
                }

                break;

            case "conversation":
                ConversationDTO conversationDTO = new ConversationDTO(parameters);
                switch (action) {
                    case "add":
                        break;
                    case "get":
                        return jsonConverter.convert("conversations", conversationService.findAllByUserId(Long.parseLong(JSONStringer.valueToString(parameters.get("userId")))));
                }

            case "connection":
                switch (action) {
                    case "add":
                        return jsonConverter.convert("connections", connectionService.save(parameters));
                    case "get":
                        // Send user id and connection status @see ConnectionStatus
                        return jsonConverter.convert("connections", connectionService.findAllByUserIdAndStatus(
                                Long.parseLong(JSONStringer.valueToString(parameters.get("userId"))),
                                (String) parameters.get("connectionStatus"))
                        );
                    case "edit":
                        return jsonConverter.convert(connectionService.update(parameters, senderId));
                }
                break;

            case "login":
                switch (action) {
                    case "get":
                        return jsonConverter.convert(loginService.login(parameters));
                }
                break;
        }

        return null;
    }
}
