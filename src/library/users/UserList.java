package library.users;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Changers;

import java.util.ArrayList;

public class UserList implements Changers {
    private static UserList INSTANCE;

    // singleton design pattern
    private ArrayList<User> userList = null;

    private UserList() {
        userList = new ArrayList<User>();
    }

    public static UserList getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new UserList();
        }
        return INSTANCE;
    }


    public void addToArray(User user) {
        userList.add(user);
    }

    public void removeFromArray(User user) {
        userList.remove(user);
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", listToJson());
        return json;
    }

    // EFFECTS: returns tasks in this todolist as a JSON array

    private JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();

        for (User l : INSTANCE.getUserList()) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }
}
