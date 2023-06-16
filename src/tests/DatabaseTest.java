package tests;

import Item.Audiobook;
import Item.Book;
import Item.DVD;
import Item.Ebook;
import library.tools.Database;
import library.tools.HashNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private Database base;

    private Audiobook audiobook;

    private Book book;

    private Ebook ebook;

    private DVD dvd;

    private HashNode node;

    private HashNode other;

    private HashNode another;

    private HashNode oneMore;

    @BeforeEach
    void RunBefore() {
        base = Database.getInstance();
        audiobook = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
        book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");
        dvd = new DVD("Cars", 5, 0, 221113.13112829, "John Lasseter", 117, 26.80, "dvd");
        ebook = new Ebook("Matilda", 5, 0, 141118.2311301922, "Roald Dahl", 239, 26.80, "ebook");

        node = new HashNode(251211.1126282523, "A Promised Land");
        other = new HashNode(251211.1215132523, "Becoming");
        another = new HashNode(221113.13112829, "Cars");
        oneMore = new HashNode(141118.2311301922, "Matilda");
    }

    @Test
    void getSmallSizeTest() {
        assertEquals(0, base.getSize());
    }

    @Test
    void getBigSizeTest() {
        node.setItem(audiobook);
        base.add(node);
        assertEquals(1, base.getSize());
        other.setItem(book);
        base.add(other);
        assertEquals(1, base.getSize());
        another.setItem(dvd);
        base.add(another);
        assertEquals(2, base.getSize());

        base.remove(node.getKey());
        base.remove(other.getKey());
        base.remove(another.getKey());

        assertEquals(0, base.getSize());
    }

    @Test
    void isTrueEmptyTest() {
        assertTrue(base.isEmpty());
    }

    @Test
    void addTest() {
        node.setItem(audiobook);
        base.add(node);
        assertEquals(1, base.getSize());
        assertFalse(base.isEmpty());

        base.remove(node.getKey());

        assertEquals(0, base.getSize());
    }

    @Test
    void removeTest() {
        node.setItem(audiobook);
        base.add(node);
        assertEquals(1, base.getSize());
        assertFalse(base.isEmpty());

        base.remove(node.getKey());

        assertEquals(0, base.getSize());
        assertTrue(base.isEmpty());

    }

    @Test
    void searchKeyEmptyTest() {
        assertNull(base.search(251211.1126282523));
    }

    @Test
    void booksByAuthorTest() {
        node.setItem(audiobook);
        base.add(node);
        assertEquals(1, base.getSize());
        other.setItem(book);
        base.add(other);
        assertEquals(1, base.getSize());

        ArrayList<HashNode> nodes = new ArrayList<>();
        nodes.add(node);
        nodes.add(other);

        assertEquals(nodes, base.booksByAuthor(25));

        base.remove(node.getKey());
        base.remove(other.getKey());

        assertEquals(0, base.getSize());


    }

    @Test
    void levelOrderTest() {
        node.setItem(audiobook);
        base.add(node);
        assertEquals(1, base.getSize());
        other.setItem(book);
        base.add(other);
        assertEquals(1, base.getSize());

        ArrayList<HashNode> nodes = new ArrayList<>();
        nodes.add(node);
        nodes.add(other);
        assertEquals(nodes, base.levelOrder(node));

        base.remove(node.getKey());
        base.remove(other.getKey());

        assertEquals(0, base.getSize());

    }

    @Test
    void emptytoStringTest() {
        assertEquals("{" +
                "buckets=" + "26" +
                ", size=" + "0" +
                '}', base.toString());
    }

    @Test
    void toStringTest() {
        node.setItem(audiobook);
        base.add(node);
        assertEquals(1, base.getSize());
        other.setItem(book);
        base.add(other);
        assertEquals(1, base.getSize());
        another.setItem(dvd);
        base.add(another);
        assertEquals(2, base.getSize());

        assertEquals("{" +
                "buckets=" + "26" +
                ", size=" + "2" +
                '}', base.toString());

        base.remove(node.getKey());
        base.remove(other.getKey());
        base.remove(another.getKey());

        assertEquals(0, base.getSize());
    }
}
