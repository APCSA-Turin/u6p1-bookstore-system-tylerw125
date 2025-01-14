package com.example.project;
import java.util.Scanner;

public class ExtraCreditInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookStore bookStore = new BookStore();
        int choice;

        do {
            System.out.println("******** Welcome to the Bookstore Management System ********");
            System.out.println("1. Add a new Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Insert a Book at Specific Position");
            System.out.println("4. Show All Books");
            System.out.println("5. Register a User");
            System.out.println("6. Remove a User");
            System.out.println("7. Show All Registered Users");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.next();
                    System.out.print("Enter Author Name: ");
                    String author = scanner.next();
                    System.out.print("Enter Year Published: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.next();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    Book newBook = new Book(title, author, year, isbn, quantity);
                    bookStore.addBook(newBook);
                    System.out.println("Book added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Book Title to Remove: ");
                    String removeTitle = scanner.next();
                    Book bookToRemove = bookStore.findBook(removeTitle);
                    if (bookToRemove != null) {
                        bookStore.removeBook(bookToRemove);
                        System.out.println("Book removed successfully!");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Position: ");
                    int index = scanner.nextInt();
                    System.out.print("Enter Book Title: ");
                    String insertTitle = scanner.next();
                    System.out.print("Enter Author Name: ");
                    String insertAuthor = scanner.next();
                    System.out.print("Enter Year Published: ");
                    int insertYear = scanner.nextInt();
                    System.out.print("Enter ISBN: ");
                    String insertIsbn = scanner.next();
                    System.out.print("Enter Quantity: ");
                    int insertQuantity = scanner.nextInt();
                    Book insertedBook = new Book(insertTitle, insertAuthor, insertYear, insertIsbn, insertQuantity);
                    bookStore.insertBook(insertedBook, index);
                    System.out.println("Book inserted successfully!");
                    break;

                case 4:
                    System.out.println("Books in the Store:");
                    System.out.println(bookStore.bookStoreBookInfo());
                    break;

                case 5:
                    System.out.print("Enter User Name: ");
                    String userName = scanner.next();
                    String userId = IdGenerate.generateID();
                    User newUser = new User(userName, userId);
                    bookStore.addUser(newUser);
                    System.out.println("User registered successfully! ID: " + userId);
                    break;

                case 6:
                    System.out.print("Enter User ID to Remove: ");
                    String userRemoveId = scanner.next();
                    User userToRemove = bookStore.findUser(userRemoveId);
                    if (userToRemove != null) {
                        bookStore.removeUser(userToRemove);
                        System.out.println("User removed successfully!");
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 7: // Show all registered users
                    System.out.println("Registered Users:");
                    System.out.println(bookStore.bookStoreUserInfo());
                    break;

                case 0: // Exit
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }
}

}