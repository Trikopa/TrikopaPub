package search;

import java.util.*;

public class Search {
    ArrayList<String> people;
    HashMap<String, ArrayList<Integer>> hashMap;

    public Search(ArrayList<String> arrayList, HashMap<String, ArrayList<Integer>> hashMap) {
        this.people = arrayList;
        this.hashMap = hashMap;
    }

    void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                
                === Menu ===
                1. Find a person
                2. Print all people
                0. Exit""");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> findPerson();
            case 2 -> printAll();
            case 0 -> {
                System.out.println("\nBye!");
                System.exit(0);
            }
            default -> System.out.println("\nIncorrect option! Try again.");
        }
    }

    void findPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();
        System.out.println("\nEnter a name or email to search all suitable people.");
        String names = scanner.nextLine();
        List<Integer> indexes = hashMap.values()
                .stream()
                .flatMap(Collection::stream)
                .toList();

        // switch for all strategies
        switch (strategy) {
            case "ALL" -> findAll(names, indexes);
            case "ANY" -> findAny(names, indexes);
            case "NONE" -> findNone(names, indexes);
            default -> System.out.println("\nIncorrect option! Try again.");

        }
    }

    boolean any(String name, String[] keywords) {
        for (String keyword : keywords) {
            if (name.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    void findAll(String keywords,  List<Integer> result) {
        List<Integer> finalResult = result.stream().filter(it -> any(people.get(it), keywords.split(" "))).toList();
        printResult(finalResult);
    }


    void findAny(String keywords, List<Integer> result) {
        String[] keywordList = (keywords.split(" "));
        List<Integer> finalResult = result.stream()
                .filter(it -> any(people.get(it), keywordList))
                .toList();
        printResult(finalResult);
    }

    void findNone(String keywords, List<Integer> result) {
        String[] keywordArray = keywords.split(" ");
        List<Integer> finalResult = result.stream().filter(it -> !any(people.get(it), keywordArray)).toList();
        printResult(finalResult);
    }

    void printResult(List<Integer> result) {
        Set<Integer> set = new HashSet<>(result);
        System.out.println(set.size());
        System.out.println(people.size());
        if (set.isEmpty()) {
            System.out.println("\nNo matching people found.");
        } else {
            for (int i : set) {
                System.out.println(people.get(i));
            }
        }
    }

    void printAll() {
        System.out.println("\n=== List of people ===");
        for (String person : people) {
            System.out.println(person);
        }
        menu();
    }
}
