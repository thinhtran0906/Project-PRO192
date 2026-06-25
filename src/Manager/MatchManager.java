
package Manager;

import java.util.ArrayList;
import Model.MatchRecord;

public class MatchManager {

    private ArrayList<MatchRecord> matchList;

    public MatchManager() {
        matchList = new ArrayList<>();
    }

    public void addMatch(MatchRecord match) {
        matchList.add(match);
    }

    public void displayMatchHistory() {

        if(matchList.isEmpty()){
            System.out.println("No match history found!");
            return;
        }

        for(MatchRecord m : matchList) {
            System.out.println(m);
        }
    }

    public double calculatePlayerBonus(
            Player player,
            int performancePoints) {

        return player.calculateBonus(
                performancePoints);
    }
}

