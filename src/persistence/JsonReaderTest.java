package persistence;

import library.staff.Librarian;
import library.staff.LibrarianList;
import library.tools.Database;
import library.tools.HashNode;
import library.users.User;
import library.users.UserList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentUserFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            UserList userList = UserList.getInstance();
            userList.setUserList(reader.readUserList());
            LibrarianList librarianList = LibrarianList.getInstance();
            librarianList.setLibrarianList(reader.readLibrarianList());
            Database database = Database.getInstance();
            database = reader.readBaseList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }



    @Test
    void testReaderEmptyLists() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLists.json");
        try {
            UserList userList = UserList.getInstance();
            userList.setUserList(reader.readUserList());
            LibrarianList librarianList = LibrarianList.getInstance();
            librarianList.setLibrarianList(reader.readLibrarianList());
            Database database = Database.getInstance();
            database.remove(251211.1126282523);
            database.remove(251211.1215132523);
            database = reader.readBaseList();

            assertEquals(0, userList.getUserList().size());
            assertEquals(0, librarianList.getLibrarianList().size());
            assertEquals(0, database.getSize());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralUserlist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUserList.json");
        try {
            UserList userList = UserList.getInstance();
            userList.setUserList(reader.readUserList());

            ArrayList<User> users = userList.getUserList();
            assertEquals(1, users.size());
            assertEquals("Mike", users.get(0).getUser_name());
            assertEquals("Mike1234&", users.get(0).getPassword());
            assertEquals("123-456-7890", users.get(0).getTelephone());
            assertEquals(0.0, users.get(0).getFineStatus());
            assertEquals(2, users.get(0).getItemLoans().getLoans().size());

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String issue = formatter.format(users.get(0).getItemLoans().getLoans().get(0).getIssue_date());
            String returner = formatter.format(users.get(0).getItemLoans().getLoans().get(0).getReturn_date());

            assertEquals("06/01/2007", issue);
            assertEquals("06/15/2007", returner);
            assertTrue(users.get(0).getItemLoans().getLoans().get(0).getLate());

            assertEquals("A Promised Land", users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getTitle());
            assertEquals(5, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getCopies());
            assertEquals(0, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getBorrowed());
            assertEquals(251211.1126282523, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getIsbn());

            HashMap<String, String> map = new HashMap<>();
            assertEquals(map, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getHistory());
            assertEquals(26.80, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getCost());
            assertEquals("audiobook", users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getType());

            String issue1 = formatter.format(users.get(0).getItemLoans().getLoans().get(1).getIssue_date());
            String returner1 = formatter.format(users.get(0).getItemLoans().getLoans().get(1).getReturn_date());

            assertEquals("03/01/2008", issue1);
            assertEquals("03/15/2008", returner1);
            assertTrue(users.get(0).getItemLoans().getLoans().get(1).getLate());

            assertEquals("A Promised Land", users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getTitle());
            assertEquals(5, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getCopies());
            assertEquals(0, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getBorrowed());
            assertEquals(251211.1126282523, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getIsbn());

            HashMap<String, String> otherMap = new HashMap<>();
            otherMap.put("Nick", "02/15/2005");
            otherMap.put("Mark", "01/13/2004");

            assertEquals(otherMap, users.get(0).getItemLoans().getLoans().get(1).getBorrowed_item().getHistory());
            assertEquals(26.80, users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getCost());
            assertEquals("audiobook", users.get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getType());


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralLibrarianlist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLibrarianList.json");
        try {
            LibrarianList librarianList = LibrarianList.getInstance();
            librarianList.setLibrarianList(reader.readLibrarianList());

            ArrayList<Librarian> list = librarianList.getLibrarianList();

            assertEquals(2, list.size());

            assertEquals("Nick", list.get(0).getUser_name());
            assertEquals("05/09/1996", list.get(0).getBirthdate());
            assertEquals("098-765-4321", list.get(0).getTelephone());

            assertEquals("Larry", list.get(1).getUser_name());
            assertEquals("04/07/2001", list.get(1).getBirthdate());
            assertEquals("123-456-7890", list.get(1).getTelephone());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralBaselist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBaseList.json");
        try {
            Database database = Database.getInstance();
            database = reader.readBaseList();

            assertEquals(1, database.getSize());

            ArrayList<HashNode> nodes = database.booksByAuthor(25);
            assertEquals(2, nodes.size());
            assertEquals("A Promised Land", nodes.get(0).getValue());
            assertEquals(251211.1126282523, nodes.get(0).getKey());

            assertEquals("Becoming", nodes.get(1).getValue());
            assertEquals(251211.1215132523, nodes.get(1).getKey());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}