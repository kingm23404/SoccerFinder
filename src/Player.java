import java.util.*;

public class Player {
    private String name;
    private String skillLevel;
    private String location;
    private List<String> availability;
    private LinkedList<Integer> statistics = new LinkedList<>(); // LinkedList for stats
    private Stack<Integer> ratingHistory = new Stack<>(); // Stack for rating history
    private int goalsScored;
    private int assists;
    private int gamesWon;
    private int gamesLost;

    public Player(String name, String skillLevel, String location, List<String> availability) {
        this.name = name;
        this.skillLevel = skillLevel;
        this.location = location;
        this.availability = availability;
        this.goalsScored = 0;
        this.assists = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    public String getName() {
        return name;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getAvailability() {
        return availability;
    }

    public void addStatistic(int stat) {
        statistics.add(stat);
    }

    public void addRating(int rating) {
        ratingHistory.push(rating);
    }

    public void addGameStats(int goals, int assists, boolean won) {
        this.goalsScored += goals;
        this.assists += assists;
        if (won) {
            this.gamesWon++;
        } else {
            this.gamesLost++;
        }
    }

    public double calculateRating() {
        double statAvg = statistics.stream().mapToInt(Integer::intValue).average().orElse(0);
        double ratingAvg = ratingHistory.stream().mapToInt(Integer::intValue).average().orElse(0);

        // Performance-based rating
        double performanceRating = (goalsScored * 0.4) + (assists * 0.3) + (gamesWon * 0.2) - (gamesLost * 0.1);

        // Adjust the formula as necessary
        return (statAvg * 0.5) + (ratingAvg * 0.3) + (performanceRating * 0.2);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Skill Level: " + skillLevel + ", Location: " + location + ", Availability: " + availability +
                ", Rating: " + String.format("%.2f", calculateRating()) +
                ", Goals Scored: " + goalsScored + ", Assists: " + assists + ", Games Won: " + gamesWon + ", Games Lost: " + gamesLost;
    }

    public String toFileFormat() {
        return name + "," + skillLevel + "," + location + "," + availability + "," + calculateRating();
    }

    public static Player fromFileFormat(String data) {
        String[] parts = data.split(",");
        List<String> availabilityList = new ArrayList<>();
        if (parts.length >= 4) {
            for (int i = 3; i < parts.length - 1; i++) {
                availabilityList.add(parts[i]);
            }
            return new Player(parts[0], parts[1], parts[2], availabilityList);
        }
        return null;
    }
}

