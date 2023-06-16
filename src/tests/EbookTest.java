package tests;

import Item.Ebook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbookTest {

    private Ebook book;

    @BeforeEach
    void RunBefore() {
        book = new Ebook("Matilda", 5, 0, 141118.2311301922, "Roald Dahl", 239, 26.80, "ebook");
    }

    @Test
    void getTitleTest() {
        assertEquals("Matilda", book.getTitle());
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
        assertEquals(141118.2311301922, book.getIsbn());
    }

    @Test
    void setISBNtest() {
        book.setIsbn(8);
        assertEquals(8, book.getIsbn());
    }

    @Test
    void getAuthorTest() {
        assertEquals("Roald Dahl", book.getAuthor());
    }

    @Test
    void setAuthorTest() {
        book.setAuthor("LeBron James");
        assertEquals("LeBron James", book.getAuthor());
    }

    @Test
    void getPagesTest() {
        assertEquals(239, book.getPages());
    }

    @Test
    void setPagesTest() {
        book.setPages(2000);
        assertEquals(2000, book.getPages());
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
        assertEquals("ebook", book.getType());
    }

    @Test
    void setTypeTest() {
        book.setType("LeBron James");
        assertEquals("LeBron James", book.getType());
    }



}
