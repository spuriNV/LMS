package tests;

import Item.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    private Book book;

    @BeforeEach
    void RunBefore() {
         book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");
    }

    @Test
    void testFirstConstructor() {
        assertEquals("Becoming", book.getTitle());
        assertEquals(5, book.getCopies());
        assertEquals(0, book.getBorrowed());
        assertEquals(251211.1215132523, book.getIsbn());
        assertEquals("Michelle Obama", book.getAuthor());
        assertEquals(768, book.getPages());
        assertEquals(26.80, book.getCost());
    }

    @Test
    void getTitleTest() {
        assertEquals("Becoming", book.getTitle());
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
        assertEquals(251211.1215132523, book.getIsbn());
    }

    @Test
    void setISBNtest() {
        book.setIsbn(8);
        assertEquals(8, book.getIsbn());
    }

    @Test
    void getAuthorTest() {
        assertEquals("Michelle Obama", book.getAuthor());
    }

    @Test
    void setAuthorTest() {
        book.setAuthor("LeBron James");
        assertEquals("LeBron James", book.getAuthor());
    }

    @Test
    void getPagesTest() {
        assertEquals(768, book.getPages());
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
        assertEquals("book", book.getType());
    }

    @Test
    void setTypeTest() {
        book.setType("LeBron James");
        assertEquals("LeBron James", book.getType());
    }

}
