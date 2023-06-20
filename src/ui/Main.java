package ui;

import Item.*;
import library.staff.Librarian;
import library.staff.LibrarianList;
import library.tools.Database;
import library.tools.HashNode;
import library.users.Loan;
import library.users.User;
import library.users.UserList;
import persistence.JsonReader;
import library.users.LoanList;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements ActionListener {

    private JButton signUpButton;

    private JButton forgotPasswordButton;

    private JButton submitButton;

    private JLabel title;

    private JLabel usernameText;

    private JLabel passwordText;

    private JTextField usernameBox;

    private JTextField passwordBox;

    private JPanel abovePanel;

    private JPanel buttonPanel;

    private JFrame frame;

    private JLabel positionText;

    private JComboBox comboBox;

    private UserList userList;

    private LibrarianList librarianList;

    private Database database;

    private JRadioButton buttonD;
    private JRadioButton buttonT;
    private JRadioButton buttonU;
    private JRadioButton buttonP;
    private JRadioButton buttonC;
    private JRadioButton buttonR;
    private JRadioButton buttonZ;
    private JRadioButton buttonF;
    private JRadioButton buttonB;
    private JRadioButton buttonI;
    private JRadioButton buttonS;
    private JRadioButton buttonL;

    private User user;

    private JButton enter;

    private Librarian librarian;

    //
    private JRadioButton buttonDL;

    private JRadioButton buttonTL;

    private JRadioButton buttonUL;

    private JRadioButton buttonPL;

    private JRadioButton buttonCL;

    private JRadioButton buttonRL;

    private JRadioButton buttonZL;

    private JRadioButton buttonFL;

    private JRadioButton buttonBL;

    private JRadioButton buttonKL;

    private JRadioButton buttonLL;

    private JRadioButton buttonML;

    private JRadioButton buttonOL;

    private JRadioButton buttonQL;

    private JRadioButton buttonVL;

    private JRadioButton buttonSL;

    private JTextField titleBox;

    private JTextField copiesBox;

    private JTextField borrowedBox;

    private JTextField isbnBox;

    private JTextField authorBox;

    private JTextField pagesBox;

    private JTextField costBox;

    private JTextField typeBox;

    public Main() {
        userList = UserList.getInstance();
        librarianList = LibrarianList.getInstance();
        database = Database.getInstance();
        mainScreen();
    }

    private void mainScreen() {
        title = new JLabel();
        title.setText("Library Management System");
        title.setForeground(new Color(50, 115, 168));
        title.setFont(new Font("Buffalo", Font.BOLD, 20));
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        usernameText = new JLabel();
        usernameText.setText("Username:");
        usernameText.setForeground(Color.BLACK);
        usernameText.setBounds(30, 0, 100, 100);

        usernameBox = new JTextField();
        usernameBox.setBounds(130, 40, 280, 20);

        passwordText = new JLabel();
        passwordText.setText("Password:");
        passwordText.setForeground(Color.BLACK);
        passwordText.setBounds(30, 40, 100, 100);

        passwordBox = new JTextField();
        passwordBox.setBounds(130, 80, 280, 20);

        signUpButton = new JButton();
        signUpButton.setBounds(170, 180, 150, 40);
        signUpButton.addActionListener(this);
        signUpButton.setText("Sign Up");
        signUpButton.setBackground(Color.WHITE);

        forgotPasswordButton = new JButton();
        forgotPasswordButton.setBounds(330, 180, 150, 40);
        forgotPasswordButton.addActionListener(this);
        forgotPasswordButton.setText("Forgot Password");
        forgotPasswordButton.setBackground(Color.WHITE);

        submitButton = new JButton();
        submitButton.setBounds(10, 180, 150, 40);
        submitButton.addActionListener(this);
        submitButton.setText("Login");
        submitButton.setBackground(new Color(135, 214, 160));
        submitButton.setOpaque(true);
        submitButton.setForeground(new Color(11, 156, 57));

        positionText = new JLabel();
        positionText.setText("Position:");
        positionText.setForeground(Color.BLACK);
        positionText.setBounds(30, 80, 100, 100);

        String[] positions = {"", "User", "Librarian"};
        comboBox = new JComboBox(positions);
        comboBox.setBounds(130, 80, 200, 100);
        comboBox.addActionListener(this);

        abovePanel = new JPanel();
        abovePanel.setBounds(0, 0, 500, 100);
        abovePanel.setBackground(Color.LIGHT_GRAY);
        abovePanel.add(title);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 100, 500, 250);
        buttonPanel.setLayout(null);
        buttonPanel.add(usernameText);
        buttonPanel.add(passwordText);
        buttonPanel.add(usernameBox);
        buttonPanel.add(passwordBox);
        buttonPanel.add(signUpButton);
        buttonPanel.add(forgotPasswordButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(comboBox);
        buttonPanel.add(positionText);

        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Library Management System");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        frame.add(buttonPanel);
        frame.add(abovePanel);

    }

    private boolean searchUsername(String user_input, String password) {

        // if user is in userList, ask for password and see if it matches
        for (User user : userList.getUserList()) {
            if (user.getUser_name().equals(user_input)) {
                if (password.equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }


    private void registerUser(String user_input, String password) {

        password = JOptionPane.showInputDialog("Password \n  - You must have at least one special character \n" +
                "- You must have at least two numbers \n - You must have at least one Uppercase character. \n - You must have at least one lowercase character. \n" +
                "- You must have at least eight characters, in total.", "Enter here");

        String regex = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);


        if (!mt.matches()) {
            JOptionPane.showMessageDialog(null, "Your password format does not meet the guidelines.", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        else {
            user.setPassword(password);
            ArrayList<Loan> list = new ArrayList<>();
            LoanList loans = new LoanList(list);
            user = new User(user_input, password, loans);
            userList.addToArray(user);
            JOptionPane.showMessageDialog(null, "Password successfully set.", "Successful Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayInfoTask() {
        JOptionPane.showMessageDialog(null, user.toString(), "Your Current Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setTelephoneTask() {

        String telephone = JOptionPane.showInputDialog("Telephone: Type in the ###-###-#### format", "Enter here");
        String regex = "^[1-9]\\d{2}-\\d{3}-\\d{4}$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(telephone);

        if (!mt.matches()) {
            JOptionPane.showMessageDialog(null, "Your telephone format does not meet the guidelines.", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        else {
            user.setTelephone(telephone);
           JOptionPane.showMessageDialog(null, "Telephone number successfully set.", "Successful Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setUsernameTask() {
        String username = JOptionPane.showInputDialog("Username: ", "Enter here");
        user.setUser_name(username);
        JOptionPane.showMessageDialog(null, "Username successfully set", "Successful Message", JOptionPane.INFORMATION_MESSAGE);
    }

     private void setPasswordTask() {
        String password = JOptionPane.showInputDialog("Password \n  - You must have at least one special character \n" +
                "- You must have at least two numbers \n - You must have at least one Uppercase character. \n - You must have at least one lowercase character. \n" +
                "- You must have at least eight characters, in total.", "Enter here");

        String regex = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(password);


    }

    private void checkOutTask() {
        double bookISBN = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the book you want: ", "Enter here"));

        if(database.search(bookISBN) != null) {
            Date thisDate = new Date();
            HashNode node = database.search(bookISBN);
            item Item = node.getItem();

            Loan loan = new Loan(thisDate, Item, false);
            user.addItemLoans(loan, thisDate);
            Item.addToHistory(user, thisDate);
        }
        else {
            JOptionPane.showMessageDialog(null, "Item is not available", "Error message", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void checkInTask() {
        System.out.println("Enter the ISBN of the book you want to return: ");
        // specify how to input isbn
        double bookISBN = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the book you intend to return: ", "Enter here"));

        Loan thisLoan;
        for(Loan loan : user.getItemLoans().getLoans()) {
            if(bookISBN == loan.getBorrowed_item().getIsbn()) {
                thisLoan = loan;
                user.returnItemLoans(thisLoan);
            }
        }
        JOptionPane.showMessageDialog(null, "Item has been returned", "Successful message", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkDueDateTask() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String display = "";
        for(Loan loan : user.getItemLoans().getLoans()) {
            String date = formatter.format(loan.getReturn_date());
            display += date + ",";
        }
        JOptionPane.showMessageDialog(null, display, "Successful message", JOptionPane.PLAIN_MESSAGE);
    }

    private void payFinesTask() {
        double cost = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of money you want to pay: ", "Enter here"));
        user.payFines(cost);
    }

    private void searchBookTask() {
        double bookISBN = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the book you're looking for: ", "Enter here"));

        if (database.search(bookISBN) != null) {
            HashNode node = database.search(bookISBN);
            item Item = node.getItem();
            if (Item.getBorrowed() < Item.getCopies()) {
                JOptionPane.showMessageDialog(null, "Item is available", "Successful message", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item is not available", "Error message", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchAuthorTask() {

        int firstDigit = Integer.parseInt(JOptionPane.showInputDialog("Enter the first letter of an author (integer): ", "Enter here"));
        ArrayList<HashNode> nodes = database.booksByAuthor(firstDigit);

        String display = "";
        for (HashNode node : nodes) {
            display += node.getItem().getTitle();
        }
        JOptionPane.showMessageDialog(null, display, "Successful message", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveInfoTask() {
        String JSON_STORE = "./data/library.json";
        try {
            JsonWriter writer = new JsonWriter(JSON_STORE);
            writer.open();
            writer.writeLists();
            writer.close();
            System.out.println("Saved UserList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads userList from file
    private void loadInfoTask() {
        String JSON_STORE = "./data/library.json";
        try {
            JsonReader reader = new JsonReader(JSON_STORE);
            userList.setUserList(reader.readUserList());
            System.out.println("Loaded UserList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    private void UserOperations(String usernameBox) {

        JFrame frame = new JFrame();
        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Library Management System");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel();
        title.setText("Welcome " + usernameBox + ",");
        title.setForeground(new Color(50, 115, 168));
        title.setFont(new Font("Buffalo", Font.BOLD, 20));
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JLabel choose = new JLabel();
        choose.setText("Choose an option:");
        choose.setForeground(Color.BLACK);
        choose.setFont(new Font("Buffalo", Font.BOLD, 15));
        choose.setHorizontalAlignment(JLabel.LEFT);
        choose.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 30));

        JPanel abovePanel = new JPanel();
        abovePanel.setBounds(0, 0, 500, 100);
        abovePanel.setBackground(Color.LIGHT_GRAY);
        abovePanel.add(title);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 100, 500, 250);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 100, 500, 250);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 10));
        buttonPanel.setBounds(20, 160, 450, 180);

        buttonD = new JRadioButton("Display Your Info");
        buttonT = new JRadioButton("Set Telephone Number");
        buttonU = new JRadioButton("Set Username");
        buttonP = new JRadioButton("Set Password");
        buttonC = new JRadioButton("Check Out Items");
        buttonR = new JRadioButton("Return Items");
        buttonZ = new JRadioButton("Due dates of loans");
        buttonF = new JRadioButton("Pay Fines");
        buttonB = new JRadioButton("Book Search (specific)");
        buttonI = new JRadioButton("Book Search (first initial author)");
        buttonS = new JRadioButton("Save Information");
        buttonL = new JRadioButton("Load Information");

        buttonD.addActionListener(this);
        buttonT.addActionListener(this);
        buttonU.addActionListener(this);
        buttonP.addActionListener(this);
        buttonC.addActionListener(this);
        buttonR.addActionListener(this);
        buttonZ.addActionListener(this);
        buttonF.addActionListener(this);
        buttonB.addActionListener(this);
        buttonI.addActionListener(this);
        buttonS.addActionListener(this);
        buttonL.addActionListener(this);

        ButtonGroup group = new ButtonGroup();
        group.add(buttonD);
        group.add(buttonT);
        group.add(buttonU);
        group.add(buttonP);
        group.add(buttonC);
        group.add(buttonR);
        group.add(buttonZ);
        group.add(buttonF);
        group.add(buttonB);
        group.add(buttonI);
        group.add(buttonS);
        group.add(buttonL);

        buttonPanel.add(buttonD);
        buttonPanel.add(buttonT);
        buttonPanel.add(buttonU);
        buttonPanel.add(buttonP);
        buttonPanel.add(buttonC);
        buttonPanel.add(buttonR);
        buttonPanel.add(buttonZ);
        buttonPanel.add(buttonF);
        buttonPanel.add(buttonB);
        buttonPanel.add(buttonI);
        buttonPanel.add(buttonS);
        buttonPanel.add(buttonL);

        frame.add(abovePanel);
        mainPanel.add(choose);

        layeredPane.add(buttonPanel);
        layeredPane.add(mainPanel);
        frame.add(layeredPane);

    }


    private void registerStaff(String user_input) {

        String password = JOptionPane.showInputDialog("Type the librarian password: ", "Enter here");
        if(password.equals("library2023")) {
            librarian = new Librarian(user_input);
            librarianList.addToArray(librarian);
            LibrarianOperations(usernameBox.getText());
        }
        else{
            JOptionPane.showMessageDialog(null, "Password is incorrect", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }


    private boolean searchStaffname(String user_input, String password) {

        // if user is in userList, ask for password and see if it matches
        for (Librarian librarian : librarianList.getLibrarianList()) {
            if (librarian.getUser_name().equals(user_input)) {
                if (password.equals("library2023")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void displayInfoLib() {
        JOptionPane.showMessageDialog(null, librarian.toString(), "Your Current Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setTelephoneLib() {

        String telephone = JOptionPane.showInputDialog("Telephone: Type in the ###-###-#### format", "Enter here");
        String regex = "^[1-9]\\d{2}-\\d{3}-\\d{4}$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(telephone);

        if (!mt.matches()) {
            JOptionPane.showMessageDialog(null, "Your telephone format does not meet the guidelines.", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        else {
            librarian.setTelephone(telephone);
            JOptionPane.showMessageDialog(null, "Telephone number successfully set.", "Successful Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setUsernameLib() {
        String username = JOptionPane.showInputDialog("Username: ", "Enter here");
        librarian.setUser_name(username);
        JOptionPane.showMessageDialog(null, "Username successfully set", "Successful Message", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setBirthdateLib() {

        String birthdate = JOptionPane.showInputDialog("Birthdate: Type in the DD/MM/YYYY format", "Enter here");
        String regex = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(birthdate);

        if (!mt.matches()) {
            JOptionPane.showMessageDialog(null, "Your birthdate format does not meet the guidelines.", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        else {
            librarian.setBirthdate(birthdate);
            JOptionPane.showMessageDialog(null, "Birthdate successfully set.", "Successful Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addToUserListLib() {
        String response = JOptionPane.showInputDialog("Enter the username of the user: ", "Enter here");
        for(User user : userList.getUserList()) {
            if(response.equals(user.getUser_name())) {
                librarian.addToList(user);
            }
        }
    }

    private void removeFromUserListLib() {
        String response = JOptionPane.showInputDialog("Enter the username of the user: ", "Enter here");
        for(User user : userList.getUserList()) {
            if(response.equals(user.getUser_name())) {
                librarian.removeFromList(user);
            }
        }
    }

    private void searchHistoryFromItem() {
        double bookISBN = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the book you're looking for: ", "Enter here"));
        String display = "";
        if (database.search(bookISBN) != null) {
            HashNode node = database.search(bookISBN);
            HashMap<String, String> map = node.getItem().getHistory();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                display += key + " - " + value + ",";
            }
            JOptionPane.showMessageDialog(null, display, "Successful message", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void searchBookLib() {
        double bookISBN = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the book you're looking for: ", "Enter here"));

        if (database.search(bookISBN) != null) {
            HashNode node = database.search(bookISBN);
            item Item = node.getItem();
            if (Item.getBorrowed() < Item.getCopies()) {
                JOptionPane.showMessageDialog(null, "Item is available", "Successful message", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item is not available", "Error message", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchAuthorLib() {

        int firstDigit = Integer.parseInt(JOptionPane.showInputDialog("Enter the first letter of an author (integer): ", "Enter here"));
        ArrayList<HashNode> nodes = database.booksByAuthor(firstDigit);

        String display = "";
        for (HashNode node : nodes) {
            display += node.getItem().getTitle();
        }
        JOptionPane.showMessageDialog(null, display, "Successful message", JOptionPane.PLAIN_MESSAGE);
    }

    private void increaseCopiesLib() {
        double isbn = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the item:", "Enter here"));
        if(database.search(isbn) != null) {
            HashNode node = database.search(isbn);
            int copies = node.getItem().getCopies();
            node.getItem().setCopies(copies + 1);
        }
    }

    private void decreaseCopiesLib() {
        double isbn = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the item:", "Enter here"));
        if(database.search(isbn) != null) {
            HashNode node = database.search(isbn);
            int copies = node.getItem().getCopies();
            node.getItem().setCopies(copies - 1);
        }
    }

    private void recordFineLib() {
        librarian.refreshStatus();
    }

    private void addToDatabaseLib() {

        JFrame frame = new JFrame();
        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Library Management System");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(null);

        JLabel title = new JLabel();
        title.setText("Add Item details:");
        title.setForeground(new Color(50, 115, 168));
        title.setFont(new Font("Buffalo", Font.BOLD, 20));
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JPanel abovePanel = new JPanel();
        abovePanel.setBounds(0, 0, 500, 80);
        abovePanel.setBackground(Color.LIGHT_GRAY);
        abovePanel.add(title);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 60, 500, 400);
        mainPanel.setLayout(null);

        JLabel titleText = new JLabel();
        titleText.setText("Title:");
        titleText.setForeground(Color.BLACK);
        titleText.setBounds(30, 0, 100, 100);

        titleBox = new JTextField();
        titleBox.setBounds(130, 40, 280, 20);

        JLabel copiesText = new JLabel();
        copiesText.setText("Copies:");
        copiesText.setForeground(Color.BLACK);
        copiesText.setBounds(30, 40, 100, 100);

        copiesBox = new JTextField();
        copiesBox.setBounds(130, 80, 280, 20);

        JLabel borrowedText = new JLabel();
        borrowedText.setText("Borrowed:");
        borrowedText.setForeground(Color.BLACK);
        borrowedText.setBounds(30, 80, 100, 100);

        borrowedBox = new JTextField();
        borrowedBox.setBounds(130, 120, 280, 20);

        JLabel isbnText = new JLabel();
        isbnText.setText("ISBN:");
        isbnText.setForeground(Color.BLACK);
        isbnText.setBounds(30, 120, 100, 100);

        isbnBox = new JTextField();
        isbnBox.setBounds(130, 160, 280, 20);

        JLabel authorText = new JLabel();
        authorText.setText("Author/Director:");
        authorText.setForeground(Color.BLACK);
        authorText.setBounds(30, 160, 100, 100);

        authorBox = new JTextField();
        authorBox.setBounds(130, 200, 280, 20);

        JLabel pagesText = new JLabel();
        pagesText.setText("Pages/Time:");
        pagesText.setForeground(Color.BLACK);
        pagesText.setBounds(30, 200, 100, 100);

        pagesBox = new JTextField();
        pagesBox.setBounds(130, 240, 280, 20);

        JLabel costText = new JLabel();
        costText.setText("Cost:");
        costText.setForeground(Color.BLACK);
        costText.setBounds(30, 240, 100, 100);

        costBox = new JTextField();
        costBox.setBounds(130, 280, 280, 20);

        JLabel typeText = new JLabel();
        typeText.setText("Type:");
        typeText.setForeground(Color.BLACK);
        typeText.setBounds(30, 280, 100, 100);

        typeBox = new JTextField();
        typeBox.setBounds(130, 320, 280, 20);

        enter = new JButton();
        enter.setBounds(30, 360, 150, 30);
        enter.addActionListener(this);
        enter.setText("Enter");
        enter.setBackground(new Color(135, 214, 160));
        enter.setOpaque(true);
        enter.setForeground(new Color(11, 156, 57));


        mainPanel.add(titleText);
        mainPanel.add(titleBox);

        mainPanel.add(copiesText);
        mainPanel.add(copiesBox);

        mainPanel.add(borrowedText);
        mainPanel.add(borrowedBox);

        mainPanel.add(isbnText);
        mainPanel.add(isbnBox);

        mainPanel.add(authorText);
        mainPanel.add(authorBox);

        mainPanel.add(pagesText);
        mainPanel.add(pagesBox);

        mainPanel.add(costText);
        mainPanel.add(costBox);

        mainPanel.add(typeText);
        mainPanel.add(typeBox);

        enter.addActionListener(this);

        mainPanel.add(enter);

        frame.add(abovePanel);
        frame.add(mainPanel);


    }

    private void removeFromDatabaseLib() {
        double isbn = Double.parseDouble(JOptionPane.showInputDialog("Enter the ISBN of the book:", "Enter here"));
        if(database.search(isbn) != null) {
            librarian.removeFromDatabase(isbn);
            JOptionPane.showMessageDialog(null, "Successfully removed from database", "Successful message", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void saveInfoLib() {
        String JSON_STORE = "./data/library.json";
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.writeLists();
            jsonWriter.close();
            System.out.println("Saved LibrarianList and Database to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads librarianList from file
    private void loadInfoLib() {
        String JSON_STORE = "./data/library.json";
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            librarianList.setLibrarianList(jsonReader.readLibrarianList());
            database = jsonReader.readBaseList();
            System.out.println("Loaded librarianList and Database from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void LibrarianOperations(String username) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Library Management System");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel();
        title.setText("Welcome librarian" + username + ",");
        title.setForeground(new Color(50, 115, 168));
        title.setFont(new Font("Buffalo", Font.BOLD, 20));
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JLabel choose = new JLabel();
        choose.setText("Choose an option:");
        choose.setForeground(Color.BLACK);
        choose.setFont(new Font("Buffalo", Font.BOLD, 15));
        choose.setHorizontalAlignment(JLabel.LEFT);
        choose.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 30));

        JPanel abovePanel = new JPanel();
        abovePanel.setBounds(0, 0, 500, 100);
        abovePanel.setBackground(Color.LIGHT_GRAY);
        abovePanel.add(title);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 100, 500, 250);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 100, 500, 250);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 10));
        buttonPanel.setBounds(20, 160, 450, 180);

        buttonDL = new JRadioButton("Display Your Info");
        buttonTL = new JRadioButton("Set Telephone Number");
        buttonUL = new JRadioButton("Set Username");
        buttonPL = new JRadioButton("Set Birthdate");
        buttonCL = new JRadioButton("Add to UserList");
        buttonRL = new JRadioButton("Remove from UserList");
        buttonZL = new JRadioButton("Search history of users from item");
        buttonFL = new JRadioButton("Book search (specific)");
        buttonBL = new JRadioButton("Book Search (first initial author)");
        buttonKL = new JRadioButton("Increase copies of item");
        buttonLL = new JRadioButton("Decrease copies of item");
        buttonML = new JRadioButton("Record fine");
        buttonOL = new JRadioButton("Add to Database");
        buttonQL = new JRadioButton("Remove from Database");
        buttonVL = new JRadioButton("Load Information");
        buttonSL = new JRadioButton("Save Information");


        buttonDL.addActionListener(this);
        buttonTL.addActionListener(this);
        buttonUL.addActionListener(this);
        buttonPL.addActionListener(this);
        buttonCL.addActionListener(this);
        buttonRL.addActionListener(this);
        buttonZL.addActionListener(this);
        buttonFL.addActionListener(this);
        buttonBL.addActionListener(this);
        buttonKL.addActionListener(this);
        buttonLL.addActionListener(this);
        buttonML.addActionListener(this);
        buttonOL.addActionListener(this);
        buttonQL.addActionListener(this);
        buttonVL.addActionListener(this);
        buttonSL.addActionListener(this);


        ButtonGroup group = new ButtonGroup();
        group.add(buttonDL);
        group.add(buttonTL);
        group.add(buttonUL);
        group.add(buttonPL);
        group.add(buttonCL);
        group.add(buttonRL);
        group.add(buttonZL);
        group.add(buttonFL);
        group.add(buttonBL);
        group.add(buttonKL);
        group.add(buttonLL);
        group.add(buttonML);
        group.add(buttonOL);
        group.add(buttonQL);
        group.add(buttonVL);
        group.add(buttonSL);

        buttonPanel.add(buttonDL);
        buttonPanel.add(buttonTL);
        buttonPanel.add(buttonUL);
        buttonPanel.add(buttonPL);
        buttonPanel.add(buttonCL);
        buttonPanel.add(buttonRL);
        buttonPanel.add(buttonZL);
        buttonPanel.add(buttonFL);
        buttonPanel.add(buttonBL);
        buttonPanel.add(buttonKL);
        buttonPanel.add(buttonLL);
        buttonPanel.add(buttonML);
        buttonPanel.add(buttonOL);
        buttonPanel.add(buttonQL);
        buttonPanel.add(buttonVL);
        buttonPanel.add(buttonSL);


        frame.add(abovePanel);
        mainPanel.add(choose);

        layeredPane.add(buttonPanel);
        layeredPane.add(mainPanel);
        frame.add(layeredPane);


    }
     @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signUpButton){
            signUpButton.setEnabled(false);

            if(comboBox.getSelectedItem().equals("User")) {
                if(!searchUsername(usernameBox.getText(), passwordBox.getText())) {
                    registerUser(usernameBox.getText(), passwordBox.getText());
                    UserOperations(usernameBox.getText());
                }
                else{
                    JOptionPane.showMessageDialog(null, "You already have an account.", "Error Message", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(comboBox.getSelectedItem().equals("Librarian")) {
                if(!searchStaffname(usernameBox.getText(), passwordBox.getText())) {
                    registerStaff(usernameBox.getText());
                }
                else{
                    JOptionPane.showMessageDialog(null, "You already have an account.", "Error Message", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        if(e.getSource() == forgotPasswordButton){
            forgotPasswordButton.setEnabled(false);
        }

        if(e.getSource() == submitButton) {
            submitButton.setEnabled(false);
            if(comboBox.getSelectedItem().equals("User")) {
                if(searchUsername(usernameBox.getText(), passwordBox.getText())) {
                    UserOperations(usernameBox.getText());
                }
                else{
                    JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Error Message", JOptionPane.ERROR_MESSAGE);
                }

            }

            else if(comboBox.getSelectedItem().equals("Librarian")) {
                if(searchStaffname(usernameBox.getText(), passwordBox.getText())) {
                    LibrarianOperations(usernameBox.getText());
                }
                else {
                    JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
             }
        }

        if(e.getSource() == buttonD) {
            displayInfoTask();
        }

        if(e.getSource() == buttonT) {
           setTelephoneTask();
        }

        if(e.getSource() == buttonU) {
            setUsernameTask();
        }

        if(e.getSource() == buttonP) {
            setPasswordTask();
        }

        if(e.getSource() == buttonC) {
            checkOutTask();
        }

        if(e.getSource() == buttonR) {
            checkInTask();
        }

        if(e.getSource() == buttonZ) {
            checkDueDateTask();
        }

        if(e.getSource() == buttonF) {
            payFinesTask();
        }

        if(e.getSource() == buttonB) {
            searchBookTask();
        }

        if(e.getSource() == buttonI) {
            searchAuthorTask();
        }

        if(e.getSource() == buttonS) {
            saveInfoTask();
        }

        if(e.getSource() == buttonL) {
            loadInfoTask();
        }

        ///////////

         if(e.getSource() == buttonDL) {
             displayInfoLib();
         }

         if(e.getSource() == buttonTL) {
             setTelephoneLib();
         }

         if(e.getSource() == buttonUL) {
             setUsernameLib();
         }

         if(e.getSource() == buttonPL) {
            setBirthdateLib();
         }

         if(e.getSource() == buttonCL) {
            addToUserListLib();
         }

         if(e.getSource() == buttonRL) {
             removeFromUserListLib();
         }

         if(e.getSource() == buttonZL) {
             searchHistoryFromItem();
         }

         if(e.getSource() == buttonFL) {
             searchBookLib();
         }

         if(e.getSource() == buttonBL) {
             searchAuthorLib();
         }

         if(e.getSource() == buttonKL) {
             increaseCopiesLib();
         }

         if(e.getSource() == buttonLL) {
             decreaseCopiesLib();
         }

         if(e.getSource() == buttonML) {
             recordFineLib();
         }

         if(e.getSource() == buttonOL) {
             addToDatabaseLib();
         }

         if(e.getSource() == buttonQL) {
             removeFromDatabaseLib();
         }

         if(e.getSource() == buttonVL) {
             loadInfoLib();
         }

         if(e.getSource() == buttonSL) {
             saveInfoLib();
         }

         if(e.getSource() == enter) {
             enter.setEnabled(false);
             if(typeBox.getText().equals("book")) {
                 Book book = new Book(titleBox.getText(), Integer.parseInt(copiesBox.getText()), Integer.parseInt(borrowedBox.getText()), Double.parseDouble(isbnBox.getText()), authorBox.getText(),
                         Double.parseDouble(pagesBox.getText()), Double.parseDouble(costBox.getText()), typeBox.getText());
                 HashNode node = new HashNode(Double.parseDouble(isbnBox.getText()), titleBox.getText());
                 node.setItem(book);
                 librarian.addToDatabase(node);
                 JOptionPane.showMessageDialog(null, "Successfully added to database", "Successful message", JOptionPane.INFORMATION_MESSAGE);
             }
             else if(typeBox.getText().equals("audiobook")) {
                 Audiobook book = new Audiobook(titleBox.getText(), Integer.parseInt(copiesBox.getText()), Integer.parseInt(borrowedBox.getText()), Double.parseDouble(isbnBox.getText()), authorBox.getText(),
                         Double.parseDouble(pagesBox.getText()), Double.parseDouble(costBox.getText()), typeBox.getText());
                 HashNode node = new HashNode(Double.parseDouble(isbnBox.getText()), titleBox.getText());
                 node.setItem(book);
                 librarian.addToDatabase(node);
                 JOptionPane.showMessageDialog(null, "Successfully added to database", "Successful message", JOptionPane.INFORMATION_MESSAGE);
             }
             else if(typeBox.getText().equals("ebook")) {
                 Ebook book = new Ebook(titleBox.getText(), Integer.parseInt(copiesBox.getText()), Integer.parseInt(borrowedBox.getText()), Double.parseDouble(isbnBox.getText()), authorBox.getText(),
                         Double.parseDouble(pagesBox.getText()), Double.parseDouble(costBox.getText()), typeBox.getText());
                 HashNode node = new HashNode(Double.parseDouble(isbnBox.getText()), titleBox.getText());
                 node.setItem(book);
                 librarian.addToDatabase(node);
                 JOptionPane.showMessageDialog(null, "Successfully added to database", "Successful message", JOptionPane.INFORMATION_MESSAGE);
             }
             else if(typeBox.getText().equals("dvd")) {
                 DVD book = new DVD(titleBox.getText(), Integer.parseInt(copiesBox.getText()), Integer.parseInt(borrowedBox.getText()), Double.parseDouble(isbnBox.getText()), authorBox.getText(),
                         Double.parseDouble(pagesBox.getText()), Double.parseDouble(costBox.getText()), typeBox.getText());
                 HashNode node = new HashNode(Double.parseDouble(isbnBox.getText()), titleBox.getText());
                 node.setItem(book);
                 librarian.addToDatabase(node);
                 JOptionPane.showMessageDialog(null, "Successfully added to database", "Successful message", JOptionPane.INFORMATION_MESSAGE);
             }
             LibrarianOperations(usernameBox.getText());
         }


    }


    public static void main(String[] args) throws FileNotFoundException {
        new Main();
    }
}
