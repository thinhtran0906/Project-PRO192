
package Entities;
public class MatchRecord {
    private String matchID;
    private String opponent;

    public MatchRecord(String matchID, String opponent) {
        this.matchID = matchID;
        this.opponent = opponent;
    }
    public String toString() {
        return matchID + " | " + opponent;
    }
}
