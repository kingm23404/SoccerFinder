import java.util.*;

public class SoccerFinder {
    private static List<Player> players = new ArrayList<>();
    private static final String FILE_NAME = "SoccerPlayerFile.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚽ Welcome to Pickup Soccer Finder! ⚽");

        // Load existing players from Soccerfile
        players = FileHandler.loadPlayers(FILE_NAME);

        sortPlayersBySkillLevel();

        // Main menu loop
        while (true) {
            System.out.println("\n📋 Menu:");
            System.out.println("1️⃣ Register a new player");
            System.out.println("2️⃣ View all registered players (Sorted by Skill Level)");
            System.out.println("3️⃣ Find a match");
            System.out.println("4️⃣ Search for a player");
            System.out.println("5️⃣ Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Player newPlayer = registerPlayer(scanner);
                    if (newPlayer != null) {
                        FileHandler.savePlayers(Collections.singletonList(newPlayer), FILE_NAME); // Save only new player
                        players.add(newPlayer);
                        sortPlayersBySkillLevel();
                    }
                    break;
                case "2":
                    displayPlayers();
                    break;
                case "3":
                    findMatch(scanner);
                    break;
                case "4":
                    searchPlayer(scanner);
                    break;
                case "5":
                    System.out.println("🚪Thank you for using SoccerFinder!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 5.");
            }
        }
    }

    private static Player registerPlayer(Scanner scanner) {
        System.out.print("👤 Enter your name: ");
        String name = scanner.nextLine().trim();


        String skillLevel;
        while (true) {
            System.out.print("🎯 Select skill level (beginner, intermediate, advanced): ");
            skillLevel = scanner.nextLine().trim().toLowerCase();
            if (skillLevel.equals("beginner") || skillLevel.equals("intermediate") || skillLevel.equals("advanced")) {
                break;
            } else {
                System.out.println("Invalid skill level. Please enter 'beginner', 'intermediate', or 'advanced'.");
            }
        }


        System.out.print("📍 Enter your preferred location (Park name): ");
        String location = scanner.nextLine().trim();


        System.out.print("⏰ Enter your availability (ex., evenings, weekends, weekdays): ");
        String availability = scanner.nextLine().trim();


        Player player = new Player(name, skillLevel, location, availability);
        System.out.println("\n Player registered successfully!");
        return player;
    }


    private static void displayPlayers() {
        if (players.isEmpty()) {
            System.out.println("No players registered yet.");
            return;
        }
        System.out.println("\nRegistered Players (Sorted by Skill Level):");
        for (Player p : players) {
            System.out.println(p);
        }
    }


    private static void findMatch(Scanner scanner) {
        if (players.size() < 2) {
            System.out.println(" Not enough players to create a match.");
            return;
        }

        System.out.print("🎯 Enter your skill level: ");
        String skillLevel = scanner.nextLine().trim().toLowerCase();

        System.out.print("📍 Enter your preferred location: ");
        String location = scanner.nextLine().trim();

        System.out.print("⏰ Enter your availability: ");
        String availability = scanner.nextLine().trim();

        List<Player> matchedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getSkillLevel().equals(skillLevel) &&
                    player.getLocation().equalsIgnoreCase(location) &&
                    player.getAvailability().equalsIgnoreCase(availability)) {
                matchedPlayers.add(player);
            }
        }

        if (matchedPlayers.isEmpty()) {
            System.out.println("No matches found. Try a different skill level, location, or availability.");
        } else {
            System.out.println("\nMatching Players:");
            for (Player player : matchedPlayers) {
                System.out.println(player);
            }
        }
    }

    private static Player findPlayerRecursive(List<Player> players, String name, int index) {
        if (index >= players.size()) {
            return null;
        }
        if (players.get(index).getName().equalsIgnoreCase(name)) {
            return players.get(index);
        }
        return findPlayerRecursive(players, name, index + 1);
    }


    private static void searchPlayer(Scanner scanner) {
        System.out.print("🔍 Enter player name to search: ");
        String name = scanner.nextLine().trim();

        Player foundPlayer = findPlayerRecursive(players, name, 0);
        if (foundPlayer != null) {
            System.out.println("Player Found: " + foundPlayer);
        } else {
            System.out.println("Player not found.");
        }
    }

    private static void sortPlayersBySkillLevel() {
        Collections.sort(players, new SkillLevelComparator());
    }
}


class SkillLevelComparator implements Comparator<Player> {
    private static final List<String> SKILL_ORDER = Arrays.asList("beginner", "intermediate", "advanced");

    @Override
    public int compare(Player p1, Player p2) {
        return Integer.compare(SKILL_ORDER.indexOf(p1.getSkillLevel()), SKILL_ORDER.indexOf(p2.getSkillLevel()));
    }
}



