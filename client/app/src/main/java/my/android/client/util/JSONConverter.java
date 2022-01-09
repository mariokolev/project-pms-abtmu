package my.android.client.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter {

    public JSONObject convert(String message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("endpoint", "login")
                .put("action", "get")
                .put("senderId", 1L)
//                .put("receivers")
                .put("paramateres", new JSONObject()
                .put("username", "sasa")
                .put("password", "sasa"));

        return json;
    }
}
