import java.util.*;

public class SoccerFinder {
    private static List<Player> players = new ArrayList<>();
    private static final String PLAYER_FILE = "SoccerPlayerFile.txt";
    private static final String GAME_SCHEDULE_FILE = "ScheduledGames.txt";
    private static Queue<Player> matchQueue = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚽ Welcome to Pickup Soccer Finder! ⚽");

        // Load existing players from file
        players = FileHandler.loadPlayers(PLAYER_FILE);
        sortPlayersByRating();

        while (true) {
            System.out.println("\n📋 Menu:");
            System.out.println("1️⃣ Register a new player");
            System.out.println("2️⃣ View all players (Sorted by Rating)");
            System.out.println("3️⃣ Find a match");
            System.out.println("4️⃣ Search for a player");
            System.out.println("5️⃣ Rate a player");
            System.out.println("6️⃣ Enter your match stats");
            System.out.println("7️⃣ Schedule Games");
            System.out.println("8️⃣ Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Player newPlayer = registerPlayer(scanner);
                    if (newPlayer != null) {
                        players.add(newPlayer);
                        FileHandler.savePlayers(players, PLAYER_FILE);
                        sortPlayersByRating();
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
                    ratePlayer(scanner);
                    break;
                case "6":
                    enterMatchStats(scanner);
                    break;
                case "7":
                    GameScheduler.scheduleGames(players, scanner);
                    break;
                case "8":
                    System.out.println("🚪 Thank you for using SoccerFinder!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 8.");
            }
        }
    }

    private static Player registerPlayer(Scanner scanner) {
        System.out.print("👤 Enter your name: ");
        String name = scanner.nextLine().trim();
        System.out.print("📊 Enter your skill level (Beginner, Intermediate, Advanced): ");
        String skillLevel = scanner.nextLine().trim();
        System.out.print("📍 Enter your location: ");
        String location = scanner.nextLine().trim();
        System.out.print("📅 Enter your availability(specific time): ");
        String availabilityInput = scanner.nextLine().trim();
        List<String> availability = Arrays.asList(availabilityInput.split(","));

        return new Player(name, skillLevel, location, availability);
    }

    private static void displayPlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
    }

    private static void findMatch(Scanner scanner) {
        System.out.print("Enter your name to find a match: ");
        String name = scanner.nextLine().trim();
        Player currentPlayer = recursiveSearch(players, name, 0);

        if (currentPlayer == null) {
            System.out.println("❌ Player not found! Please register first.");
            return;
        }

        List<Player> potentialMatches = new ArrayList<>();
        for (Player p : players) {
            if (!p.getName().equalsIgnoreCase(name) && p.getSkillLevel().equalsIgnoreCase(currentPlayer.getSkillLevel())) {
                potentialMatches.add(p);
            }
        }

        if (potentialMatches.size() >= 10) {
            Collections.shuffle(potentialMatches);
            List<Player> team = new ArrayList<>();
            team.add(currentPlayer);
            team.addAll(potentialMatches.subList(0, 10));

            System.out.println("✅ Match found! Here’s your team:");
            for (Player player : team) {
                System.out.println(player);
            }
        } else {
            System.out.println("⚠ Not enough players found. Adding you to the waiting queue...");
            matchQueue.add(currentPlayer);
        }
    }

    private static void searchPlayer(Scanner scanner) {
        System.out.print("🔎 Enter player name: ");
        String name = scanner.nextLine().trim();
        Player player = recursiveSearch(players, name, 0);

        if (player != null) {
            System.out.println(player);
        } else {
            System.out.println("❌ Player not found.");
        }
    }

    private static void ratePlayer(Scanner scanner) {
        System.out.print("⭐ Enter player name to rate: ");
        String name = scanner.nextLine().trim();
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                System.out.print("🌟 Rate the player (1-10): ");
                int rating = scanner.nextInt();
                scanner.nextLine();
                player.addRating(rating);
                sortPlayersByRating();
                System.out.println("✅ Rating submitted!");
                return;
            }
        }
        System.out.println("❌ Player not found.");
    }

    private static void enterMatchStats(Scanner scanner) {
        System.out.print("👤 Enter your name to update match stats: ");
        String name = scanner.nextLine().trim();
        Player currentPlayer = recursiveSearch(players, name, 0);

        if (currentPlayer == null) {
            System.out.println("❌ Player not found! Please register first.");
            return;
        }

        System.out.print("⚽ Enter goals scored: ");
        int goals = scanner.nextInt();
        System.out.print("🎯 Enter assists made: ");
        int assists = scanner.nextInt();
        System.out.print("🏆 Did your team win? (true/false): ");
        boolean won = scanner.nextBoolean();

        currentPlayer.addGameStats(goals, assists, won);
        FileHandler.savePlayers(players, PLAYER_FILE);
        System.out.println("✅ Stats updated successfully!");
    }

    private static void sortPlayersByRating() {
        players.sort(Comparator.comparingDouble(Player::calculateRating).reversed());
    }

    private static Player recursiveSearch(List<Player> players, String name, int index) {
        if (index >= players.size()) return null;
        if (players.get(index).getName().equalsIgnoreCase(name)) return players.get(index);
        return recursiveSearch(players, name, index + 1);
    }
}
