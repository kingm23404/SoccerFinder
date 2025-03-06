import java.util.ArrayList;
import java.util.List;

public class MatchMaker {
    public static List<List<Player>> findMatches(List<Player> players) {
        List<List<Player>> matches = new ArrayList<>();
        List<Player> beginners = new ArrayList<>();
        List<Player> intermediate = new ArrayList<>();
        List<Player> advanced = new ArrayList<>();

        // Group players by skill level
        for (Player p : players) {
            switch (p.getSkillLevel().toLowerCase()) {
                case "beginner":
                    beginners.add(p);
                    break;
                case "intermediate":
                    intermediate.add(p);
                    break;
                case "advanced":
                    advanced.add(p);
                    break;
            }
        }

        // Create games from available players
        if (!beginners.isEmpty()) matches.add(beginners);
        if (!intermediate.isEmpty()) matches.add(intermediate);
        if (!advanced.isEmpty()) matches.add(advanced);

        return matches;
    }

    public static void printMatches(List<List<Player>> matches) {
        int gameNum = 1;
        for (List<Player> game : matches) {
            System.out.println("Game " + gameNum + ":");
            for (Player p : game) {
                System.out.println(p);
            }
            System.out.println();
            gameNum++;
        }
    }
}
