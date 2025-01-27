package com.example.project;

public class BookStore {
    private Book[] books; // Array to store books
    private User[] users = new User[10]; // Array to store users, initialized with a fixed size of 10

    public BookStore() { }
        
    // Getter for books array
    public Book[] getBooks() {
        return books;
    }

    // Getter for users array
    public User[] getUsers() {
        return users;
    }

    // Setter for users array
    public void setUsers(User[] users) {
        this.users = users;
    }

    // Adds a new user to the users array
    public void addUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) { // Find the first empty slot
                users[i] = user;
                return;
            }
        }
        System.out.println("User array is full!");
    }

    // Removes a user from the users array
    public void removeUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == user) { // Match user reference
                users[i] = null; // Remove user by setting the slot to null
            }
        }
        consolidateUsers(); // Reorganize the array to remove gaps
    }

    // Consolidates the users array to remove null gaps
    public void consolidateUsers() {
        int index = 0;
        User[] temp = new User[users.length]; // Temporary array
        for (User user : users) {
            if (user != null) { // Only add non-null users
                temp[index++] = user;
            }
        }
        this.users = temp; // Replace the old array with the consolidated one
    }

    // Adds a new book to the books array
    public void addBook(Book book) {
        // Check if books array is null
        if (books == null) {
            // Initialize the array with the first book
            books = new Book[1];
            books[0] = book;
            return;
        }
    
        // Check if the book already exists in the array
        for (int i = 0; i < books.length; i++) {
            if (books[i].getTitle().equals(book.getTitle())) { // Match by title
                books[i].setQuantity(books[i].getQuantity() + book.getQuantity()); // Increase quantity
                return;
            }
        }
    
        // If the book does not exist, create a new array with an extra slot
        Book[] temp = new Book[books.length + 1];
        for (int i = 0; i < books.length; i++) {
            temp[i] = books[i]; // Copy existing books
        }
        temp[books.length] = book; // Add the new book to the end of the array
        books = temp; // Replace the old array
    }
    
    // Inserts a book at a specific index in the books array
    public void insertBook(Book book, int index) {
        if (index < 0 || index > books.length) { // Check for invalid index
            System.out.println("Index out of bounds!");
            return;
        }
    
        Book[] temp = new Book[books.length + 1]; // Temporary array with one extra slot
    
        for (int i = 0; i < index; i++) {
            temp[i] = books[i]; // Copy elements before the index
        }
    
        temp[index] = book; // Insert the new book at the specified index
    
        for (int i = index; i < books.length; i++) {
            temp[i + 1] = books[i]; // Copy elements after the index
        }
    
        books = temp; // Replace the old array
    }
    
    // Removes a book from the books array
    public void removeBook(Book book) {
        if (books == null || books.length == 0) { // Check if the array is empty
            System.out.println("No books to remove!");
            return;
        }
    
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getTitle().equals(book.getTitle())) { // Match by title
                books[i].setQuantity(books[i].getQuantity() - 1); // Decrease quantity
    
                if (books[i].getQuantity() == 0) { // If quantity reaches zero
                    Book[] temp = new Book[books.length - 1]; // Create a smaller array
    
                    for (int j = 0, k = 0; j < books.length; j++) {
                        if (j != i) { // Skip the book to remove
                            temp[k++] = books[j];
                        }
                    }
    
                    books = temp; // Replace the old array
                }
                return; // Exit the method after processing
            }
        }
    
        System.out.println("Book not found in the store!"); // If book is not found
    }
    
    // Generates information about all books in the store
    public String bookStoreBookInfo() {
        StringBuilder info = new StringBuilder();
        for (Book book : books) {
            if (book != null) { // Only include non-null books
                info.append(book.bookInfo()).append("\n");
            }
        }
        return info.toString();
    }

    // Generates information about all users in the store
    public String bookStoreUserInfo() {
        StringBuilder info = new StringBuilder();
        for (User user : users) {
            if (user != null) { // Only include non-null users
                info.append(user.userInfo()).append("\n");
            }
        }
        return info.toString();
    }
}

