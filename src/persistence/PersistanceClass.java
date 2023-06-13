package persistence;

import library.staff.LibrarianList;
import library.users.UserList;
import library.tools.Database;
import org.json.JSONObject;

public class PersistanceClass implements Changers{
    private static PersistanceClass INSTANCE;

    // singleton design pattern

    private PersistanceClass() {

    }

    public static PersistanceClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersistanceClass();
        }
        return INSTANCE;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("usersJSON", UserList.getInstance().toJson());
        json.put("librariansJSON", LibrarianList.getInstance().toJson());
        json.put("nodesJSON", Database.getInstance().toJson());
        return json;
    }
}
