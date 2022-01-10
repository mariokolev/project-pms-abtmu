package my.android.client.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import my.android.client.model.Message;
import my.android.client.tcp.TCPClient;

public class MessageRepository {

    public List<Message> findAllByConversationId(Long senderId, Long receiverId, Long id) throws JSONException {
        TCPClient tcpClient = TCPClient.getInstance();
        JSONObject request = new JSONObject();
        List<Long> receivers = new ArrayList<>();
        receivers.add(id);
        request
                .put("endpoint", "message")
                .put("action", "get")
                .put("senderId", )
                .put("receivers", new JSONArray(receivers))
                .put("parameters", new JSONObject()
                        .put("userId", id)
                );

        tcpClient.sendMessage(request.toString() + "\n");
        return null;
    }
}
