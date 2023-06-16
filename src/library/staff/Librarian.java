package library.staff;

import library.tools.Database;
import library.tools.HashNode;
import org.json.JSONObject;
import persistence.Changers;

public class Librarian extends Staff implements Changers {
    private String telephone;
    private String user_name;
    private String birthdate;



    public Librarian(String user_name) {
        super(user_name);
    }

    // REQUIRES: key and value parameters
    // MODIFIES: hashtable field
    // EFFECTS: hashtable adds key and value

    public void addToDatabase(HashNode node) {
        Database base = Database.getInstance();
        base.add(node);
        notifyObserversItem();
    }

    // REQUIRES: key parameter
    // MODIFIES: hashtable field
    // EFFECTS: hashtable removes key

    public void removeFromDatabase(double key) {
        Database base = Database.getInstance();
        base.remove(key);
    }

    @Override
    public String toString() {
        return "Librarian{" +
                ", telephone='" + telephone + '\'' +
                ", user_name='" + super.getUser_name() + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }

    // update book info

    /*
    -Librarian

        + staff stuff
        can add new book
        can delete a book
        can update book info
     */

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", super.getUser_name());
        json.put("birthdate", super.getBirthdate());
        json.put("telephone", super.getTelephone());
        return json;
    }
}
