package tests;

import library.staff.Librarian;
import library.tools.Database;
import library.tools.HashNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LibrarianTest {
    private Librarian librarian;
    private Database base;

    @BeforeEach
    void RunBefore() {
        librarian = new Librarian("user_name");
        base = Database.getInstance();
    }

    @Test
    void addToDatabaseTest() {
        HashNode node = new HashNode(251211.1126282523, "A Promised Lan");
        librarian.addToDatabase(node);
        assertEquals(node.getValue(), base.search(251211.1126282523).getValue());
        assertEquals(node.getKey(), base.search(251211.1126282523).getKey());
        assertEquals(node.getHeight(), base.search(251211.1126282523).getHeight());

        HashNode node1 = new HashNode(251211.1126282523, "A Promised Land");
        librarian.addToDatabase(node1);
        assertEquals(node1.getValue(), base.search(251211.1126282523).getValue());
        assertEquals(node1.getKey(), base.search(251211.1126282523).getKey());
        assertEquals(node1.getHeight(), base.search(251211.1126282523).getHeight());

        assertFalse(base.isEmpty());

        librarian.removeFromDatabase(251211.1126282523);
        assertEquals(0, base.booksByAuthor(25).size());
    }

    @Test
    void removeFromDatabaseTest() {

        HashNode node = new HashNode(251211.1126282523, "A Promised Land");
        HashNode node1 = new HashNode(251211.1215132523, "Becoming");
        HashNode node2 = new HashNode(251113.13112829, "Cars?");
        HashNode node3 = new HashNode(251118.2311301922, "Matilda?");

        librarian.addToDatabase(node);
        librarian.addToDatabase(node1);
        librarian.addToDatabase(node2);
        librarian.addToDatabase(node3);


        assertEquals(node1, node.getRight());
        assertEquals(node2, node.getLeft());
        assertEquals(node3, node2.getRight());

        librarian.removeFromDatabase(251211.1126282523);
        assertEquals(null, base.search(251211.1126282523));

       assertEquals(3, base.booksByAuthor(25).size());
       assertEquals(251113.13112829, base.booksByAuthor(25).get(0).getKey());

       librarian.removeFromDatabase(251211.1215132523);
       librarian.removeFromDatabase(251113.13112829);
       librarian.removeFromDatabase(251118.2311301922);
        assertEquals(0, base.booksByAuthor(25).size());

    }

    @Test
    void toStringTest() {
        assertEquals("Librarian{" +
                ", telephone='" + librarian.getTelephone() + '\'' +
                ", user_name='" + librarian.getUser_name() + '\'' +
                ", birthdate='" + librarian.getBirthdate() + '\'' +
                '}', librarian.toString());
    }
}
