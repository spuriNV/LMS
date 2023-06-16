package library.users;

import library.staff.LibrarianList;
import org.json.JSONObject;
import persistence.Changers;

import java.util.Date;
import java.util.Random;

public class User implements Observer, Changers {

    private String telephone;
    private double fineStatus;
    private LoanList ItemLoans;
    private String user_name;
    private String password;

    public User(String user_name, String password, LoanList ItemLoans) {
        this.fineStatus = 0;
        this.user_name = user_name;
        this.telephone = "";
        this.password = password;
        setItemLoans(ItemLoans);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getFineStatus() {
        return fineStatus;
    }

    public void setFineStatus(double fineStatus) {
        this.fineStatus = fineStatus;
    }

    public LoanList getItemLoans() {
        return ItemLoans;
    }

    public void setItemLoans(LoanList itemLoans) {
        if(this.ItemLoans != itemLoans) {
            this.ItemLoans = itemLoans;
        }
    }

    public void addItemLoans(Loan loan, Date date) {

        LibrarianList librarianList = LibrarianList.getInstance();
        Random rand = new Random();
        int max = librarianList.getLibrarianList().size() - 1;
        int min = 0;
        int random = rand.nextInt(max - min + 1) + min;

        librarianList.getLibrarianList().get(random).checkOutItem(this, loan, date);
    }

    public void returnItemLoans(Loan loan) {
        if(loan.getLate()) {
            setFineStatus(getFineStatus() - loan.getBorrowed_item().getCost());
        }


        LibrarianList librarianList = LibrarianList.getInstance();
        Random rand = new Random();
        int max = librarianList.getLibrarianList().size() - 1;
        int min = 0;
        int random = rand.nextInt(max - min + 1) + min;

        librarianList.getLibrarianList().get(random).checkInItem(this, loan);
    }

    public void payFines(double num) {
        if(num >= getFineStatus()) {
            setFineStatus(0);
        }
        else {
            setFineStatus(getFineStatus() - num);
        }
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void updateNotificationFines() {
        System.out.println("Fines have been charged to your account");
    }

    public void updateNotificationItem() {
        System.out.println("A new item has been added to the library");
    }

    // Observer pattern method update


    @Override
    public String toString() {
        return "User{" +
                ", telephone='" + telephone + '\'' +
                ", fineStatus='" + fineStatus + '\'' +
                ", ItemLoans='" + ItemLoans + '\'' +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", user_name);
        json.put("password", password);
        json.put("telephone", telephone);
        json.put("fineStatus", fineStatus);
        json.put("Item Loans", ItemLoans.toJson());
        return json;
    }
}



