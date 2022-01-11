package my.android.client.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.Message;
import my.android.client.tcp.TCPClient;
import my.android.client.util.JSONConverter;

public class MessageRepository {

    private JSONConverter jsonConverter = new JSONConverter();


    public List<Message> findAllByConversationId(Long senderId, Long receiverId, Long id) throws JSONException {
        TCPClient tcpClient = TCPClient.getInstance();
        JSONObject request = new JSONObject();
        List<Long> receivers = new ArrayList<>();
//        receivers.add(receiverId);
        receivers.add(senderId);
        request
                .put("endpoint", "message")
                .put("action", "get")
                .put("senderId", senderId)
                .put("receivers", new JSONArray(receivers))
                .put("parameters", new JSONObject()
                        .put("conversationId", id)
                        .put("senderId", senderId)
                );

        tcpClient.sendMessage(request.toString() + "\n");

        return jsonConverter.convertJsonToMessages(new JSONObject(tcpClient.receive()));
    }

    public Message save(Message message, Long receiverId) throws JSONException {
        TCPClient tcpClient = TCPClient.getInstance();
        JSONObject request = new JSONObject();
        List<Long> receivers = new ArrayList<>();
        receivers.add(receiverId);
        receivers.add(message.getSenderId());
        request
                .put("endpoint", "message")
                .put("action", "add")
                .put("senderId", message.getSenderId())
                .put("receivers", new JSONArray(receivers))
                .put("parameters", new JSONObject()
                        .put("conversationId", message.getConversationId())
                        .put("senderId", message.getSenderId())
                        .put("messageBody", message.getBody())
                        .put("isMedia", false)
                );

        tcpClient.sendMessage(request.toString() + "\n");
        String received = tcpClient.receive();
        System.out.println("Repository received: " + received);

        JSONObject jsonMessage = new JSONObject(received);
        return jsonConverter.convertJsonToMessage(jsonMessage);
    }
}
