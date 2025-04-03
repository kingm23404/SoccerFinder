import java.io.*;
import java.util.*;

public class FileHandler {
    public static void savePlayers(List<Player> players, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Player player : players) {
                writer.write(player.toFileFormat() + "\n");
            }
            System.out.println("✅ Players saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving players: " + e.getMessage());
        }
    }

    public static List<Player> loadPlayers(String fileName) {
        List<Player> players = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No existing player file found. Create a new one!");
            return players;
        }

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Player player = Player.fromFileFormat(line);
                if (player != null) {
                    players.add(player);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading players: " + e.getMessage());
        }
        return players;
    }
}
