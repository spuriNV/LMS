package tests;

import Item.Audiobook;
import Item.Book;
import library.staff.Staff;
import library.tools.Database;
import library.tools.HashNode;
import library.users.Loan;
import library.users.LoanList;
import library.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class StaffTest {

    private Staff staff;
    private Database base;

    @BeforeEach
    void RunBefore() {
        staff = new Staff("the_librarian");
        base = Database.getInstance();
    }


    @Test
    void getTelephoneTest() {
        assertEquals(null, staff.getTelephone());
    }

    @Test
    void setTelephoneTest() {
        staff.setTelephone("123-456-7890");
        assertEquals("123-456-7890", staff.getTelephone());
    }

    @Test
    void getUsernameTest() {
        assertEquals("the_librarian", staff.getUser_name());
    }

    @Test
    void setUsernameTest() {
        staff.setUser_name("abc");
        assertEquals("abc", staff.getUser_name());
    }


    @Test
    void getBirthdateTest() {
        assertEquals("04/24/1996", staff.getBirthdate());
    }

    @Test
    void setBirthdateTest() {
        staff.setBirthdate("03/12/1997");
        assertEquals("03/12/1997", staff.getBirthdate());
    }


    @Test
    void getUserListTest() {
        ArrayList<User> list = new ArrayList<>();
        assertEquals(list.size(), staff.getUserList().getUserList().size());
    }

    @Test
    void addUserListTest() {
        ArrayList<User> list = new ArrayList<>();

        ArrayList<Loan> otherList = new ArrayList<Loan>();
        LoanList newList = new LoanList(otherList);
        User name = new User("user_name", "password", newList);
        Date thisDate = new Date(2016, 07, 22);

        list.add(name);
        staff.addToList(name);

        assertEquals(list.size(), staff.getUserList().getUserList().size());
    }

    @Test
    void removeUserListTest() {
        ArrayList<User> list = new ArrayList<>();
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        LoanList newList = new LoanList(otherList);
        User name = new User("user_name", "password", newList);
        list.add(name);
        staff.addToList(name);
        assertEquals(list.size(), staff.getUserList().getUserList().size());
    }

   @Test
   void searchKeyTest() {
        assertFalse(staff.search(251211.1126282523));
   }

    @Test
    void searchTrueKeyTest() {
        Audiobook audiobook = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
        HashNode node = new HashNode(251211.1126282523, "A Promised Land");
        base.add(node);
        assertTrue(staff.search(251211.1126282523));
    }

    @Test
    void searchHistoryTest() {
        Audiobook it = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook" );
        HashMap<User, Date> list = new HashMap<>();

        ArrayList<Loan> otherList = new ArrayList<Loan>();
        LoanList newList = new LoanList(otherList);
        User name = new User("user_name", "password", newList);

        Date thisDate = new Date();
        Loan loan = new Loan(thisDate, it, false);
        name.addItemLoans(loan, thisDate);

        list.put(name, thisDate);
        staff.addToList(name);

        assertEquals(list.size(), (staff.searchHistory(it)).getUserList().size());
    }


    @Test
    void increaseQuantityTest() {
       Book book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");
        staff.increaseQuantity(book);
        assertEquals(6, book.getCopies());
    }

    @Test
    void decreaseQuantityTest() {
        Book book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");
        staff.decreaseQuantity(book);
        assertEquals(4, book.getCopies());
    }

    @Test
    void recordFineTest() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        LoanList newList = new LoanList(otherList);
        User name = new User("user_name", "password", newList);
    }

    @Test
    void checkOutWorksTest() {
        Audiobook it = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
        ArrayList<User> list = new ArrayList<>();

        ArrayList<Loan> otherList = new ArrayList<Loan>();
        LoanList newList = new LoanList(otherList);
        User name = new User("user_name", "password", newList);

        Date thisDate = new Date();
        Loan loan = new Loan(thisDate, it, false);

        list.add(name);
        staff.addToList(name);

        staff.checkOutItem(name, loan, thisDate);

        otherList.add(loan);

        assertEquals(1, loan.getBorrowed_item().getBorrowed());
        assertEquals(otherList.size(), name.getItemLoans().getLoans().size());
    }

    @Test
    void checkInTest() {
        Audiobook it = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
        ArrayList<User> list = new ArrayList<>();

        ArrayList<Loan> otherList = new ArrayList<Loan>();
        LoanList newList = new LoanList(otherList);
        User name = new User("user_name", "password", newList);

        Date thisDate = new Date();
        Loan loan = new Loan(thisDate, it, false);

        list.add(name);
        staff.addToList(name);

        staff.checkOutItem(name, loan, thisDate);
        otherList.add(loan);

        assertEquals(1, loan.getBorrowed_item().getBorrowed());
        assertEquals(otherList.size(), name.getItemLoans().getLoans().size());


        staff.checkInItem(name, loan);
        otherList.remove(loan);
        assertEquals(0, loan.getBorrowed_item().getBorrowed());
        assertEquals(otherList.size(), name.getItemLoans().getLoans().size());

    }
}

