package library.staff;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

public class LibrarianList {
    private static LibrarianList INSTANCE;

    // singleton design pattern
    private ArrayList<Librarian> librarianList = null;

    private LibrarianList() {librarianList = new ArrayList<Librarian>();
    }

    public static LibrarianList getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new LibrarianList();
        }
        return INSTANCE;
    }


    public void addToArray(Librarian librarian) {
        librarianList.add(librarian);
    }

    public void removeFromArray(Librarian librarian) {librarianList.remove(librarian);
    }

    public ArrayList<Librarian> getLibrarianList() {
        return librarianList;
    }

    public void setLibrarianList(ArrayList<Librarian> librarianList) {
        this.librarianList = librarianList;
    }

    public void emptyList() {
        for(int i = 0; i < librarianList.size(); i++) {
            librarianList.remove(i);
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("librarians", listToJson());
        return json;
    }

    // EFFECTS: returns tasks in this todolist as a JSON array

    private JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Librarian l : INSTANCE.getLibrarianList()) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }
}
