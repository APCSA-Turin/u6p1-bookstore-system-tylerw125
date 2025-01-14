package com.example.project;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestBookStore {


    @Test
    public void testAddUser(){
        BookStore store = new BookStore(); //create a new bookstore with 6 empty books
        IdGenerate.generateID();
        User u1 = new User("John",IdGenerate.getCurrentId());
        IdGenerate.generateID();
        User u2 = new User("Jane",IdGenerate.getCurrentId());
        IdGenerate.generateID();
        User u3 = new User("Mary",IdGenerate.getCurrentId());
        IdGenerate.generateID();
        User u4 = new User("Alex",IdGenerate.getCurrentId());
        store.addUser(u1);store.addUser(u2);store.addUser(u3);store.addUser(u4);
        User[] expected = {u1,u2,u3,u4,null,null,null,null,null,null};
        assertArrayEquals(expected, store.getUsers(),"array should have 4 users and 6 nulls");
    }

    @Test 
    public void testRemoveUser(){ //tests the consolidate too
        IdGenerate.reset();
        BookStore store = new BookStore(); //create a new bookstore with 6 empty books
        IdGenerate.generateID();
        User u1 = new User("John",IdGenerate.getCurrentId());
        IdGenerate.generateID();
        User u2 = new User("Jane",IdGenerate.getCurrentId());
        IdGenerate.generateID();
        User u3 = new User("Mary",IdGenerate.getCurrentId());
        IdGenerate.generateID();
        User u4 = new User("Alex",IdGenerate.getCurrentId());
        store.addUser(u1);store.addUser(u2);store.addUser(u3);store.addUser(u4);

        store.removeUser(u2);
        User[] expected = {u1,u3,u4,null,null,null,null,null,null,null};
        assertArrayEquals(expected, store.getUsers());

        store.removeUser(u1);
        User []expected2 = {u3,u4,null,null,null,null,null,null,null,null};
        assertArrayEquals(expected2, store.getUsers());

        store.removeUser(u4);
        User []expected3 = {u3,null,null,null,null,null,null,null,null,null};
        assertArrayEquals(expected3, store.getUsers());

        store.removeUser(u3);
        User []expected4 = {null,null,null,null,null,null,null,null,null,null};
        assertArrayEquals(expected4, store.getUsers());
    }

    @Test
    public void testConsolidateUsers(){
        BookStore store = new BookStore(); //create a new bookstore with 6 empty books
        User x = new User("x","100");
        User y = new User("y","101");
        User[] users = {x,null,null,y};
        store.setUsers(users);
        store.consolidateUsers();
        User[] expected = {x,y,null,null};
        assertArrayEquals(expected, store.getUsers(),"consolidate() did not work");

    }

    @Test
    public void testAddBook(){
        BookStore store = new BookStore(); 
        Book b1 = new Book("The Great Gatsby","Scott Fitzgerald", 1925, "979-8351145013",3);
        Book b2 = new Book("The Outliers", "Malcolm Gladwell",2008,"978-0316017930",1);
        Book b3 = new Book("1984", "George Orwell", 1949, "978-0451524935", 5);
        Book b4 = new Book("Brave New World", "Aldous Huxley", 1932, "978-0060850524", 3);
        Book b5 = new Book("Test","Author",1900, "1234", 1);
        store.addBook(b1);store.addBook(b2);store.addBook(b3);store.addBook(b4);store.addBook(b5);
        Book[] expected = {b1,b2,b3,b4,b5};
        assertArrayEquals(expected, store.getBooks(), "Book array does not match");
    }

    @Test 
    public void testRemoveBook(){
        BookStore store = new BookStore(); 
        Book b1 = new Book("The Great Gatsby","Scott Fitzgerald", 1925, "979-8351145013",3);
        Book b2 = new Book("The Outliers", "Malcolm Gladwell",2008,"978-0316017930",1);
        Book b3 = new Book("1984", "George Orwell", 1949, "978-0451524935", 5);
        Book b4 = new Book("Brave New World", "Aldous Huxley", 1932, "978-0060850524", 3);
        Book b5 = new Book("Test","Author",1900, "1234", 1);
        store.addBook(b1);store.addBook(b2);store.addBook(b3);store.addBook(b4);store.addBook(b5);
        store.removeBook(b1);store.removeBook(b1);
        Book[] expected = {b1,b2,b3,b4,b5};
        assertArrayEquals(expected, store.getBooks(), "Book array does not match");
        assertEquals(1,store.getBooks()[0].getQuantity(), "There should be only one copy left for the great gatsby book");
        store.removeBook(b1); //b1 should be removed. testing consolidation
        Book[] expected2 = {b2,b3,b4,b5};
        assertArrayEquals(expected2, store.getBooks());
        store.removeBook(b5);
        Book[] expected3 = {b2,b3,b4};
        assertArrayEquals(expected3, store.getBooks());
        store.removeBook(b3);store.removeBook(b3);store.removeBook(b3);store.removeBook(b3);store.removeBook(b3);
        Book[] expected4 = {b2,b4};
        assertArrayEquals(expected4, store.getBooks());
    }

    @Test 
    public void testInsertBooK(){
        BookStore store = new BookStore(); 
        Book b1 = new Book("The Great Gatsby","Scott Fitzgerald", 1925, "979-8351145013",3);
        Book b2 = new Book("The Outliers", "Malcolm Gladwell",2008,"978-0316017930",1);
        Book b3 = new Book("1984", "George Orwell", 1949, "978-0451524935", 5);
        Book b4 = new Book("Brave New World", "Aldous Huxley", 1932, "978-0060850524", 3);
        Book b5 = new Book("Test","Author",1900, "1234", 1);
        store.addBook(b1);
        store.addBook(b4);
        store.insertBook(b2, 1);
        store.insertBook(b3,2);
        store.insertBook(b5,4);
        Book[] expected = {b1,b2,b3,b4,b5};
        assertArrayEquals(expected, store.getBooks());
    }
}
