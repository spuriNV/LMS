package Item;

import org.json.JSONObject;

public class Audiobook extends item {
    private String author;
    private double time_mins;

    public Audiobook(String title, int copies, int borrowed, double isbn, String author, double time, double cost, String type) {
        super(title, copies, borrowed, isbn, cost, type);
        this.author = author;
        this.time_mins = time;
    }

    // REQUIRES: n/a
    // EFFECTS: returns author field
    public String getAuthor() {
        return author;
    }

    // REQUIRES: n/a
    // MODIFIES: author field
    // EFFECTS: author parameter is set to author field

    public void setAuthor(String author) {
        this.author = author;
    }

    // REQUIRES: n/a
    // EFFECTS: returns time_mins field

    public double getTime() {
        return time_mins;
    }

    // REQUIRES: n/a
    // MODIFIES: time_mins field
    // EFFECTS: time_mins parameter is set to time_mins field

    public void setTime(double time_mins) {
        this.time_mins = time_mins;
    }

    // REQUIRES: n/a
    // EFFECTS: outputs values of some fields

    @Override
    public String toString() {
            String txt = "";
            txt += "Audiobook ~~\n";
            txt += "title: " + getTitle() + "\n";
            txt += "author: " + getAuthor() + "\n";
            txt += "pages: " + getTime() + "\n";
            txt += "copies: " + getCopies() + "\n";

            if(isAvailable() == true)
                txt += "Book is Available\n";
            else
                txt += "Book is not Available\n";
            return txt;

        }

        public JSONObject toJson() {
            JSONObject json = new JSONObject();
            JSONObject map =  new JSONObject(super.getHistory());

            json.put("title", super.getTitle());
            json.put("copies", super.getCopies());
            json.put("borrowed", super.getBorrowed());
            json.put("isbn", super.getIsbn());
            json.put("history", map);
            json.put("cost", super.getCost());
            json.put("author/director", author);
            json.put("pages/time", time_mins);
            json.put("type", super.getType());
            return json;
        }
    }

