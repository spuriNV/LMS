package Item;

import library.users.User;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public abstract class item {

    private String title;
    private int copies;
    private int borrowed;
    private double isbn;

    private HashMap<String, String> history;

    private double cost;

    private String type;


    public item(String title, int copies, int borrowed, double isbn, double cost, String type) {

        this.title = title;
        this.copies = copies;
        this.borrowed = borrowed;

       this.isbn = isbn; // first 3 letters of author's last name, followed by first 5 letters of title's name

        this.cost = cost;
        this.history = new HashMap<>();
        this.type = type;
    }

    // REQUIRES: n/a
    // EFFECTS: returns cost field

    public double getCost() {
        return this.cost;
    }

    // REQUIRES: n/a
    // EFFECTS: cost parameter is set to cost field

    public void setCost(double cost) {
        this.cost= cost;
    }

    // REQUIRES: n/a
    // EFFECTS: returns title field
    public String getTitle() {
        return title;
    }

    // REQUIRES: n/a
    // EFFECTS: title parameter is set to title field

    public void setTitle(String title) {
        this.title = title;
    }

    // REQUIRES: n/a
    // EFFECTS: returns copies field
    public int getCopies() {
        return copies;
    }

    // REQUIRES: n/a
    // EFFECTS: copies parameter is set to copies field
    public void setCopies(int copies) {
        this.copies = copies;
    }

    // REQUIRES: n/a
    // EFFECTS: returns borrowed field
    public int getBorrowed() {
        return borrowed;
    }

    // REQUIRES: n/a
    // EFFECTS: borrowed parameter is set to borrowed field
    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    // REQUIRES: n/a
    // EFFECTS: returns isbn field
    public double getIsbn() {
        return isbn;
    }


    // REQUIRES: n/a
    // EFFECTS: isbn parameter is set to isbn field
    public void setIsbn(double isbn) {
        this.isbn = isbn;
    }

    // REQUIRES: n/a
    // EFFECTS: returns true if copies is greater than borrowed
    public boolean isAvailable() {

        if(getCopies() > getBorrowed())
            return true;
        return false;
    }

    // REQUIRES: n/a
    // EFFECTS: returns history field
    public HashMap<String, String> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, String> history) {
        this.history = history;
    }

    // REQUIRES: user parameter
    // MODIFIES: history hashmap parameter
    // EFFECTS: if user is not null, arraylist adds user

    public void addToHistory(User user, Date date) {
        if(user != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY");
            String stringDate = formatter.format(date);
            this.history.put(user.getUser_name(), stringDate);
        }
    }

    // REQUIRES: user parameter
    // MODIFIES: history hashmap parameter
    // EFFECTS: map removes user

    public void removeFromHistory(User user) {
        this.history.remove(user.getUser_name());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "item{" +
                "title='" + title + '\'' +
                ", copies=" + copies +
                ", borrowed=" + borrowed +
                ", isbn=" + isbn +
                ", history=" + history +
                ", cost=" + cost +
                '}';
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONObject map =  new JSONObject(history);

        json.put("title", title);
        json.put("copies", copies);
        json.put("borrowed", borrowed);
        json.put("isbn", isbn);
        json.put("history", map);
        json.put("cost", cost);
        return json;
    }

}
