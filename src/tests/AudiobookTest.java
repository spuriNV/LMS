package tests;

import Item.Audiobook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AudiobookTest {
    private Audiobook book;

    @BeforeEach
    void RunBefore() { book = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook"); }

    @Test
    void getTitleTest() {
        assertEquals("A Promised Land", book.getTitle());
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
        assertEquals(251211.1126282523, book.getIsbn());
    }

    @Test
    void setISBNtest() {
        book.setIsbn(8);
        assertEquals(8, book.getIsbn());
    }

    @Test
    void getAuthorTest() {
        assertEquals("Barak Obama", book.getAuthor());
    }

    @Test
    void setAuthorTest() {
        book.setAuthor("LeBron James");
        assertEquals("LeBron James", book.getAuthor());
    }

    @Test
    void getTimeTest() {
        assertEquals(1680, book.getTime());
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
        book.setTime(30.00);
        assertEquals(30.00, book.getTime());
    }

    @Test
    void getTypeTest() {
        assertEquals("audiobook", book.getType());
    }

    @Test
    void setTypeTest() {
        book.setType("LeBron James");
        assertEquals("LeBron James", book.getType());
    }

}