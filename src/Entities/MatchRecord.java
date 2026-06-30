package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MatchRecord {

    private String matchID;
    private LocalDate date;
    private String opponent;   // BR9-note: stored as text only
    private String matchType;  // Friendly / League / Cup

    public MatchRecord(String matchID, LocalDate date, String opponent, String matchType) {
        this.matchID = matchID;
        this.date = date;
        this.opponent = opponent;
        this.matchType = matchType;
    }

    public String getMatchID() { return matchID; }
    public LocalDate getDate() { return date; }
    public String getOpponent() { return opponent; }
    public String getMatchType() { return matchType; }

    @Override
    public String toString() {
        return String.format("%-6s %-12s %-18s %-8s",
                matchID,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                opponent,
                matchType);
    }
}
