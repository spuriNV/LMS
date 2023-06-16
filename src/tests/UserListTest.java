package tests;

import Item.Book;
import library.staff.Staff;
import library.users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserListTest {

    private User name;
    private Staff staff;
    private LoanList list;

    private Loan loan;

    private Book book;
    private UserList userList;

    @BeforeEach
    void runBefore() {
        ArrayList<Loan> otherList = new ArrayList<Loan>();
        list = new LoanList(otherList);
        staff = new Staff("the_librarian");
        name = new User("user_name", "password", list);
        book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");

        Date thisDate = new Date();
        loan = new Loan(thisDate, book, false);
        userList = UserList.getInstance();
    }

    @Test
    void removeFromArrayTest() {
        userList.addToArray(name);
        userList.removeFromArray(name);

        ArrayList<User> useNewList = new ArrayList<>();
        assertEquals(useNewList, userList.getUserList());
        assertEquals(useNewList.size(), userList.getUserList().size());
    }

    @Test
    void getEmptyUserListTest() {
        ArrayList<User> useNewList = new ArrayList<>();
        assertEquals(useNewList, userList.getUserList());
        assertEquals(useNewList.size(), userList.getUserList().size());
    }

    @Test
    void addToArrayTest() {
        ArrayList<User> useNewList = new ArrayList<>();
        Staff otherStaff = new Staff("not_the_librarian");
        User other = new User("not_user_name", "password", list);

        userList.addToArray(other);
        useNewList.add(other);

        assertEquals(useNewList, userList.getUserList());
        assertEquals(useNewList.size(), userList.getUserList().size());

        userList.removeFromArray(other);

    }
}
