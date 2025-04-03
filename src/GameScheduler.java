import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GameScheduler {
    private static final String GAME_SCHEDULE_FILE = "ScheduledGames.txt";
    private static Queue<Player> waitingQueue = new LinkedList<>();

    public static void scheduleGames(List<Player> players, Scanner scanner) {
        System.out.print("👤 Enter your name to schedule a game: ");
        String name = scanner.nextLine().trim();


        Player player = recursiveSearch(players, name, 0);
        if (player == null) {
            System.out.println("❌ Player not found! Please register first.");
            return;
        }

        System.out.println("🎮 Choose a match format:");
        System.out.println("1️⃣ 8v8 (16 players needed)");
        System.out.println("2️⃣ 5v5 (10 players needed)");
        System.out.println("3️⃣ 11v11 (22 players needed)");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        int playersNeeded;
        switch (choice) {
            case 1 -> playersNeeded = 16;
            case 2 -> playersNeeded = 10;
            case 3 -> playersNeeded = 22;
            default -> {
                System.out.println("❌ Invalid choice. Defaulting to 11v11.");
                playersNeeded = 22;
            }
        }



        List<Player> availablePlayers = findAvailablePlayers(players, player.getSkillLevel(), playersNeeded);


        if (availablePlayers.size() < playersNeeded) {
            System.out.println("⚠ Not enough players available with the same skill level. Adding you to the waiting queue...");
            waitingQueue.add(player);
            return;
        }


        String bestTime = findBestTimeForPlayers(availablePlayers);

        if (bestTime == null) {
            System.out.println("❌ No common available time found. Please try again later.");
            return;
        }


        String location = availablePlayers.get(0).getLocation();
        System.out.println("✅ Game scheduled!");
        System.out.println("Location: " + location);
        System.out.println("Time: " + bestTime);
        System.out.println("Players: ");
        for (Player p : availablePlayers) {
            System.out.println(p);
        }


        saveScheduledGame(location, bestTime, availablePlayers);
    }

    private static String findBestTimeForPlayers(List<Player> players) {
        Map<String, Integer> timeCounts = new HashMap<>();
        for (Player p : players) {
            for (String time : p.getAvailability()) {
                timeCounts.put(time, timeCounts.getOrDefault(time, 0) + 1);
            }
        }


        int maxCount = 0;
        String bestTime = null;
        for (Map.Entry<String, Integer> entry : timeCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                bestTime = entry.getKey();
            }
        }
        return bestTime;
    }

    private static List<Player> findAvailablePlayers(List<Player> players, String skillLevel, int playersNeeded) {
        List<Player> availablePlayers = new ArrayList<>();
        for (Player p : players) {
            if (p.getSkillLevel().equalsIgnoreCase(skillLevel) && p.getAvailability() != null && !p.getAvailability().isEmpty()) {
                availablePlayers.add(p);
            }
        }
        return availablePlayers.size() >= playersNeeded ? availablePlayers : new ArrayList<>();
    }

    private static void saveScheduledGame(String location, String time, List<Player> players) {
        try (FileWriter writer = new FileWriter(GAME_SCHEDULE_FILE)) {
            writer.write("Scheduled Game\n");
            writer.write("Location: " + location + "\n");
            writer.write("Time: " + time + "\n");
            writer.write("Players:\n");
            for (Player p : players) {
                writer.write(p.getName() + " - " + p.getSkillLevel() + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving scheduled games: " + e.getMessage());
        }
    }

    private static Player recursiveSearch(List<Player> players, String name, int index) {
        if (index >= players.size()) return null;
        if (players.get(index).getName().equalsIgnoreCase(name)) return players.get(index);
        return recursiveSearch(players, name, index + 1);
    }
}
