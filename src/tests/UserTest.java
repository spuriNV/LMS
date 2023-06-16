package tests;

import Item.Book;
import library.staff.Librarian;
import library.users.Loan;
import library.users.LoanList;
import library.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User name;
    private Librarian librarian;
    private LoanList list;

    private Loan loan;

    private Book book;

    @BeforeEach
    void runBefore()  {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        list = new LoanList(otherList);
        librarian = new Librarian("the_librarian");
        name = new User("user_name", "password", list);
        book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");

        Date thisDate = new Date();
        loan = new Loan(thisDate, book, false);
    }

    @Test
    void getUserNameTest() {
        assertEquals("user_name", name.getUser_name());
    }

    @Test
    void setUserNameTest() {
        name.setUser_name("not_user_name");
        assertEquals("not_user_name", name.getUser_name());
    }


    @Test
    void getTelephoneTest() {
        assertEquals("", name.getTelephone());
    }

    @Test
    void setTelephoneTest() {
        name.setTelephone("123 456 7890");
        assertEquals("123 456 7890", name.getTelephone());
    }

    @Test
    void getFineTest() {
        assertEquals(0, name.getFineStatus());
    }

    @Test
    void setFineTest() {
        name.setFineStatus(100);
        assertEquals(100, name.getFineStatus());
    }

    @Test
    void getItemloansTest() {
        assertEquals(list, name.getItemLoans());
    }

    @Test
    void setItemloansTest() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        otherList.add(loan);
        LoanList newList = new LoanList(otherList);
        name.setItemLoans(newList);
        assertEquals(newList, name.getItemLoans());
    }

    @Test
    void toStringTest() {
        assertEquals("User{" +
                ", telephone='" + name.getTelephone() + '\'' +
                ", fineStatus='" + name.getFineStatus() + '\'' +
                ", ItemLoans='" + list.toString() + '\'' +
                ", user_name='" + name.getUser_name() + '\'' +
                ", password='" + name.getPassword() + '\'' +
                '}', name.toString());
    }
}
