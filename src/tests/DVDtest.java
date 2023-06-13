package tests;

import Item.DVD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DVDtest {
    private DVD book;

    @BeforeEach
    void RunBefore() { book = new DVD("Cars", 5, 0, 221113.13112829, "John Lasseter", 117, 26.80, "dvd"); }

    @Test
    void getTitleTest() {
        assertEquals("Cars", book.getTitle());
    }

    @Test
    void setTitleTest() {
        book.setTitle("Not the real title");
        assertEquals("Not the real title", book.getTitle());
    }

    @Test
    void getCopiesTest() {
        assertEquals(5, book.getCopies());
    }

    @Test
    void setCopiesTest() {
        book.setCopies(8);
        assertEquals(8, book.getCopies());
    }

    @Test
    void getBorrowedTest() {
        assertEquals(0, book.getBorrowed());
    }

    @Test
    void setBorrowedTest() {
        book.setBorrowed(8);
        assertEquals(8, book.getBorrowed());
    }

    @Test
    void getISBNTest() {
        assertEquals(221113.13112829, book.getIsbn());
    }

    @Test
    void setISBNtest() {
        book.setIsbn(8);
        assertEquals(8, book.getIsbn());
    }

    @Test
    void getDirectorTest() {
        assertEquals("John Lasseter", book.getDirector());
    }

    @Test
    void setDirectorTest() {
        book.setDirector("LeBron James");
        assertEquals("LeBron James", book.getDirector());
    }

    @Test
    void getTimeTest() {
        assertEquals(117, book.getTime());
    }

    @Test
    void setTimeTest() {
        book.setTime(2000);
        assertEquals(2000, book.getTime());
    }

    @Test
    void getCostTest() {
        assertEquals(26.80, book.getCost());
    }

    @Test
    void setCostTest() {
        book.setCost(30.00);
        assertEquals(30.00, book.getCost());
    }

    @Test
    void getTypeTest() {
        assertEquals("dvd", book.getType());
    }

    @Test
    void setTypeTest() {
        book.setType("LeBron James");
        assertEquals("LeBron James", book.getType());
    }

}
