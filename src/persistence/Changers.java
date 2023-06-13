package persistence;

import org.json.JSONObject;

public interface Changers {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
