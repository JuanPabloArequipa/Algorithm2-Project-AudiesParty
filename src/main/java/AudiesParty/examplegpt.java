package AudiesParty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AudiesParty {
    static class Villager {
        String name;
        Map<String, Integer> connections;

        public Villager(String name) {
            this.name = name;
            this.connections = new HashMap<>();
        }

        public void addConnection(String villager, int relationship) {
            connections.put(villager, relationship);
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\JP\\Code\\Algoritm2\\src\\greedy\\AudiesParty\\example1.txt"); // Change to the actual file path
            Scanner scanner = new Scanner(file);

            Map<String, Villager> villagers = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("X")) break;

                Villager villager = new Villager(line);
                villagers.put(line, villager);
            }

            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(" ");
                if (tokens.length == 3) {
                    villagers.get(tokens[0]).addConnection(tokens[1], Integer.parseInt(tokens[2]));
                    villagers.get(tokens[1]).addConnection(tokens[0], Integer.parseInt(tokens[2]));
                } else {
                    break;
                }
            }

            int x = Integer.parseInt(scanner.nextLine().split(" ")[2]);
            List<String> guestList = generateGuestList(villagers, x);
            System.out.println("Guest List: " + guestList);

            int k = Integer.parseInt(scanner.nextLine().split(" ")[2]);
            List<List<String>> groups = divideIntoGroups(guestList, villagers, k);
            printGroups(groups);

        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
            e.printStackTrace();
        }
    }

    public static List<String> generateGuestList(Map<String, Villager> villagers, int x) {
        List<String> guestList = new ArrayList<>();
        for (String villagerName : villagers.keySet()) {
            boolean addVillager = true;
            for (String guest : guestList) {
                if (villagers.get(villagerName).connections.containsKey(guest)) {
                    if (villagers.get(villagerName).connections.get(guest) < x) {
                        addVillager = false;
                        break;
                    }
                }
            }
            if (addVillager) guestList.add(villagerName);
        }
        return guestList;
    }

    public static List<List<String>> divideIntoGroups(List<String> guestList, Map<String, Villager> villagers, int k) {
        List<List<String>> groups = new ArrayList<>();
        if (guestList.size() >= k) {
            Collections.shuffle(guestList); // Randomize guest list to handle multiple valid solutions
            for (int i = 0; i < k; i++) {
                List<String> group = new ArrayList<>();
                groups.add(group);
            }
            for (String villager : guestList) {
                int maxRelationship = Integer.MIN_VALUE;
                int minRelationship = Integer.MAX_VALUE;
                String maxRelationshipVillager = null;
                String minRelationshipVillager = null;
                for (List<String> group : groups) {
                    int relationshipSum = 0;
                    for (String member : group) {
                        if (villagers.get(villager).connections.containsKey(member)) {
                            relationshipSum += villagers.get(villager).connections.get(member);
                        }
                        if (villagers.get(villager).connections.containsKey(member) && villagers.get(villager).connections.get(member) > maxRelationship) {
                            maxRelationship = villagers.get(villager).connections.get(member);
                            maxRelationshipVillager = villager;
                        }
                        if (villagers.get(villager).connections.containsKey(member) && villagers.get(villager).connections.get(member) < minRelationship) {
                            minRelationship = villagers.get(villager).connections.get(member);
                            minRelationshipVillager = villager;
                        }
                    }
                    if (relationshipSum == 0) {
                        group.add(villager);
                        break;
                    }
                }
                if (maxRelationshipVillager != null) groups.get(0).add(maxRelationshipVillager);
                if (minRelationshipVillager != null) groups.get(groups.size() - 1).add(minRelationshipVillager);
            }
        } else {
            System.out.println("It is not possible");
        }
        return groups;
    }

    public static void printGroups(List<List<String>> groups) {
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + (i + 1) + ": " + groups.get(i));
        }
    }
}
