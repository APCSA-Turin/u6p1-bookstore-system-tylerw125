package com.example.project;

public class BookStore {
    private Book[] books;
    private User[] users;

    public BookStore() {
        this.books = new Book[5];
        this.users = new User[10];
    }

    public Book[] getBooks() {
        return books;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public void addUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                return;
            }
        }
        System.out.println("User array is full!");
    }

    public void removeUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].equals(user)) {
                users[i] = null;
                return;
            }
        }
    }

    public void consolidateUsers() {
        int index = 0;
        User[] temp = new User[users.length];
        for (User user : users) {
            if (user != null) {
                temp[index++] = user;
            }
        }
        this.users = temp;
    }

    public void addBook(Book book) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = book;
                return;
            }
        }
        System.out.println("Book array is full!");
    }

    public void insertBook(Book book, int index) {
        if (index >= 0 && index < books.length) {
            books[index] = book;
        } else {
            System.out.println("Index out of bounds!");
        }
    }

    public void removeBook(Book book) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].equals(book)) {
                books[i] = null;
                return;
            }
        }
    }

    public String bookStoreBookInfo() {
        StringBuilder info = new StringBuilder();
        for (Book book : books) {
            if (book != null) {
                info.append(book.bookInfo()).append("\n");
            }
        }
        return info.toString();
    }

    public String bookStoreUserInfo() {
        StringBuilder info = new StringBuilder();
        for (User user : users) {
            if (user != null) {
                info.append(user.userInfo()).append("\n");
            }
        }
        return info.toString();
    }
}
