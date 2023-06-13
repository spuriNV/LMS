package persistence;

import Item.Audiobook;
import Item.Book;
import Item.DVD;
import Item.Ebook;
import com.google.gson.Gson;
import library.staff.Librarian;
import library.tools.Database;
import library.tools.HashNode;
import library.users.Loan;
import library.users.LoanList;
import library.users.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<User> readUserList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject json = jsonObject.getJSONObject("usersJSON");
        return parseUserList(json);
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private ArrayList<User> parseUserList(JSONObject jsonObject) {
        ArrayList<User> list = new ArrayList<>();
        addUsers(list, jsonObject);
        return list;
    }

    public ArrayList<Librarian> readLibrarianList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject json = jsonObject.getJSONObject("librariansJSON");
        return parseLibrarianList(json);
    }

    private ArrayList<Librarian> parseLibrarianList(JSONObject jsonObject) {
        ArrayList<Librarian> list = new ArrayList<>();
        addLibrarians(list, jsonObject);
        return list;
    }

    public Database readBaseList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject json = jsonObject.getJSONObject("nodesJSON");
        return parseBaseList(json);
    }


    private Database parseBaseList(JSONObject jsonObject) {
        Database base = Database.getInstance();
        addNodes(base, jsonObject);
        return base;
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }



    // MODIFIES: tl
    // EFFECTS: parses tasks from JSON object and adds them to todolist
    private void addUsers(ArrayList<User> list, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(list, nextUser);
        }
    }

    // MODIFIES: tl
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addUser(ArrayList<User> list, JSONObject jsonObject) {
        String user_name = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String telephone = jsonObject.getString("telephone");
        double fineStatus = jsonObject.getDouble("fineStatus");
        JSONObject loansList = jsonObject.getJSONObject("Item Loans");


        // parse loansList
        ArrayList<Loan> loans = new ArrayList<>();
        JSONArray jsonArray = loansList.getJSONArray("loansList");
        for(Object json : jsonArray) {
            JSONObject nextLoan = (JSONObject) json;
            addLoan(loans, nextLoan);
        }
        LoanList loansList1 = new LoanList(loans);


        User user = new User(user_name, password, loansList1);
        user.setTelephone(telephone);
        user.setFineStatus(fineStatus);
        list.add(user);
    }

    private void addLoan(ArrayList<Loan> list, JSONObject jsonObject) {
        String issue_date = jsonObject.getString("issue_date");
        String return_date = jsonObject.getString("return_date");
        JSONObject node = jsonObject.getJSONObject("borrowed_item");
        boolean isLate = jsonObject.getBoolean("late_status");



        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date issueDate;
        Date returnDate;
        try {
            issueDate = formatter.parse(issue_date);
            returnDate = formatter.parse(issue_date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        String title = node.getString("title");
        int copies = node.getInt("copies");
        int borrowed = node.getInt("borrowed");
        double isbn = node.getDouble("isbn");
        String author = node.getString("author/director");
        double pages = node.getDouble("pages/time");
        double cost = node.getDouble("cost");
        String type = node.getString("type");
        JSONObject map = node.getJSONObject("history");

        HashMap<String, String> yourHashMap = new Gson().fromJson(String.valueOf(map), HashMap.class);

        if(type.equals("audiobook")) {
            Audiobook audiobook = new Audiobook(title, copies, borrowed, isbn, author, pages, cost, type);
            audiobook.setHistory(yourHashMap);
            Loan newLoan = new Loan(issueDate, audiobook, isLate);
            list.add(newLoan);
        }
        else if(type.equals("book")) {
            Book book = new Book(title, copies, borrowed, isbn, author, pages, cost, type);
            book.setHistory(yourHashMap);
           Loan newLoan = new Loan(issueDate, book, isLate);
            list.add(newLoan);
        }
        else if(type.equals("ebook")) {
            Ebook ebook = new Ebook(title, copies, borrowed, isbn, author, pages, cost, type);
            ebook.setHistory(yourHashMap);
            Loan newLoan = new Loan(issueDate, ebook, isLate);
            list.add(newLoan);
        }
        else if(type.equals("DVD")) {
            DVD dvd = new DVD(title, copies, borrowed, isbn, author, pages, cost, type);
            dvd.setHistory(yourHashMap);
            Loan newLoan = new Loan(issueDate, dvd, isLate);
            list.add(newLoan);
        }

    }

    private void addLibrarians(ArrayList<Librarian> list, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("librarians");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addLibrarian(list, nextUser);
        }
    }

    private void addLibrarian(ArrayList<Librarian> list, JSONObject jsonObject) {
        String user_name = jsonObject.getString("username");
        String telephone = jsonObject.getString("telephone");
        String birthdate = jsonObject.getString("birthdate");


        Librarian librarian = new Librarian(user_name);
        librarian.setBirthdate(birthdate);
        librarian.setTelephone(telephone);
        list.add(librarian);
    }

    private void addNodes(Database list, JSONObject jsonObject) { // diff structure
        JSONArray jsonArray = jsonObject.getJSONArray("nodes");
        for (Object json : jsonArray) {
            JSONObject nextNode = (JSONObject) json;
            addNode(list, nextNode);
        }
    }

    private void addNode(Database list, JSONObject jsonObject) {
        double key = jsonObject.getDouble("keyBox");
        String value = jsonObject.getString("value");
        JSONObject node = jsonObject.getJSONObject("Item");

        String title = node.getString("title");
        int copies = node.getInt("copies");
        int borrowed = node.getInt("borrowed");
        double isbn = node.getDouble("isbn");
        String author = node.getString("author/director");
        double pages = node.getDouble("pages/time");
        double cost = node.getDouble("cost");
        String type = node.getString("type");
        JSONObject map = node.getJSONObject("history");

        HashMap<String, String> yourHashMap = new Gson().fromJson(String.valueOf(map), HashMap.class);


        if(type.equals("audiobook")) {
            Audiobook audiobook = new Audiobook(title, copies, borrowed, isbn, author, pages, cost, type);
            audiobook.setHistory(yourHashMap);
            HashNode thisNode = new HashNode(key, value);
            thisNode.setItem(audiobook);
            list.add(thisNode);
        }
        else if(type.equals("book")) {
            Book book = new Book(title, copies, borrowed, isbn, author, pages, cost, type);
            book.setHistory(yourHashMap);
            HashNode thisNode = new HashNode(key, value);
            thisNode.setItem(book);
            list.add(thisNode);
        }
        else if(type.equals("ebook")) {
            Ebook ebook = new Ebook(title, copies, borrowed, isbn, author, pages, cost, type);
            ebook.setHistory(yourHashMap);
            HashNode thisNode = new HashNode(key, value);
            thisNode.setItem(ebook);
            list.add(thisNode);
        }
        else if(type.equals("DVD")) {
            DVD dvd = new DVD(title, copies, borrowed, isbn, author, pages, cost, type);
            dvd.setHistory(yourHashMap);
            HashNode thisNode = new HashNode(key, value);
            thisNode.setItem(dvd);
            list.add(thisNode);
        }
    }
}
