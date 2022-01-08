package util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class JSONConverter<T> {

    public JSONObject convert(T object) {
        return new JSONObject(object);
    }

    public JSONObject convert(String key, T object) {
        return new JSONObject().put(key, object);
    }

    public HashMap<String, Object> turnJSONObjectToHashMap(JSONObject json) {
        HashMap<String, Object> parameters = new HashMap<>();
        Iterator<String> iterator = json.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            parameters.put(key, json.get(key));
        }

        return parameters;
    }
}
