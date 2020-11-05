// Pet Database: Basic database program that manages info about pets (name and age)
// Assignment 2 Part 2
// Class: CSC 422 Software Engineering
// Professor: Gregory Silver
// Date: 11/5/2020
// Version: 5.0
// Updates: Program handles errors now.

package edu.csp.mootzk;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pet Database Program\n");

        int userSelection = 0;
        int rowCount;
        String separator = "+-----------------------+";
        Scanner sc = new Scanner(System.in);
        String petDatabase = "petDatabase.txt";
        ArrayList<Pet> pets = new ArrayList<>();

        // Instantiate FileManagement class object
        FileManagement fm = new FileManagement();

        // If file doesn't exist, then create it
        File petDatabaseFile = fm.createDatabaseFile(petDatabase);

        while(true) {
            displayMenuOptions();
            try {
                userSelection = Integer.parseInt(sc.next());
            }
            catch(NumberFormatException e) {
                System.out.println("\nERROR! Invalid input...");
                // System.exit(1);
            }

            switch (userSelection) {
                case 1 -> {
                    // View all pets here
                    pets = fm.readDatabaseFile(petDatabaseFile);
                    fm.displayDatabase(pets);
                }
                case 2 -> {
                    pets = fm.readDatabaseFile(petDatabaseFile);

                    if (pets.size() >= 5) {
                        // If five pets already in database, print error message
                        System.out.println("\nError: Database is full.\n");
                    }
                    else {
                        // Add more pets here
                        System.out.println("\nAdd pets by typing name and age.\nType \"Done\" when done adding pets.\n");
                        int countPetsAdded = 0;
                        sc.nextLine(); // Used to catch empty line
                        while (true) {
                            if (pets.size() >= 5) {
                                // If 5 pets are added (max for database), break out of loop
                                System.out.println("\nMessage: Database limit reached (max of five pets).");
                                break;
                            }
                            else {
                                System.out.print("Add Pet (name, age): ");
                                String userInput = sc.nextLine().trim();

                                if (userInput.toLowerCase(Locale.ENGLISH).equals("done")) {
                                    break;
                                }
                                else {
                                    String regexStringPetInfo = "(^[a-zA-Z]+)(\\s+)([0-9]+$)";
                                    Pattern patternPetInfo = Pattern.compile(regexStringPetInfo);
                                    Matcher matchPetInfo = patternPetInfo.matcher(userInput);

                                    // Method to get boolean result whether pattern matches the matcher
                                    // Necessary for .group method functionality
                                    boolean matches = matchPetInfo.matches();

                                    if (matches) {
                                        String name = matchPetInfo.group(1);
                                        String petAge = matchPetInfo.group(3);
                                        int age = Integer.parseInt(petAge);

                                        if (age <= 0 || age > 20) {
                                            System.out.println("Error: " + age + " is not a valid age.\n");
                                        }
                                        else {
                                            Pet newPet = new Pet(name, age);
                                            pets.add(newPet);
                                            countPetsAdded++;
                                        }
                                    }
                                    else {
                                        System.out.println("Error: " + userInput + " is not a valid input\n");
                                    }
                                }
                            }
                        }

                        // Call add pets to database function
                        fm.addToDatabase(petDatabase, pets);
                        System.out.println("\n" + countPetsAdded + " pets added.\n");
                    }
                }

                case 3 -> {
                    // Update an existing pet here
                    pets = fm.readDatabaseFile(petDatabaseFile);
                    fm.displayDatabase(pets);

                    System.out.print("Enter the pet ID you want to update: ");
                    sc.nextLine(); // Used to catch empty line
                    int petID = Integer.parseInt(sc.nextLine());

                    if (petID < 0 || petID > pets.size() - 1) {
                        System.out.println("Error: ID " + petID + " does not exist.\n");
                    }
                    else {
                        System.out.print("\nEnter new name and new age: ");
                        String updatedPetInfo = sc.nextLine().trim();
                        String currentPetInfo = pets.get(petID).toString();
                        String regexStringPetInfo = "(^[a-zA-Z]+)(\\s+)([0-9]+$)";
                        Pattern patternPetInfo = Pattern.compile(regexStringPetInfo);
                        Matcher matchPetInfo = patternPetInfo.matcher(updatedPetInfo);
                        boolean matches = matchPetInfo.matches();

                        if (matches) {
                            String name = matchPetInfo.group(1);
                            String petAge = matchPetInfo.group(3);
                            int age = Integer.parseInt(petAge);
                            pets.get(petID).setName(name);
                            pets.get(petID).setAge(age);
                            fm.addToDatabase(petDatabase, pets);
                            System.out.println(currentPetInfo + " changed to " + pets.get(petID).toString() + ".\n");
                        }
                        else {
                            System.out.println("Invalid data entered...\n");
                        }
                    }
                }
                case 4 -> {
                    // Remove an existing pet here
                    fm.displayDatabase(pets);
                    System.out.print("Enter the pet ID to remove: ");
                    sc.nextLine(); // Used to catch empty line
                    int petID = Integer.parseInt(sc.nextLine());

                    if (petID < 0 || petID > pets.size() - 1){
                        System.out.println("Error: ID " + petID + " does not exist.\n");
                    }
                    else {
                        String petName = pets.get(petID).getName();
                        int petAge = pets.get(petID).getAge();
                        pets.remove(petID);
                        fm.addToDatabase(petDatabase, pets);
                        System.out.println(petName + " " + petAge + " is removed.\n");
                    }
                }
                case 5 -> {
                    // Search pets by name here
                    System.out.print("\nEnter a name to search: ");
                    sc.nextLine(); // Used to catch empty line
                    String nameSearch = sc.nextLine().toLowerCase();
                    rowCount = 0;
                    fm.displayTableHeader();
                    pets = fm.readDatabaseFile(petDatabaseFile);
                    for (int x = 0; x < pets.size(); x++) {
                        if (pets.get(x).getName().toLowerCase().contains(nameSearch)) {
                            System.out.printf("| %-3s| %-10s | %-4s|\n%17s\n", x, pets.get(x).getName(), pets.get(x).getAge(), separator);
                            rowCount++;
                        }
                    }
                    if (rowCount == 1) {
                        System.out.println(rowCount + " row in set.\n");
                    } else {
                        System.out.println(rowCount + " rows in set.\n");
                    }
                }
                case 6 -> {
                    // Search pets by age here
                    System.out.print("\nEnter an age to search: ");
                    sc.nextLine(); // Used to catch empty line
                    int ageSearch = Integer.parseInt(sc.nextLine());
                    rowCount = 0;
                    fm.displayTableHeader();
                    pets = fm.readDatabaseFile(petDatabaseFile);
                    for (int x = 0; x < pets.size(); x++) {
                        if (pets.get(x).getAge().equals(ageSearch)) {
                            System.out.printf("| %-3s| %-10s | %-4s|\n%17s\n", x, pets.get(x).getName(), pets.get(x).getAge(), separator);
                            rowCount++;
                        }
                    }
                    if (rowCount == 1) {
                        System.out.println(rowCount + " row in set.\n");
                    }
                    else {
                        System.out.println(rowCount + " rows in set.\n");
                    }
                }
                case 7 -> {
                    // Exit program here
                    System.out.println("\nGoodbye!");
                    sc.close(); // Close scanner
                    System.exit(0);
                }
            } // End of switch(userSelection)
        } // End of while(true) loop
    } // End of static void main

    static void displayMenuOptions() {
        System.out.println("What would you like to do?");
        System.out.println("\t1) View all pets");
        System.out.println("\t2) Add more pets");
        System.out.println("\t3) Update an existing pet");
        System.out.println("\t4) Remove an existing pet");
        System.out.println("\t5) Search pets by name");
        System.out.println("\t6) Search pets by age");
        System.out.println("\t7) Exit program");
        System.out.print("Your choice: ");
    }
} // End of Main.java

// Create Pet class
class Pet {
    String name;
    Integer age;

    // Constructor that takes name and speech String arguments
    Pet(String name, Integer age) {
        setName(name);
        setAge(age);
    }

    // Get method returns name variable value
    public String getName() {
        return name;
    }

    // Set method sets name variable value
    public void setName(String name) {
        this.name = name;
    }

    // Get method returns name variable value
    public Integer getAge() {
        return age;
    }

    // Set method sets name variable value
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return getName() + " " + getAge();
    }
}

class FileManagement {

    public File createDatabaseFile(String petDatabase) {
        File petDatabaseFile = new File(petDatabase);

        try {
            boolean newFile = petDatabaseFile.createNewFile();

            if (newFile) {
                System.out.println("Database file created: "  + petDatabaseFile.getAbsolutePath() + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return petDatabaseFile;
    }

    public void displayTableHeader() {
        String separator = "+-----------------------+";
        String headingOne = "ID";
        String headingTwo = "NAME";
        String headingThree = "AGE";

        System.out.printf("\n%17s\n| %-3s| %-10s | %-4s|\n%17s\n", separator, headingOne, headingTwo, headingThree, separator);

    }
    public void displayDatabase(ArrayList<Pet> pets) {
        displayTableHeader();

        String separator = ("+-----------------------+");
        int rowCount = 0;
        for (int x = 0; x < pets.size(); x++) {
            System.out.printf("| %-3s| %-10s | %-4s|\n%17s\n", x, pets.get(x).getName(), pets.get(x).getAge(), separator);
            rowCount++;
        }

        if (rowCount == 1) {
            System.out.println(rowCount + " row in set.\n");
        }
        else {
            System.out.println(rowCount + " rows in set.\n");
        }
    }

    public ArrayList<Pet> readDatabaseFile(File petDatabaseFile) {
        ArrayList<Pet> pets = new ArrayList<>();
        String regexStringPetInfo = "(^[a-zA-Z]+)(\\s+)([0-9]+$)";
        Pattern patternPetInfo = Pattern.compile(regexStringPetInfo);

        try {
            BufferedReader br = new BufferedReader(new FileReader(petDatabaseFile));
            String line = br.readLine();

            // DEBUG: System.out.println(line);
            while (line != null) {
                Matcher matchPetInfo = patternPetInfo.matcher(line);

                // Method to get boolean result whether pattern matches the matcher
                // Necessary for .group method functionality
                boolean matches = matchPetInfo.matches();

                if (matches) {
                    String name = matchPetInfo.group(1);
                    String petAge = matchPetInfo.group(3);
                    int age = Integer.parseInt(petAge);
                    Pet newPet = new Pet(name, age);
                    pets.add(newPet);
                    line = br.readLine();
                }
                else {
                    System.out.println("No matches");
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return pets;
    }

    public void addToDatabase(String database, ArrayList<Pet> pets) {
        try {
            FileWriter writeToDatabase = new FileWriter(database);
            for (Object pet : pets) {
                writeToDatabase.write(pet + "\n");
            }
            writeToDatabase.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
