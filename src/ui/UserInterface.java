package ui;

import Item.*;
import library.staff.Librarian;
import library.staff.LibrarianList;
import library.tools.Database;
import library.tools.HashNode;
import library.users.Loan;
import library.users.LoanList;
import library.users.User;
import library.users.UserList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {

    private static final String JSON_STORE = "./data/library.json";
    private Scanner input;

    private Database database;

    private JsonWriter jsonWriter;

    private JsonReader jsonReader;

    private UserList userList;

    private LibrarianList librarianList;

    private User user;

    private Librarian librarian;


    // EFFECTS: runs the teller application
    public UserInterface() throws FileNotFoundException {
        input = new Scanner(System.in);
        this.database = Database.getInstance();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.userList = UserList.getInstance();
        this.librarianList = librarianList.getInstance();

        logIn();
    }

    private void logIn() {
        System.out.println("Enter U or L: ");
        String response = null;
        String user_input = null;
        String password = null;
        input = new Scanner(System.in);
        response = input.nextLine();
        if (response.equals("U")) {
            System.out.println("Enter your username or preferred username");
            user_input = input.nextLine();
            if (searchUsername(user_input, password)) {
                runApp();
            } else {
                registerUser(user_input, password);
            }
        } else if (response.equals("L")) {
            System.out.println("Enter your username or preferred username");
            user_input = input.nextLine();
            if (searchStaffname(user_input, password)) {
                runApp();
            } else {
                registerStaff(user_input, password);
            }
        } else {
            System.out.println("Refresh browser");
        }

    }

    private boolean searchUsername(String user_input, String password) {

        // if user is in userList, ask for password and see if it matches
        for (User user : userList.getUserList()) {
            if (user.getUser_name().equals(user_input)) {
                password = input.nextLine();
                int i = 2;
                while (!password.equals(user.getPassword()) && i != 0) {
                    System.out.println("Your password is incorrect. Try again");
                    password = input.nextLine();
                    i--;
                }
                if (!password.equals(user.getPassword())) {
                    System.out.println("Please refresh the browser");
                    return false;
                }
                System.out.println("Your password is correct.");

                user = searchUser(user_input);
                return true;
            }
        }
        return false;
    }

    private boolean searchStaffname(String user_input, String password) {

        // if user is in userList, ask for password and see if it matches
        for (Librarian librarian : librarianList.getLibrarianList()) {
            if (librarian.getUser_name().equals(user_input)) {
                password = input.nextLine();
                int i = 2;
                while (!password.equals("library2023") && i != 0) {
                    System.out.println("Your password is incorrect. Try again");
                    password = input.nextLine();
                    i--;
                }
                if (!password.equals("library2023")) {
                    System.out.println("Please refresh the browser");
                    return false;
                }
                librarian = searchStaff(user_input);
                return true;
            }
        }
        return false;
    }

    private void registerUser(String user_input, String password) {

        // else ask for registration
        System.out.println("Let's get you registered. Please type in the password.");
        System.out.println(" - You must have at least one special character");
        System.out.println(" - You must have at least two numbers");
        System.out.println(" - You must have at least one Uppercase character.");
        System.out.println(" - You must have at least six characters, in total.");
        System.out.println("\n Type your password below: ");

        password = input.nextLine();

        String regex = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);

        int i = 2;
        while (mt.matches() && i != 0) {
            System.out.println("Your password does not match our guidelines. Try again");
            password = input.next();
            i--;
        }
        if (mt.matches()) {
            System.out.println("Please refresh the browser");
            return;
        }

        ArrayList<Loan> list = new ArrayList<>();
        LoanList loans = new LoanList(list);
        user = new User(user_input, password, loans);
        userList.addToArray(user);
        runApp();
    }


    private void registerStaff(String user_input, String password) {
        librarian = new Librarian(user_input);
        librarianList.addToArray(librarian);
        libApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    private void libApp() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayLibMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processLibCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user

    private void displayMenu() {
        System.out.println("\n LIBRARY USER, select from:");
        System.out.println("\td -> Display current information");
        System.out.println("\tt -> Set Telephone number");
        System.out.println("\tu-> Set Username");
        System.out.println("\tp -> Set Password");
        System.out.println("\tc -> Check Out Items");
        System.out.println("\tr -> Return Items");
        System.out.println("\tz -> Due dates of loans");
        System.out.println("\tf -> Pay Fines");
        System.out.println("\tb -> Book search (specific)");
        System.out.println("\ti -> Book search (first initial author)");
        System.out.println("\ts -> Save information");
        System.out.println("\tl -> Load information");
        System.out.println("\tq -> quit");
    }

    private void processCommand(String command) {
        if (command.equals("d")) {
            System.out.println("Your information");
            displayInfoTask();
        }  else if (command.equals("t")) {
            setTelephoneTask();
        } else if (command.equals("u")) {
            setUsernameTask();
        } else if (command.equals("p")) {
            setPasswordTask();
        } else if (command.equals("c")) {
            checkOutTask();
        } else if (command.equals("r")) {
            checkInTask();
        } else if (command.equals("z")) {
            checkDueDateTask();
        } else if (command.equals("f")) {
            payFinesTask();
        } else if (command.equals("b")) {
            searchBookTask();
        } else if (command.equals("i")) {
            searchAuthorTask();
        } else if (command.equals("l")) {
            loadInfoTask();
        } else if (command.equals("s")) {
            saveInfoTask();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    private User searchUser(String user_input) {
        for (User user : userList.getUserList()) {
            if (user.getUser_name().equals(user_input)) {
                return user;
            }
        }
        return null;
    }

    // EFFECTS: prints all the properties of user
    private void displayInfoTask() {
        System.out.println(user.toString());
    }

    private void setTelephoneTask() {
        System.out.println("Please enter your phone number: ");
        System.out.println(" - You must type in the ###-###-#### format");

        String password = input.nextLine();
        String regex = "^[1-9]\\d{2}-\\d{3}-\\d{4}$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);

        int i = 2;
        while (!mt.matches() && i != 0) {
            System.out.println("Your format does not match our guidelines. Try again");
            password = input.next();
            i--;
        }
        if (!mt.matches()) {
            System.out.println("Please refresh the browser");
            return;
        }
        user.setTelephone(password);
        System.out.println("Successfully set.");
    }

    private void setUsernameTask() {
        System.out.println("Please enter name of new username: ");
        String username = input.nextLine();
        user.setUser_name(username);
        System.out.println("Successfully set.");
    }

    private void setPasswordTask() {
        System.out.println("Please enter name of new password: ");
        System.out.println(" - You must have at least one special character");
        System.out.println(" - You must have at least two numbers");
        System.out.println(" - You must have at least one Uppercase character.");
        System.out.println(" - You must have at least one lowercase character.");
        System.out.println(" - You must have at least eight characters, in total.");
        System.out.println("\n Type your password below: ");


        String password = input.nextLine();
        String regex = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);

        int i = 2;
        while (mt.matches() && i != 0) {
            System.out.println("Your password does not match our guidelines. Try again");
            password = input.next();
            i--;
        }
        if (mt.matches()) {
            System.out.println("Please refresh the browser");
            return;
        }
        user.setPassword(password);
        System.out.println("Successfully set.");
    }

    private void checkOutTask() {
        System.out.println("Enter the ISBN of the book you want: ");
        // specify how to input isbn
        double bookISBN = input.nextDouble();
        Date thisDate = new Date();
        HashNode node = database.search(bookISBN);
        item Item = node.getItem();

        Loan loan = new Loan(thisDate, Item, false);
        user.addItemLoans(loan, thisDate);
        Item.addToHistory(user, thisDate);
    }

    private void checkInTask() {
        System.out.println("Enter the ISBN of the book you want to return: ");
        // specify how to input isbn
        double bookISBN = input.nextDouble();

        Loan thisLoan;
        for(Loan loan : user.getItemLoans().getLoans()) {
            if(bookISBN == loan.getBorrowed_item().getIsbn()) {
                thisLoan = loan;
                user.returnItemLoans(thisLoan);
            }
        }
    }

    private void checkDueDateTask() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        for(Loan loan : user.getItemLoans().getLoans()) {
            String date = formatter.format(loan.getReturn_date());
            System.out.println(date);
        }
    }

    private void payFinesTask() {
        System.out.println("Enter the amount of money you want to pay: ");
        double cost = input.nextDouble();
        user.payFines(cost);
    }

    private void searchBookTask() {
        System.out.println("Input the first 3 letters of author's first name, followed by a decimal");
        System.out.println("Followed by first five letters of item title name");
        System.out.println("A - 11 to Z - 36");
        System.out.println("Enter the ISBN of the book you want: ");
        double bookISBN = input.nextDouble();
        if (database.search(bookISBN) != null) {
            HashNode node = database.search(bookISBN);
            item Item = node.getItem();
            if (Item.getBorrowed() < Item.getCopies()) {
                System.out.println("This item is available!");
            }
        } else {
            System.out.println("Sorry, we do not have that item in our library.");
        }
    }

    private void searchAuthorTask() {
        System.out.println("A - 11 to Z - 36");
        System.out.println("Enter the first letter of an author: ");
        int firstDigit = input.nextInt();
        ArrayList<HashNode> nodes = database.booksByAuthor(firstDigit);

        for (HashNode node : nodes) {
            System.out.println(node.getItem().getTitle());
        }
    }

    // EFFECTS: saves the userList to file
    private void saveInfoTask() {
        try {
            jsonWriter.open();
            jsonWriter.writeLists();
            jsonWriter.close();
            System.out.println("Saved UserList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads userList from file
    private void loadInfoTask() {
        try {
            userList.setUserList(jsonReader.readUserList());
            System.out.println("Loaded UserList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    private void displayLibMenu() {
        System.out.println("\n LIBRARIAN, select from:");
        System.out.println("\tb -> Display current information");
        System.out.println("\tc -> Set Telephone number");
        System.out.println("\td-> Set Username");
        System.out.println("\te -> Set Birthdate");
        System.out.println("\tf -> Add to UserList");
        System.out.println("\tg -> Remove from UserList");
        System.out.println("\th -> Search history of users from item");
        System.out.println("\ti -> Book search (specific)");
        System.out.println("\tj -> Book search (first intial author)");
        System.out.println("\tk -> Increase Copies of item");
        System.out.println("\tl -> Decrease Copies of item");
        System.out.println("\tm -> Record Fine");
        System.out.println("\to -> Add to Database");
        System.out.println("\tp -> Remove from Database");
        System.out.println("\ts -> Save information");
        System.out.println("\tr -> Load information");
        System.out.println("\tq -> quit");
    }

    private void processLibCommand(String command) {
        if (command.equals("b")) {
            System.out.println("Your information");
            displayInfoLib();
        } else if (command.equals("c")) {
            setTelephoneLib();
        } else if (command.equals("d")) {
            setUsernameLib();
        } else if (command.equals("e")) {
            setBirthdateLib();
        } else if (command.equals("f")) {
            addToUserListLib();
        } else if (command.equals("g")) {
            removeFromUserListLib();
        } else if (command.equals("h")) {
            searchHistoryFromItem();
        } else if (command.equals("i")) {
            searchBookLib();
        } else if (command.equals("j")) {
            searchBookbyAuthorLib();
        } else if (command.equals("k")) {
            increaseCopiesLib();
        } else if (command.equals("l")) {
            decreaseCopiesLib();
        } else if (command.equals("m")) {
            recordFineLib();
        }  else if (command.equals("n")) {
            addToDatabaseLib();
        }else if (command.equals("o")) {
            removeFromDatabaseLib();
        }else if (command.equals("p")) {
            loadInfoLib();
        } else if (command.equals("s")) {
            saveInfoLib();
        } else if (command.equals("a")) {
            loadDatabase();
        } else if (command.equals("r")) {
            saveDatabase();
        }else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt user for name of task and adds to todolist

    private Librarian searchStaff(String user_input) {
        for (Librarian librarian : librarianList.getLibrarianList()) {
            if (librarian.getUser_name().equals(user_input)) {
                return librarian;
            }
        }
        return null;
    }

    // EFFECTS: prints all the properties of user
    private void displayInfoLib() {
        System.out.println(user.toString());
    }

    private void setTelephoneLib() {
        System.out.println("Please enter your phone number: ");
        System.out.println(" - You must type in the ###-###-#### format");

        String password = input.nextLine();
        String regex = "\\d{3}-\\d{3}-\\d{4}$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);

        int i = 2;
        while (!mt.matches() && i != 0) {
            System.out.println("Your format does not match our guidelines. Try again");
            password = input.next();
            i--;
        }
        if (!mt.matches()) {
            System.out.println("Please refresh the browser");
            return;
        }
        librarian.setTelephone(password);
        System.out.println("Successfully set.");
    }

    private void setUsernameLib() {
        System.out.println("Please enter name of new username: ");
        String username = input.nextLine();
        librarian.setUser_name(username);
        System.out.println("Successfully set.");
    }

    private void setBirthdateLib() {
        System.out.println("Please enter your birthdate ");
        System.out.println(" - You must type in the DD/MM/YYYY format");
        System.out.println("\n Type your birthdate below: ");


        String password = input.nextLine();
        String regex = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);

        int i = 2;
        while (!mt.matches() && i != 0) {
            System.out.println("Your format does not match our guidelines. Try again");
            password = input.next();
            i--;
        }
        if (!mt.matches()) {
            System.out.println("Please refresh the browser");
            return;
        }
        librarian.setBirthdate(password);
        System.out.println("Successfully set.");
    }

    private void addToUserListLib() {
        System.out.println("Enter the username of the user: ");
        String response = input.nextLine();
        User user = searchUser(response);
        librarian.addToList(user);
    }

    private void removeFromUserListLib() {
        System.out.println("Enter the username of the user: ");
        String response = input.nextLine();
        User user = searchUser(response);
        librarian.removeFromList(user);
    }

    private void searchHistoryFromItem() {
        System.out.println("Enter the ISBN of the book you want: ");
        // specify how to input isbn
        double bookISBN = input.nextDouble();
        if (database.search(bookISBN) != null) {
            HashNode node = database.search(bookISBN);
            item Item = node.getItem();
            if (Item.getBorrowed() < Item.getCopies()) {
                System.out.println("This item is available!");
            }
        } else {
            System.out.println("Sorry, we do not have that item in our library.");
        }
    }

    private void searchBookLib() {
        System.out.println("Enter the ISBN of the book you want: ");
        // specify how to input isbn
        double bookISBN = input.nextDouble();
        if (database.search(bookISBN) != null) {
            HashNode node = database.search(bookISBN);
            item Item = node.getItem();
            if (Item.getBorrowed() < Item.getCopies()) {
                System.out.println("This item is available!");
            }
        } else {
            System.out.println("Sorry, we do not have that item in our library.");
        }

    }

    private void searchBookbyAuthorLib() {
        System.out.println("Enter the first letter of an author: ");
        int firstDigit = input.nextInt();
        ArrayList<HashNode> nodes = database.booksByAuthor(firstDigit);

        for (HashNode node : nodes) {
            System.out.println(node.getItem().getTitle());
        }
    }
    private void increaseCopiesLib() {
        System.out.println("Enter the ISBN of the book: ");
        double isbn = input.nextDouble();
        HashNode node = database.search(isbn);
        int copies = node.getItem().getCopies();
        node.getItem().setCopies(copies + 1);
    }
    private void decreaseCopiesLib() {
        System.out.println("Enter the ISBN of the book: ");
        double isbn = input.nextDouble();
        HashNode node = database.search(isbn);
        int copies = node.getItem().getCopies();
        node.getItem().setCopies(copies - 1);
    }
    private void recordFineLib() {
        librarian.refreshStatus();
    }
    private void addToDatabaseLib() {
        System.out.println("What would you like to add: Book, Audiobook, Ebook, or DVD?");
        System.out.println("Type: book, audiobook, ebook, or dvd");
        String response = input.nextLine();

        if(response.equals("book")) {
            System.out.println("Title: ");
            String title = input.next();
            System.out.println("Copies: ");
            int copies = input.nextInt();
            System.out.println("Borrowed: ");
            int borrowed = input.nextInt();
            System.out.println("ISBN: ");
            double isbn = input.nextDouble();
            System.out.println("Author: ");
            String author = input.next();
            System.out.println("Pages: ");
            int pages = input.nextInt();
            System.out.println("Cost: ");
            double cost = input.nextDouble();
            System.out.println("Type: ");
            String type = input.next();

            Book book = new Book(title, copies, borrowed, isbn, author, pages, cost, type);
            HashNode node = new HashNode(isbn, title);
            node.setItem(book);
            node.setHeight(0);
            librarian.addToDatabase(node);
        }
        else if(response.equals("audiobook")) {
            System.out.println("Title: ");
            String title = input.next();
            System.out.println("Copies: ");
            int copies = input.nextInt();
            System.out.println("Borrowed: ");
            int borrowed = input.nextInt();
            System.out.println("ISBN: ");
            double isbn = input.nextDouble();
            System.out.println("Author: ");
            String author = input.next();
            System.out.println("Time: ");
            double time = input.nextDouble();
            System.out.println("Cost: ");
            double cost = input.nextDouble(); System.out.println("Type: ");
            String type = input.next();

            Audiobook book = new Audiobook(title, copies, borrowed, isbn, author, time, cost, type);
            HashNode node = new HashNode(isbn, title);
            node.setItem(book);
            node.setHeight(0);
            librarian.addToDatabase(node);
        }
        else if(response.equals("ebook")) {
            System.out.println("Title: ");
            String title = input.next();
            System.out.println("Copies: ");
            int copies = input.nextInt();
            System.out.println("Borrowed: ");
            int borrowed = input.nextInt();
            System.out.println("ISBN: ");
            double isbn = input.nextDouble();
            System.out.println("Author: ");
            String author = input.next();
            System.out.println("Pages: ");
            int pages = input.nextInt();
            System.out.println("Cost: ");
            double cost = input.nextDouble();
            System.out.println("Type: ");
            String type = input.next();

            Ebook book = new Ebook(title, copies, borrowed, isbn, author, pages, cost, type);
            HashNode node = new HashNode(isbn, title);
            node.setItem(book);
            node.setHeight(0);
            librarian.addToDatabase(node);
        }
        else if(response.equals("dvd")) {
            System.out.println("Title: ");
            String title = input.next();
            System.out.println("Copies: ");
            int copies = input.nextInt();
            System.out.println("Borrowed: ");
            int borrowed = input.nextInt();
            System.out.println("ISBN: ");
            double isbn = input.nextDouble();
            System.out.println("Director: ");
            String director = input.next();
            System.out.println("Time: ");
            double time = input.nextDouble();
            System.out.println("Cost: ");
            double cost = input.nextDouble();
            System.out.println("Type: ");
            String type = input.next();

            DVD book = new DVD(title, copies, borrowed, isbn, director, time, cost, type);
            HashNode node = new HashNode(isbn, title);
            node.setItem(book);
            node.setHeight(0);
            librarian.addToDatabase(node);
        }

        System.out.println("Successfully added.");
    }
    private void removeFromDatabaseLib() {
        System.out.println("Enter the ISBN of the book: ");
        double isbn = input.nextDouble();
        librarian.removeFromDatabase(isbn);

        System.out.println("Successfully removed.");
    }

    // EFFECTS: saves librarianList to file
    private void saveInfoLib() {
        try {
            jsonWriter.open();
            jsonWriter.writeLists();
            jsonWriter.close();
            System.out.println("Saved LibrarianList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads librarianList from file
    private void loadInfoLib() {
        try {
            librarianList.setLibrarianList(jsonReader.readLibrarianList());
            System.out.println("Loaded librarianList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves dataBase to file
    private void saveDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.writeLists();
            jsonWriter.close();
            System.out.println("Saved Database to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads dataBase from file
    private void loadDatabase() {
        try {
            database = jsonReader.readBaseList();
            System.out.println("Loaded Database from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}