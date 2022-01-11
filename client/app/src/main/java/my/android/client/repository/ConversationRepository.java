package my.android.client.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.Conversation;
import my.android.client.tcp.TCPClient;
import my.android.client.util.JSONConverter;

public class ConversationRepository {

    private JSONConverter jsonConverter = new JSONConverter();

    public List<Conversation> findAllByUserId(Long id) throws JSONException {
        TCPClient tcpClient = TCPClient.getInstance();
        JSONObject request = new JSONObject();
        List<Long> receivers = new ArrayList<>();

        System.out.println("CONVERSATION RECEIVER ID: " + id);
        receivers.add(id);
        request
                .put("endpoint", "conversation")
                .put("action", "get")
                .put("senderId", id)
                .put("receivers", new JSONArray(receivers))
                .put("parameters", new JSONObject()
                        .put("userId", id)
                );

        tcpClient.sendMessage(request.toString() + "\n");

        return jsonConverter.convertJsonToConversations(new JSONObject(tcpClient.receive()));
    }
}
