class Player {
    private String name;
    private String skillLevel;
    private String location;
    private String availability; // New field

    // Constructor
    public Player(String name, String skillLevel, String location, String availability) {
        this.name = name;
        this.skillLevel = skillLevel;
        this.location = location;
        this.availability = availability;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getSkillLevel() { return skillLevel; }
    public String getLocation() { return location; }
    public String getAvailability() { return availability; }

    public void setAvailability(String availability) { this.availability = availability; }


    public String toFileFormat() {
        return name + "," + skillLevel + "," + location + "," + availability;
    }

    // Convert file string back to an object
    public static Player fromFileFormat(String data) {
        String[] parts = data.split(",");
        if (parts.length == 4) {
            return new Player(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Skill Level: " + skillLevel + ", Location: " + location + ", Availability: " + availability;
    }
}
