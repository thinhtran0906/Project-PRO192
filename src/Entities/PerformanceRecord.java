public class PerformanceRecord {
    private String playerID;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private int minutesPlayed;

    public PerformanceRecord(String playerID) {
        this.playerID = playerID;
    }

    public void updatePerformance(int goals, int assists, int yellowCards, int redCards, int minutesPlayed) {
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.minutesPlayed = minutesPlayed;

        System.out.println("Performance updated!");
    }
}
