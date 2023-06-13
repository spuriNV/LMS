package Item;

import org.json.JSONObject;

public class Ebook extends item {
    private String author;
    private double pages;

    public Ebook(String title, int copies, int borrowed, double isbn, String author, double pages, double cost, String type) {
        super(title, copies, borrowed, isbn, cost, type);
        this.author = author;
        this.pages = pages;
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
    // EFFECTS: returns pages field
    public double getPages() {
        return pages;
    }

    // REQUIRES: n/a
    // MODIFIES: pages field
    // EFFECTS: pages parameter is set to pages field

    public void setPages(double pages) {
        this.pages = pages;
    }

    // REQUIRES: n/a
    // EFFECTS: outputs some fields

    @Override
    public String toString() {
        String txt = "";
        txt += "Book ~~\n";
        txt += "title: " + getTitle() + "\n";
        txt += "author: " + getAuthor() + "\n";
        txt += "pages: " + getPages() + "\n";
        txt += "copies: " + getCopies() + "\n";

        if(isAvailable() == true)
            txt += "Book is Available\n";
        else
            txt += "Book is Available\n";
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
        json.put("pages/time", pages);
        json.put("type", super.getType());
        return json;
    }


}
