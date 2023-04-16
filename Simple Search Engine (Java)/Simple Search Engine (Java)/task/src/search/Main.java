package search;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final ArrayList<String> people = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (!"--data".equals(args[0])){
            System.out.println("\n### WRONG OPTIONS! ###\n");
            return;
        }
        try {
            File data = new File(args[1]);
            storeData(data);
        } catch (Exception e) {
            System.out.println("\n### NO SUCH FILE FOUND IN THIS DIRECTORY! ###\n");
        }

        while (true) {
            System.out.println("\n=== Menu ===\n" +
                    "1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit");
            switch (scanner.nextInt()) {
                case 1:
                    findPerson();
                    break;
                case 2:
                    printData();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    return;
                default:
                    System.out.println("\nIncorrect option! Try again.\n");
            }
        }

    }
    private static void printData() {
        System.out.println("=== List of people ===");
        for (String person : people) {
            System.out.println(person);
        }
    }

    private static void findPerson() {
        var peopleFound = new ArrayList<String>();
        System.out.println("\nWrite the search strategy:");
        scanner.nextLine();
        String strategy = scanner.nextLine().toLowerCase();
        System.out.println("\nEnter data to search people:");
        String searchingFor = scanner.nextLine().toLowerCase();
        switch (strategy) {
            case "all":
                allSearch(peopleFound, searchingFor);
                break;
            case "any":
                anySearch(peopleFound, searchingFor);
                break;
            case "none":
                noneSearch(peopleFound, searchingFor);
                break;
        }

        if(!peopleFound.isEmpty()) {
            System.out.println("Found people");
            for (String person : peopleFound) {
                System.out.println(person);
            }
        } else {
            System.out.println("No matching people found.");
        }
    }
    private static void storeData(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            people.add(scanner.nextLine());
        }
        scanner.close();
    }

    // Search strategies here
    private static void anySearch(ArrayList<String> peopleFound, String searchingFor) {
        for (String info : searchingFor.split("\\s")) {
            for (String person : people) {
                if (person.toLowerCase().matches(
                        "(" + info + "\\s.*)|(.*\\s" + info + "\\s.*)|(.*\\s" + info + ")")) {
                    peopleFound.add(person);
                }
            }
        }
    }
    private static void allSearch(ArrayList<String> peopleFound, String searchingFor) {
        for (String person : people) {
            boolean doesFit = true;
            for (String info : searchingFor.split("\\s")) {
                if (!person.toLowerCase().matches(
                        "(" + info + "\\s.*)|(.*\\s" + info + "\\s.*)|(.*\\s" + info + ")")) {
                    doesFit = false;
                    break;
                }
            }
            if (doesFit) {
                peopleFound.add(person);
            }
        }
    }
    private static void noneSearch(ArrayList<String> peopleFound, String searchingFor) {
        for (String person : people) {
            boolean doesFit = true;
            for (String info : searchingFor.split("\\s")) {
                if (person.toLowerCase().matches(
                        "(" + info + "\\s.*)|(.*\\s" + info + "\\s.*)|(.*\\s" + info + ")")) {
                    doesFit = false;
                    break;
                }
            }
            if (doesFit) {
                peopleFound.add(person);
            }
        }
    }
}