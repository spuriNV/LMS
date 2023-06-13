package Item;

import org.json.JSONObject;

public class DVD extends item {

    private String director;

    private double time;

    public DVD(String title, int copies, int borrowed, double isbn, String director, double time, double cost, String type) {
        super(title, copies, borrowed, isbn, cost, type);
        this.director = director;
        this.time = time;
    }

    // REQUIRES: n/a
    // EFFECTS: returns director field
    public String getDirector() {
        return director;
    }

    // REQUIRES: n/a
    // MODIFIES: director field
    // EFFECTS: director parameter is set to director field

    public void setDirector(String director) {
        this.director = director;
    }

    // REQUIRES: n/a
    // EFFECTS: returns length field
    public double getTime() {
        return time;
    }

    // REQUIRES: n/a
    // MODIFIES: length field
    // EFFECTS: length parameter is set to length field

    public void setTime(int time) {
        this.time = time;
    }

    // REQUIRES: n/a
    // EFFECTS: outputs some fields

    @Override
    public String toString() {
        String txt = "";
        txt += "DVD ~~\n";
        txt += "title: " + getTitle() + "\n";
        txt += "director: " + getDirector() + "\n";
        txt += "movie length (min): " + getTime() + "\n";
        txt += "copies: " + getCopies() + "\n";

        if(isAvailable() == true)
            txt += "Movie is Available\n";
        else
            txt += "Movie is not Available\n";
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
        json.put("author/director", director);
        json.put("pages/time", time);
        json.put("type", super.getType());
        return json;
    }
}
