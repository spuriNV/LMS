package tests;

import library.staff.Librarian;
import library.tools.Database;
import library.tools.HashNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(null, base.search(123.345));
        HashNode node = new HashNode(123.345, "123");
        librarian.addToDatabase(node);
        HashNode node1 = new HashNode(123.345, "123");
        assertEquals(node, base.search(123.345));
        assertEquals(node1.getHeight(), base.search(123.345).getHeight());
        assertEquals(node1.getValue(), base.search(123.345).getValue());
        assertEquals(0, base.search(123.345).getHeight());
    }

    @Test
    void removeFromDatabaseTest() {
        HashNode node = new HashNode(123.345, "123");
        librarian.addToDatabase(node);
        HashNode node1 = new HashNode(123.345, "123");
        assertEquals(node, base.search(123.345));
        librarian.removeFromDatabase(123.345);
        assertEquals(null, base.search(123.345));

    }

    @Test
    void toStringTest() {
        assertEquals("Librarian{" +
                "address='" + "" + '\'' +
                ", telephone='" + "" + '\'' +
                ", user_name='" + "user_name" + '\'' +
                ", gender=" + 'f' +
                ", birthdate='" + "27/03/1997" + '\'' +
                '}', librarian.toString());
    }
}
