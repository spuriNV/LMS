package tests;

import Item.Audiobook;
import library.staff.Staff;
import library.users.Loan;
import library.users.LoanList;
import library.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanListTest {
    private LoanList list;
    private User name;

    private Audiobook book;

    private Staff staff;

    @BeforeEach
    void runBefore() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        list = new LoanList(otherList);
        staff = new Staff("the_librarian");
        name = new User("user_name", "password", list);
        book = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");

    }

    @Test
    void getEmptyLoansList() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        assertEquals(otherList, list.getLoans());
    }

    @Test
    void getLoansList() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        Date thisDate = new Date();
        Loan loan = new Loan(thisDate, book, false);
        otherList.add(loan);
        list.addToList(loan);

        assertEquals(otherList.size(), list.getLoans().size());
        assertEquals(otherList.get(0).getIssue_date().getDay(), list.getLoans().get(0).getIssue_date().getDay());

    }

    @Test
    void addToListTest() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        Date thisDate = new Date();
        Loan loan = new Loan(thisDate,book, false);
        list.addToList(loan);
        otherList.add(loan);
        assertEquals(otherList.size(), list.getLoans().size());
        assertEquals(otherList.get(0).getIssue_date().getDay(), list.getLoans().get(0).getIssue_date().getDay());

    }

    @Test
    void removeFromListTest() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        Date thisDate = new Date();
        Loan loan = new Loan(thisDate, book, false);
        list.addToList(loan);
        otherList.add(loan);
        assertEquals(otherList.size(), list.getLoans().size());

        otherList.remove(loan);
        list.removeFromList(loan);
        assertEquals(otherList, list.getLoans());
        assertEquals(otherList.size(), list.getLoans().size());

    }

    @Test
    void calculateFineTest() {
        assertEquals(0, list.calculateFine());

        Date thisDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(thisDate);

        Loan loan = new Loan(thisDate, book, false);

        c.add(Calendar.DATE, -16);
        Date newDate = c.getTime();

        loan.setReturn_date(newDate);

        list.addToList(loan);
        assertEquals(26.80, list.calculateFine());


    }
}
