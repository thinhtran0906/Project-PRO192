package Entities;

/**
 * Performance of one player in one match (BR22: one record per player per match).
 */
public class PerformanceRecord {

    private String matchID;
    private String playerID;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private int minutesPlayed;

    public PerformanceRecord(String matchID, String playerID, int goals, int assists,
                             int yellowCards, int redCards, int minutesPlayed) {
        this.matchID = matchID;
        this.playerID = playerID;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.minutesPlayed = minutesPlayed;
    }

    public String getMatchID() { return matchID; }
    public String getPlayerID() { return playerID; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
    public int getMinutesPlayed() { return minutesPlayed; }

    public void setGoals(int goals) { this.goals = goals; }
    public void setAssists(int assists) { this.assists = assists; }
    public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }
    public void setRedCards(int redCards) { this.redCards = redCards; }
    public void setMinutesPlayed(int minutesPlayed) { this.minutesPlayed = minutesPlayed; }

    // BR24: Performance Points = goals*5 + assists*3 - yellow*1 - red*3, min 0
    public int calculatePerformancePoints() {
        int points = goals * 5 + assists * 3 - yellowCards * 1 - redCards * 3;
        return Math.max(points, 0);
    }

    @Override
    public String toString() {
        return String.format("Goals: %d | Assists: %d | Yellow: %d | Red: %d | Minutes: %d | Points: %d",
                goals, assists, yellowCards, redCards, minutesPlayed, calculatePerformancePoints());
    }
}
