package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Start {
    ArrayList<String> people = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();


    public Start(String path) {
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                people.add(line);
                String[] split = line.split(" ");
                for (String s : split) {
                    ArrayList<Integer> temp;
                    if (hashMap.containsKey(s)) {
                        temp = hashMap.get(s);
                    }
                    else {
                        temp = new ArrayList<>();
                    }
                    temp.add(i);
                    hashMap.put(s.toLowerCase(), temp);
                }
                i++;
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }
}
