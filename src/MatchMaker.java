import java.util.ArrayList;
import java.util.List;

public class MatchMaker {
    public static List<List<Player>> findMatches(List<Player> players, int playersNeeded) {
        List<List<Player>> matches = new ArrayList<>();
        List<Player> beginners = new ArrayList<>();
        List<Player> intermediate = new ArrayList<>();
        List<Player> advanced = new ArrayList<>();


        for (Player p : players) {
            switch (p.getSkillLevel().toLowerCase()) {
                case "beginner" -> beginners.add(p);
                case "intermediate" -> intermediate.add(p);
                case "advanced" -> advanced.add(p);
            }
        }


        if (beginners.size() >= playersNeeded)
            matches.add(new ArrayList<>(beginners.subList(0, playersNeeded)));
        if (intermediate.size() >= playersNeeded)
            matches.add(new ArrayList<>(intermediate.subList(0, playersNeeded)));
        if (advanced.size() >= playersNeeded)
            matches.add(new ArrayList<>(advanced.subList(0, playersNeeded)));

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
