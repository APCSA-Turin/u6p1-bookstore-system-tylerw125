package com.example.project;

public class User {
    // User attributes 
    private String name;
    private String id;
    private Book[] books;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
        this.books = new Book[5];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }
    // Uniform output (For GUI)
    public String bookListInfo() {
        String bookList = "";
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                bookList += "\n" + books[i].bookInfo();
            } else {
                bookList += "\nempty";
            }
        }
        return bookList;
    }

    public String userInfo() {
        return "Name: " + name + "\nId: " + id + "\nBooks: " + bookListInfo() + "\n";
    }
}
