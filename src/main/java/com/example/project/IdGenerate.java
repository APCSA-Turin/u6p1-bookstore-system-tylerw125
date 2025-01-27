package com.example.project;

public class IdGenerate {
    // Private static attribute for currentID
    private static int currentId = 99;

    // Empty constructor
    public IdGenerate() {}

    // CurrentID as a string
    public static String getCurrentId() {
        return String.valueOf(currentId);
    }

    public static int setCurrentId(int newId) {
        return currentId = newId;
    }


    // Reset the ID back to 99
    public static void reset() {
        currentId = 99;
    }

    // Method to generate a new ID (increments the current ID by 1)
    public static String generateID() {
        currentId++;
        return String.valueOf(currentId);
    }
}
