package tests;

import Item.Audiobook;
import Item.Book;
import library.staff.Staff;
import library.users.Loan;
import library.users.LoanList;
import library.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoanTest {

    private Loan loan;
    private ArrayList<Loan> otherList;
    private LoanList newList;
    private Staff staff;
    private User name;

    private Audiobook book;

    private Date thisDate;


    @BeforeEach
    void RunBefore() {
            otherList = new ArrayList<Loan>();
            newList = new LoanList(otherList);
            staff = new Staff("the_librarian");
            name = new User("user_name", "password", newList);
        book = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");

        thisDate = new Date();
        loan = new Loan(thisDate, book, false);
    }

    @Test
    void getIssueTest() {
        assertEquals(thisDate.getDate(), loan.getIssue_date().getDate());
    }

    @Test
    void setIssueTest() {

        Calendar c = Calendar.getInstance();
        c.setTime(loan.getIssue_date());
        c.add(Calendar.DATE, 14);

        loan.setIssue_date(c.getTime());

        assertEquals(c.getTime().getDate(), loan.getIssue_date().getDate());

    }

    @Test
    void getReturnTest() {
        Calendar c = Calendar.getInstance();
        c.setTime(loan.getIssue_date());
        c.add(Calendar.DATE, 14);

        assertEquals(c.getTime().getDay(), loan.getReturn_date().getDay());
        assertEquals(c.getTime().getMonth(), loan.getReturn_date().getMonth());
        assertEquals(c.getTime().getYear(), loan.getReturn_date().getYear());
    }


    @Test
    void getBorrowedItemTest() {
        Audiobook book = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
        assertEquals("A Promised Land", loan.getBorrowed_item().getTitle());
        assertEquals(251211.1126282523, loan.getBorrowed_item().getIsbn());
        assertEquals("audiobook", loan.getBorrowed_item().getType());
        assertEquals(26.80, loan.getBorrowed_item().getCost());
    }

    @Test
    void setBorrowedItemTest() {
        Book book = book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");
        loan.setBorrowed_item(book);
        assertEquals(book, loan.getBorrowed_item());
    }

    @Test
    void isLateTest() {
        assertFalse(loan.getLate());
    }

}
