package library.staff;

import Item.item;
import library.tools.Database;
import library.tools.HashNode;
import library.users.Loan;
import library.users.User;
import library.users.UserList;

import java.util.ArrayList;
import java.util.Date;

public class Staff implements Subject {

    private String telephone;
    private UserList userList;

    private Database hashTable;
    private String user_name;
    private String birthdate;


    public Staff(String user_name) {
        this.user_name = user_name;
        this.userList = UserList.getInstance();
        this.hashTable = Database.getInstance();
    }

    // REQUIRES: n/a
    // EFFECTS: returns telephone field
    public String getTelephone() {
        return telephone;
    }

    // REQUIRES: n/a
    // MODIFIES: telephone field
    // EFFECTS: sets telephone field telephone parameter
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // REQUIRES: n/a
    // EFFECTS: returns userlist instance
    public UserList getUserList() {
        return userList;
    }

    // REQUIRES: user parameter
    // MODIFIES: userList field
    // EFFECTS: adds user to userList
    public void addToList(User user) {
        if(!this.userList.getUserList().contains(user)) {
            this.userList.addToArray(user);
        }
    }

    // REQUIRES: user parameter
    // MODIFIES: userList field
    // EFFECTS: remove user from userList
    public void removeFromList(User user) {
        if(this.userList.getUserList().contains(user)) {
            this.userList.removeFromArray(user);
        }
    }

    // REQUIRES: n/a
    // EFFECTS: returns name field
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    // REQUIRES: n/a
    // EFFECTS: returns birthdate field
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    // REQUIRES: n/a
    // EFFECTS: returns item field
    public boolean search(double key) {
        return (this.hashTable.search(key) != null) && (this.hashTable.search(key).getItem().getCopies() >= 1);
    }

    // REQUIRES: n/a
    // EFFECTS: returns a list of users that have taken this item out
    public UserList searchHistory(item it) {
        ArrayList<User> history = new ArrayList();
        for(User user : this.userList.getUserList()) {
            if(it.getHistory().containsKey(user)) {
                history.add(user);
            }
        }
        return userList;
    }

    // REQUIRES: n/a
    // EFFECTS: increases copies quantity of item
    public void increaseQuantity(item it){
        it.setCopies(it.getCopies() + 1);
    }

    // REQUIRES: n/a
    // EFFECTS: decreases copies quantity of item
    public void decreaseQuantity(item it){

        if(it.getCopies() >= 1) {
            it.setCopies(it.getCopies() - 1);
        }
    }

    // REQUIRES: user and loan parameter
    // MODIFIES: user fine status
    // EFFECTS: records fine by calculating loan cost
    public void recordFine(User user, Loan loan) {
        user.setFineStatus(user.getFineStatus() + loan.getBorrowed_item().getCost());
        user.updateNotificationFines();
    }

    // REQUIRES: user and loan parameter
    // MODIFIES: loan.getItem()'s history list, loan.getItem()'s copies, user's loanList
    // EFFECTS: if item exists and there are sufficient copies and user's fine status is less than 10, then item's copies are reduced by 1, user adds loan to loanList,
    public void checkOutItem(User user, Loan loan, Date date) {
        HashNode node = this.hashTable.search(loan.getBorrowed_item().getIsbn());
        if((node != null) &&
                (node.getItem().getBorrowed() < node.getItem().getCopies()) && (user.getFineStatus() <= 10)) {
            loan.getBorrowed_item().setBorrowed(loan.getBorrowed_item().getBorrowed() + 1); // increase borrowed items
            user.getItemLoans().addToList(loan); // user adds loan to loanList
            loan.getBorrowed_item().addToHistory(user, date); // loan's item adds user to history

            loan.setReturn_date(date); // staff sets loan return date
        }
        else {
            System.out.println("Copies unavailable or Fines have exceeded capacity");
        }
    }
    // REQUIRES: user and loan parameter
    // MODIFIES: loan.getItem()'s history list, loan.getItem()'s copies, user's loanList
    // EFFECTS: added one to loan.getItem()'s copies, and user removes loan from loanList
    public void checkInItem(User user, Loan loan) {
            loan.getBorrowed_item().setBorrowed(loan.getBorrowed_item().getBorrowed() - 1);
            user.getItemLoans().removeFromList(loan);
    }


    public void refreshStatus() {
        for(User user : this.userList.getUserList()) {
            for(Loan loan : user.getItemLoans().getLoans()) {
                if(loan.getLate()) {
                    recordFine(user, loan);
                }
            }
        }
    }


    public void notifyObserversItem() {
        for(User user : userList.getUserList()) {
            user.updateNotificationItem();
        }
    }

    public void notifyObserversFines() {
        for(User user : userList.getUserList()) {
            user.updateNotificationFines();
        }
    }


    @Override
    public String toString() {
        return "Staff{" +
                ", telephone='" + telephone + '\'' +
                ", userList=" + userList +
                ", hashTable=" + hashTable +
                ", user_name='" + user_name + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }




    /*
    -Staff

        can view loan history of borrowers
        can add borrower
        can update personal information of borrower
        can checkout an item
        can checkin an item
        can record fine of borrower
        can increase quantity of book
        can decrease quantity of book
     */
}
