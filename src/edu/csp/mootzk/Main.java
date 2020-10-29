// Pet Database: Basic database program that manages info about pets (name and age)
// Assignment 1 Part 2
// Class: CSC 422 Software Engineering
// Professor: Gregory Silver
// Date: 10/29/2020
// Version: 2.0

package edu.csp.mootzk;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        ArrayList<Pet> pets = new ArrayList<>();

        System.out.println("Pet Database Program\n");

        int userSelection = 0;
        int rowCount;

        while(true) {
            displayMenuOptions();
            Scanner sc = new Scanner(System.in);
            try {
                userSelection = Integer.parseInt(sc.next());
            }
            catch(NumberFormatException e) {
                System.out.println("\nERROR! Invalid input...");
                System.exit(1);
            }

            switch (userSelection) {
                case 1 -> {
                    // View all pets here
                    System.out.println("\n+----------------------+\n" +
                            "| ID | NAME      | AGE |\n" +
                            "+----------------------+");
                    rowCount = 0;
                    for (int x = 0; x < pets.size(); x++) {
                        System.out.println("|  " + x + pets.get(x).toString());
                        rowCount++;
                    }
                    System.out.println("+----------------------+");
                    if (rowCount == 1) {
                        System.out.println(rowCount + " row in set.\n");
                    } else {
                        System.out.println(rowCount + " rows in set.\n");
                    }
                }
                case 2 -> {
                    // Add more pets here
                    System.out.println("\nAdd pets by typing name and age.\nType \"Done\" when done adding pets.\n");
                    int countPetsAdded = 0;
                    sc.nextLine(); // Used to catch empty line ****************
                    while (true) {
                        System.out.print("Add Pet (name, age): ");
                        String userInput = sc.nextLine().trim();

                        if (userInput.toLowerCase(Locale.ENGLISH).equals("done")) {
                            break;
                        } else {
                            String regexStringPetInfo = "(^[a-zA-Z]+)(\\s+)([0-9]+$)";
                            Pattern patternPetInfo = Pattern.compile(regexStringPetInfo);
                            Matcher matchPetInfo = patternPetInfo.matcher(userInput);

                            // Method to get boolean result whether pattern matches the matcher
                            // Necessary for .group method functionality
                            matchPetInfo.matches();
// NOT NEEDED???            matchPetInfo.groupCount();

                            try {
                                String name = matchPetInfo.group(1);
                                String petAge = matchPetInfo.group(3);
                                int age = Integer.parseInt(petAge);
                                Pet newPet = new Pet(name, age);
                                pets.add(newPet);
                                countPetsAdded++;
                            } catch (IllegalStateException e) {
                                System.out.println("Invalid data entered...\n");
                                // USED FOR DEBUGGING: System.out.println("DEBUG: " + e);
                            }
                        }
                    }
                    System.out.println(countPetsAdded + " pets added.\n");
                }
                case 5 -> {
                    // Search pets by name here
                    System.out.print("\nEnter a name to search: ");
                    sc.nextLine(); // Used to catch empty line ****************
                    String nameSearch = sc.nextLine().toLowerCase();
                    rowCount = 0;
                    System.out.println("\n+----------------------+\n" +
                            "| ID | NAME      | AGE |\n" +
                            "+----------------------+");
                    for (int x = 0; x < pets.size(); x++) {
                        if (pets.get(x).name.toLowerCase().contains(nameSearch)) {
                            System.out.println("|  " + x + pets.get(x).toString());
                            rowCount++;
                        }
                    }
                    System.out.println("+----------------------+");
                    if (rowCount == 1) {
                        System.out.println(rowCount + " row in set.\n");
                    } else {
                        System.out.println(rowCount + " rows in set.\n");
                    }
                }
                case 6 -> {
                    // Search pets by age here
                    System.out.print("\nEnter an age to search: ");
                    sc.nextLine(); // Used to catch empty line ****************
                    int ageSearch = Integer.parseInt(sc.nextLine());
                    rowCount = 0;
                    System.out.println("\n+----------------------+\n" +
                            "| ID | NAME      | AGE |\n" +
                            "+----------------------+");
                    for (int x = 0; x < pets.size(); x++) {
                        if (pets.get(x).age.equals(ageSearch)) {
                            System.out.println("|  " + x + pets.get(x).toString());
                            rowCount++;
                        }
                    }
                    System.out.println("+----------------------+");
                    if (rowCount == 1) {
                        System.out.println(rowCount + " row in set.\n");
                    } else {
                        System.out.println(rowCount + " rows in set.\n");
                    }
                }
                case 7 -> {
                    // Exit program here
                    System.out.println("\nGoodbye!");
                    System.exit(0);
                }
                default -> System.out.println("\nError! Invalid menu option selected...\n");
            }
        }
    }

    static void displayMenuOptions() {
        System.out.println("What would you like to do?");
        System.out.println("\t1) View all pets");
        System.out.println("\t2) Add more pets");
// COMMENT OUT UNTIL READY FOR RELEASE        System.out.println("\t3) Update an existing pet");
// COMMENT OUT UNTIL READY FOR RELEASE        System.out.println("\t4) Remove an existing pet");
        System.out.println("\t5) Search pets by name");
        System.out.println("\t6) Search pets by age");
        System.out.println("\t7) Exit program");
        System.out.print("Your choice: ");
    }

} // End of Main.java


// Create Pet class
class Pet {
    int ID;
    String name;
    Integer age;

    // Constructor that takes name and speech String arguments
    Pet(String name, Integer age) {
        setName(name);
        setAge(age);
        int count = 0;
    }

    // Get method returns name variable value
    private String getName() {
        return name;
    }

    // Set method sets name variable value
    private void setName(String name) {
        this.name = name;
    }

    // Get method returns name variable value
    private Integer getAge() {
        return age;
    }

    // Set method sets name variable value
    private void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return " | " + getName() + "      | " + getAge() + " |";
    }
}