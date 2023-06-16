package persistence;


import Item.Audiobook;
import Item.Book;
import library.staff.Librarian;
import library.staff.LibrarianList;
import library.tools.Database;
import library.tools.HashNode;
import library.users.LoanList;
import library.users.User;
import library.users.Loan;
import library.users.UserList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest  {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLists() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLists.json");

            UserList userList = UserList.getInstance();
            LibrarianList librarianList = LibrarianList.getInstance();
            Database database = Database.getInstance();

            userList.emptyList();
            librarianList.emptyList();
            database.emptyList();

            writer.open();
            writer.writeLists();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLists.json");
            userList.setUserList(reader.readUserList());
            librarianList.setLibrarianList(reader.readLibrarianList());
            database = reader.readBaseList();

            assertEquals(0, userList.getUserList().size());
            assertEquals(0, librarianList.getLibrarianList().size());
            assertEquals(0, database.getSize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLists() {
        try {

            UserList userList = UserList.getInstance();
            LibrarianList librarianList = LibrarianList.getInstance();
            Database baseList = Database.getInstance();

            userList.emptyList();
            librarianList.emptyList();
            baseList.emptyList();

            Audiobook audioBook = new Audiobook("A Promised Land", 5, 0, 251211.1126282523, "Barak Obama", 1680, 26.80, "audiobook");
            Book book = new Book("Becoming", 5, 0, 251211.1215132523, "Michelle Obama", 768, 26.80, "book");

            ArrayList<Loan> list = new ArrayList<Loan>();
            LoanList loans = new LoanList(list);
            Date oneDate = new Date(2007, 06, 01);
            Loan loan = new Loan(oneDate, audioBook, false);
            loans.addToList(loan);
            User user = new User("Mike", "Mike1234&", loans);
            userList.addToArray(user);

            Librarian librarian = new Librarian("Nick");
            librarian.setTelephone("098-765-4321");
            librarian.setBirthdate("05/09/1996");
            librarianList.addToArray(librarian);

            HashNode node = new HashNode(audioBook.getIsbn(), audioBook.getTitle());
            node.setItem(audioBook);
            baseList.add(node);

            HashNode node1 = new HashNode(book.getIsbn(), book.getTitle());
            node1.setItem(book);
            baseList.add(node1);

            assertEquals(2, baseList.levelOrder(node).size());

           JsonWriter writer = new JsonWriter("./data/testWriterGeneralLists.json");
            writer.open();
            writer.writeLists();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLists.json");
            userList.setUserList(reader.readUserList());
            librarianList.setLibrarianList(reader.readLibrarianList());
            baseList = reader.readBaseList();


           // assertEquals statements
            assertEquals("Mike", userList.getUserList().get(0).getUser_name());
            assertEquals("Mike1234&", userList.getUserList().get(0).getPassword());
            assertEquals(audioBook.getTitle(), userList.getUserList().get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getTitle());
            assertEquals(audioBook.getType(), userList.getUserList().get(0).getItemLoans().getLoans().get(0).getBorrowed_item().getType());

            assertEquals("Nick", librarian.getUser_name());
            assertEquals("098-765-4321", librarian.getTelephone());
            assertEquals("05/09/1996", librarian.getBirthdate());

            assertEquals(1, baseList.getSize());
            ArrayList<HashNode> nodes = baseList.booksByAuthor(25);
            assertEquals(2, nodes.size());
            assertEquals("A Promised Land", nodes.get(0).getValue());
            assertEquals(251211.1126282523, nodes.get(0).getKey());
            assertEquals("Becoming", nodes.get(1).getValue());
            assertEquals(251211.1215132523, nodes.get(1).getKey());

            userList.emptyList();
            librarianList.emptyList();
            baseList.emptyList();


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}