package com.example.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BookStoreGUI {
    public static void main(String[] args) {
        
        // Create BookStore instance
        BookStore store = new BookStore();
    
        // Add some books for testing
        store.addBook(new Book("The Great Gatsby", "Scott Fitzgerald", 1925, "979-8351145013", 3));
        store.addBook(new Book("1984", "George Orwell", 1949, "978-0451524935", 5));
    
        // Create JFrame (Main window)
        JFrame frame = new JFrame("BookStore Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center window on screen
    
        // Create JTabbedPane for sections
        JTabbedPane tabbedPane = new JTabbedPane();
    
        // Create a shared label for current ID
        JLabel currentIdLabel = new JLabel("Current ID: " + IdGenerate.getCurrentId());
    
        // Add "Books" tab
        tabbedPane.addTab("Books", createBooksPanel(store));
    
        // Add "Users" tab, passing the currentIdLabel
        tabbedPane.addTab("Users", createUsersPanel(store, currentIdLabel));
    
        // Add "ID Generator" tab, passing the currentIdLabel
        tabbedPane.addTab("ID Generator", createIdPanel(currentIdLabel));
    
        // Add the tabbedPane to the frame
        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    
    private static int i = 0;
    // https://stackoverflow.com/questions/310ZZZ   42605/java-swing-adding-multiple-lines-in-jtables-cell
    private static void makeBooksColumnMultiline(JTable table) {
    table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JTextArea textArea = new JTextArea(value == null ? "" : value.toString());
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(true);
            textArea.setFont(table.getFont());

            // Set background/foreground for selection
            if (isSelected) {
                textArea.setBackground(table.getSelectionBackground());
                textArea.setForeground(table.getSelectionForeground());
            } else {
                textArea.setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
            }

            // Adjust the row height dynamically
            int textHeight = textArea.getPreferredSize().height;
            if (table.getRowHeight(row) != textHeight) {
                table.setRowHeight(row, textHeight);
            }

            return textArea;
        }
    });
}

    // Create the "Books" panel
    private static JPanel createBooksPanel(BookStore store) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Table for showing books
        String[] columnNames = {"Title", "Author", "Year", "ISBN", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        updateBookTable(store, model);

        // Buttons for managing books
        JButton addButton = createFlatButton("Add Book");
        JButton removeButton = createFlatButton("Remove Book");
        JButton updateButton = createFlatButton("Refresh Table");
        JButton changeButton = createFlatButton("Change Book Info");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(updateButton);

        // Add Action Listeners
        addButton.addActionListener(e -> {
            store.addBook(new Book("New Book", "New Author", 2025, "000-0000000000", 1));
            updateBookTable(store, model);
        });

        removeButton.addActionListener(e -> {
            if (bookTable.getSelectedRow() != -1) {
                Book selectedBook = store.getBooks()[bookTable.getSelectedRow()];
                store.removeBook(selectedBook);
                updateBookTable(store, model);
            }
        }); 
        updateButton.addActionListener(e -> updateBookTable(store, model));

        changeButton.addActionListener(e -> {
            if (bookTable.getSelectedRow() != -1) {
                int selectedRow = bookTable.getSelectedRow();
                Book selectedBook = store.getBooks()[selectedRow];
                showEditBookDialog(store, selectedBook, model);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a book to edit.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add components to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    // Reset the user index tracker to 0
    private static void resetUserIndex() {
        i = 0;
    }

    // Create the "Users" panel
    private static JPanel createUsersPanel(BookStore store, JLabel currentIdLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Table for showing users
        String[] columnNames = {"Name", "ID", "Books Borrowed"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable userTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(userTable);
        updateUserTable(store, model);
        makeBooksColumnMultiline(userTable);
        // Buttons for managing users
        JButton addUserButton = createFlatButton("Add User");
        JButton removeUserButton = createFlatButton("Remove User");
        JButton updateButton = createFlatButton("Refresh Table");
        JButton changeIdButton = createFlatButton("Change User ID");
        JButton changeNameButton = createFlatButton("Change User Name");
        JButton addBookButton = createFlatButton("Add Book to User");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addUserButton);
        buttonPanel.add(removeUserButton);
        buttonPanel.add(changeIdButton);
        buttonPanel.add(changeNameButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(updateButton);
        
        // Add Action Listeners
        addUserButton.addActionListener(e -> {
            // Check if the current index is out of bounds before accessing it
            if (i >= store.getUsers().length) {
                JOptionPane.showMessageDialog(panel, "User array is full. Cannot add more users.", "User Limit Reached", JOptionPane.WARNING_MESSAGE);
            } else {
                // Check if the current index is null
                if (store.getUsers()[i] == null) {
                    String userId = IdGenerate.generateID();
                    store.addUser(new User("User " + userId, userId)); // Add the user at the next available spot
                    updateUserTable(store, model);
                    currentIdLabel.setText("Current ID: " + IdGenerate.getCurrentId());
                    i++; // Increment to check the next index for the next user
                } else {
                    i++; // Move to the next index if the current one is already filled
                }
            }
        });
        
        

        removeUserButton.addActionListener(e -> {
            if (userTable.getSelectedRow() != -1) {
                User selectedUser = store.getUsers()[userTable.getSelectedRow()];
                store.removeUser(selectedUser);
                updateUserTable(store, model);
                resetUserIndex();  // Reset the user index after removing a user
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a user to remove.", "No User Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> updateUserTable(store, model));

        changeIdButton.addActionListener(e -> {
            if (userTable.getSelectedRow() != -1) {
                User selectedUser = store.getUsers()[userTable.getSelectedRow()];
                showEditUserIdDialog(store, selectedUser, model);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a user to edit their ID.", "No User Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        changeNameButton.addActionListener(e -> {
            if (userTable.getSelectedRow() != -1) {
                User selectedUser = store.getUsers()[userTable.getSelectedRow()];
                showEditUserNameDialog(store, selectedUser, model);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a user to edit their name.", "No User Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        addBookButton.addActionListener(e -> {
            if (userTable.getSelectedRow() != -1) {
                User selectedUser = store.getUsers()[userTable.getSelectedRow()];
                
                // Check if the user already has 5 books
                if (selectedUser.getBooks().length > 5) {
                    JOptionPane.showMessageDialog(panel, "This user cannot borrow more than 5 books.", "Book Limit Reached", JOptionPane.WARNING_MESSAGE);
                } else {
                    showAddBookToUserDialog(store, selectedUser, model);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a user to add books.", "No User Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add components to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    private static void showEditUserNameDialog(BookStore store, User user, DefaultTableModel model) {
    JDialog dialog = new JDialog((Frame) null, "Edit User Name", true);
    dialog.setSize(300, 150);
    dialog.setLocationRelativeTo(null);
    dialog.setLayout(new GridLayout(2, 2, 10, 10));

    // Create input field for new name
    JTextField nameField = new JTextField(user.getName());
    dialog.add(new JLabel("New Name:"));
    dialog.add(nameField);

    // Buttons for saving or canceling
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    dialog.add(saveButton);
    dialog.add(cancelButton);

    // Save button logic
    saveButton.addActionListener(e -> {
        user.setName(nameField.getText().trim()); // Update the user's name
        updateUserTable(store, model); // Refresh the user table
        dialog.dispose(); // Close the dialog
    });

    // Cancel button logic
    cancelButton.addActionListener(e -> dialog.dispose()); // Close the dialog without saving

    dialog.setVisible(true); // Make the dialog visible
}


    // Create the "ID Generator" panel
    private static JPanel createIdPanel(JLabel currentIdLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Display current ID
        currentIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Buttons for generating and resetting IDs
        JButton generateButton = createFlatButton("Generate New ID");
        JButton resetButton = createFlatButton("Reset ID");
    
        // Add Action Listeners
        generateButton.addActionListener(e -> currentIdLabel.setText("Current ID: " + IdGenerate.generateID()));
        resetButton.addActionListener(e -> {
            IdGenerate.reset();
            currentIdLabel.setText("Current ID: " + IdGenerate.getCurrentId());
        });
    
        // Add components to the panel
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        panel.add(currentIdLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        panel.add(generateButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        panel.add(resetButton);
        return panel;
    }
    

    // Opens a dialog to edit the details of an existing book in the bookstore
private static void showEditBookDialog(BookStore store, Book book, DefaultTableModel model) {
    JDialog dialog = new JDialog((Frame) null, "Edit Book Info", true); // Create a modal dialog
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(null); // Center the dialog on the screen
    dialog.setLayout(new GridLayout(6, 2, 10, 10)); // Grid layout for labels and input fields

    // Pre-fill input fields with the current book's details
    JTextField titleField = new JTextField(book.getTitle());
    JTextField authorField = new JTextField(book.getAuthor());
    JTextField yearField = new JTextField(String.valueOf(book.getYearPublished()));
    JTextField isbnField = new JTextField(book.getIsbn());
    JTextField quantityField = new JTextField(String.valueOf(book.getQuantity()));

    // Add labels and input fields to the dialog
    dialog.add(new JLabel("Title:"));
    dialog.add(titleField);
    dialog.add(new JLabel("Author:"));
    dialog.add(authorField);
    dialog.add(new JLabel("Year:"));
    dialog.add(yearField);
    dialog.add(new JLabel("ISBN:"));
    dialog.add(isbnField);
    dialog.add(new JLabel("Quantity:"));
    dialog.add(quantityField);

    // Save and Cancel buttons
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    dialog.add(saveButton);
    dialog.add(cancelButton);

    // Action listener to save the updated book details
    saveButton.addActionListener(e -> {
        try {
            // Update the book's details with the values from the input fields
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setYearPublished(Integer.parseInt(yearField.getText()));
            book.setIsbn(isbnField.getText());
            book.setQuantity(Integer.parseInt(quantityField.getText()));

            // Update the book table in the UI
            updateBookTable(store, model);
            dialog.dispose(); // Close the dialog
        } catch (NumberFormatException ex) {
            // Show an error message if any input is invalid
            JOptionPane.showMessageDialog(dialog, "Invalid input. Please check your values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Close the dialog without saving changes
    cancelButton.addActionListener(e -> dialog.dispose());

    dialog.setVisible(true); // Display the dialog
}

// Opens a dialog to edit the ID of a user in the bookstore system
private static void showEditUserIdDialog(BookStore store, User user, DefaultTableModel model) {
    JDialog dialog = new JDialog((Frame) null, "Edit User ID", true);
    dialog.setSize(300, 150);
    dialog.setLocationRelativeTo(null); // Center the dialog
    dialog.setLayout(new GridLayout(2, 2, 10, 10));

    JTextField idField = new JTextField(user.getId()); // Pre-fill with the current user ID
    dialog.add(new JLabel("New ID:"));
    dialog.add(idField);

    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    dialog.add(saveButton);
    dialog.add(cancelButton);

    // Action listener to save the updated user ID
    saveButton.addActionListener(e -> {
        String newId = idField.getText();
        if (isUserIdUnique(store, newId)) {
            // Update the user's ID if it is unique
            user.setId(newId);
            if (Integer.parseInt(newId) > Integer.parseInt(IdGenerate.getCurrentId())) {
                IdGenerate.setCurrentId(Integer.parseInt(newId)); // Update the current ID generator
            }
            updateUserTable(store, model); // Update the user table in the UI
            dialog.dispose();
        } else {
            // Show an error message if the ID is not unique
            JOptionPane.showMessageDialog(dialog, "This ID is already taken. Please choose another.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Close the dialog without saving changes
    cancelButton.addActionListener(e -> dialog.dispose());

    dialog.setVisible(true);
}

// Checks if the given user ID is unique in the bookstore system
private static boolean isUserIdUnique(BookStore store, String id) {
    for (User user : store.getUsers()) {
        if (user != null && user.getId().equals(id)) {
            return false; // ID is already taken
        }
    }
    return true; // ID is unique
}

// Opens a dialog to add a book to a user's book list
private static void showAddBookToUserDialog(BookStore store, User user, DefaultTableModel model) {
    JDialog dialog = new JDialog((Frame) null, "Add Book to User", true);
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(null); // Center the dialog
    dialog.setLayout(new BorderLayout());

    // Create a list of books available in the store
    DefaultListModel<Book> bookListModel = new DefaultListModel<>();
    for (Book book : store.getBooks()) {
        bookListModel.addElement(book);
}
    JList<Book> bookList = new JList<>(bookListModel);
    bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow single selection
    dialog.add(new JScrollPane(bookList), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("Add");
    JButton cancelButton = new JButton("Cancel");
    buttonPanel.add(addButton);
    buttonPanel.add(cancelButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);

    // Action listener to add the selected book to the user's book list
    addButton.addActionListener(e -> {
        Book selectedBook = bookList.getSelectedValue();
        if (selectedBook != null) {
            boolean bookAlreadyAdded = false;

            // Check if the book is already in the user's list
            for (int i = 0; i < user.getBooks().length; i++) {
                if (user.getBooks()[i] != null && user.getBooks()[i].getTitle().equals(selectedBook.getTitle()) 
                    && user.getBooks()[i].getAuthor().equals(selectedBook.getAuthor())
                    && user.getBooks()[i].getYearPublished() == selectedBook.getYearPublished()
                    && user.getBooks()[i].getIsbn().equals(selectedBook.getIsbn())) {

                    // If the book is found, increase its quantity
                    user.getBooks()[i].setQuantity(user.getBooks()[i].getQuantity() + 1);
                    bookAlreadyAdded = true;
                    break;
    }
}

            // If the book wasn't already added, find the first empty slot and add it
            if (!bookAlreadyAdded) {
                for (int i = 0; i < user.getBooks().length; i++) {
                    if (user.getBooks()[i] == null) {
                        user.getBooks()[i] = new Book(
                                selectedBook.getTitle(),
                                selectedBook.getAuthor(),
                                selectedBook.getYearPublished(),
                                selectedBook.getIsbn(),
                                1
                        );
                        break;
                    }
                 }
            }

            updateUserTable(store, model); // Update the user table in the UI
            dialog.dispose();
        } else {
            // Show a warning if no book is selected
            JOptionPane.showMessageDialog(dialog, "Please select a book to add.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
        }
    });

    // Close the dialog without adding a book
    cancelButton.addActionListener(e -> dialog.dispose());

    dialog.setVisible(true);
 }

// Updates the book table in the UI with the latest data
private static void updateBookTable(BookStore store, DefaultTableModel model) {
    model.setRowCount(0); // Clear the existing rows
    for (Book book : store.getBooks()) {
        model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getYearPublished(), book.getIsbn(), book.getQuantity()});
    }
 }

// Updates the user table in the UI with the latest data
private static void updateUserTable(BookStore store, DefaultTableModel model) {
    model.setRowCount(0); // Clear the existing rows
    for (User user : store.getUsers()) {
        if (user != null) {
            StringBuilder booksDisplay = new StringBuilder();
            for (Book book : user.getBooks()) {
                if (book != null) {
                    booksDisplay.append(book.getTitle()).append(" (Qty: ").append(book.getQuantity()).append(")\n");
                }
            }
            if (booksDisplay.length() == 0) booksDisplay.append("empty"); // Display "empty" if the user has no books
            model.addRow(new Object[]{user.getName(), user.getId(), booksDisplay.toString().trim()});
        }
     }
}

// Creates a custom-styled button with hover effects
private static JButton createFlatButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(0, 122, 122)); // Default background color
    button.setForeground(Color.WHITE); // Text color
    button.setFont(new Font("Verdana", Font.PLAIN, 14)); // Font style
    button.setFocusPainted(false); // Remove focus border
    button.setBorderPainted(false); // Remove border
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(0, 175, 175)); // Hover color
        }

        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(0, 122, 122)); // Default color
        }
    });
    return button;
}
}
/*  Informational links \/\/\/\/\/\/
https://www.guru99.com/java-swing-gui.html/
https://www.youtube.com/watch?v=7aj4_bbWGgk
https://www.youtube.com/watch?v=qMh01cTbvYg
https://www.geeksforgeeks.org/java-swing-create-a-simple-text-editor/
https://myprojectideas.com/bookstore-management-system-java-project/
https://www.javatpoint.com/java-swing//
https://apps.mvhs.io/resources/codespaces/
https://www.geeksforgeeks.org/java-actionlistener-in-awt/
https://stackoverflow.com/questions/13978754/exception-handling-with-wrong-user-input-java */ 