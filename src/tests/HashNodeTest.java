package tests;

import Item.Audiobook;
import Item.Book;
import library.tools.HashNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashNodeTest {

    private HashNode node;

    private HashNode other;

    private Audiobook audiobook;

    private Book book;

    @BeforeEach
    void RunBefore() {
        node = new HashNode(251211.1126282523, "A Promised Land");
        audiobook = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
        book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");
        node.setItem(audiobook);
        other = new HashNode(251211.1215132523, "Becoming");
    }


    @Test
    void getItemTest() {
        assertEquals(audiobook, node.getItem());
    }

    @Test
    void setItemTest() {
        node.setItem(book);
        assertEquals(book, node.getItem());
    }

    @Test
    void getKeyTest() {
        assertEquals(251211.112628252, node.getKey());
    }

    @Test
    void setKeyTest() {
        node.setKey(123);
        assertEquals(123, node.getKey());
    }

    @Test
    void getValueTest() {
        assertEquals("A Promised Land", node.getValue());
    }

    @Test
    void setValueTest() {
        node.setValue("abc");
        assertEquals("abc", node.getValue());
    }

    @Test
    void getHeightTest() {
        assertEquals(1, node.getHeight());
    }

    @Test
    void setHeightTest() {
        node.setHeight(2);
        assertEquals(2, node.getHeight());
    }

    @Test
    void getLeftNodeTest() {
        assertEquals(null, node.getLeft());
    }

    @Test
    void setLeftNodeTest() {
        node.setLeft(other);
        assertEquals(other, node.getLeft());
    }


    @Test
    void getRightNodeTest() {
        assertEquals(null, node.getRight());
    }

    @Test
    void setRightNodeTest() {
        node.setRight(other);
        assertEquals(other, node.getRight());
    }


}
