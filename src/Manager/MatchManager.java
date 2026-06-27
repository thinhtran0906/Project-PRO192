package Manager;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import Entities.Player;
import Entities.PerformanceRecord;
import Exceptions.InvalidInputException;
import Utils.Validator;

public class MatchManager {

    private ArrayList<MatchRecord> matchList;
    private ArrayList<PerformanceRecord> performanceList;
    private Scanner sc;

    public MatchManager(Scanner sc) {
        this.sc = sc;
        matchList = new ArrayList<>();
        performanceList = new ArrayList<>();
    }

    public MatchRecord searchMatchByID(String id) {
        for (MatchRecord m : matchList) {
            if (m.getMatchID().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    // FR9 / BR1, BR12, BR14: create a match record
    public void createMatch() {
        try {
            System.out.print("Match ID: ");
            String id = Validator.requireNonEmpty(sc.nextLine(), "Match ID");
            if (searchMatchByID(id) != null) {
                System.out.println("Match ID already exists!");
                return;
            }

            System.out.print("Date (dd/MM/yyyy): ");
            LocalDate date = Validator.validateDate(sc.nextLine());

            System.out.print("Opponent Team: ");
            String opponent = Validator.requireNonEmpty(sc.nextLine(), "Opponent team");

            System.out.println("Match Type: 1. Friendly  2. League  3. Cup");
            System.out.print("Choose Match Type: ");
            String choice = sc.nextLine().trim();
            String type;
            switch (choice) {
                case "1": type = "Friendly"; break;
                case "2": type = "League"; break;
                case "3": type = "Cup"; break;
                default:  type = Validator.validateMatchType(choice);
            }

            matchList.add(new MatchRecord(id, date, opponent, type));
            System.out.println("Match record created successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // FR12: view match history
    public void viewMatchHistory() {
        if (matchList.isEmpty()) {
            System.out.println("No match history found!");
            return;
        }
        System.out.printf("%-6s %-12s %-18s %-8s%n", "ID", "Date", "Opponent", "Type");
        System.out.println("--------------------------------------------------");
        for (MatchRecord m : matchList) {
            System.out.println(m);
        }
    }

    private PerformanceRecord findPerformance(String matchID, String playerID) {
        for (PerformanceRecord p : performanceList) {
            if (p.getMatchID().equalsIgnoreCase(matchID)
                    && p.getPlayerID().equalsIgnoreCase(playerID)) {
                return p;
            }
        }
        return null;
    }

    // FR10 / BR9, BR19-24: add or update a player's performance in a match
    public void addOrUpdatePerformance(PlayerManager pm) {
        try {
            System.out.print("Match ID: ");
            String matchID = sc.nextLine().trim();
            if (searchMatchByID(matchID) == null) {
                System.out.println("Match not found!");
                return;
            }

            System.out.print("Player ID: ");
            String playerID = sc.nextLine().trim();
            Player player = pm.searchPlayerByID(playerID);
            if (player == null) {
                System.out.println("Player not found!");
                return;
            }
            if (!player.getStatus().equalsIgnoreCase("Active")) { // BR9
                System.out.println("Only Active players can have performance records!");
                return;
            }
            System.out.println("Player Name: " + player.getFullName());

            // BR23: if a record exists, show it and confirm before replacing
            PerformanceRecord existing = findPerformance(matchID, playerID);
            if (existing != null) {
                System.out.println("Existing Performance: " + existing);
                System.out.print("Replace this record? [1] Replace [2] Cancel: ");
                if (!sc.nextLine().trim().equals("1")) {
                    System.out.println("Cancelled.");
                    return;
                }
            }

            System.out.print("Goals: ");
            int goals = Validator.validateNonNegative(Validator.parseInt(sc.nextLine(), "Goals"), "Goals");
            System.out.print("Assists: ");
            int assists = Validator.validateNonNegative(Validator.parseInt(sc.nextLine(), "Assists"), "Assists");
            System.out.print("Yellow Cards: ");
            int yellow = Validator.validateNonNegative(Validator.parseInt(sc.nextLine(), "Yellow cards"), "Yellow cards");
            System.out.print("Red Cards: ");
            int red = Validator.validateNonNegative(Validator.parseInt(sc.nextLine(), "Red cards"), "Red cards");
            System.out.print("Minutes Played: ");
            int minutes = Validator.validateMinutes(Validator.parseInt(sc.nextLine(), "Minutes played"));

            // BR21: if minutes is 0, all other stats must be 0
            if (minutes == 0 && (goals != 0 || assists != 0 || yellow != 0 || red != 0)) {
                System.out.println("If minutes played is 0, goals/assists/cards must all be 0!");
                return;
            }

            if (existing != null) {
                existing.setGoals(goals);
                existing.setAssists(assists);
                existing.setYellowCards(yellow);
                existing.setRedCards(red);
                existing.setMinutesPlayed(minutes);
            } else {
                existing = new PerformanceRecord(matchID, playerID, goals, assists, yellow, red, minutes);
                performanceList.add(existing);
            }
            System.out.println("Player performance saved successfully.");
            System.out.println("Performance Points: " + existing.calculatePerformancePoints());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // BR25: sum of a player's performance points within a given month/year
    public int getMonthlyPerformancePoints(String playerID, int month, int year) {
        int total = 0;
        for (PerformanceRecord p : performanceList) {
            if (p.getPlayerID().equalsIgnoreCase(playerID)) {
                MatchRecord m = searchMatchByID(p.getMatchID());
                if (m != null && m.getDate().getMonthValue() == month && m.getDate().getYear() == year) {
                    total += p.calculatePerformancePoints();
                }
            }
        }
        return total;
    }

    // BR28: all-time goals for a player (not filtered by month/year)
    public int getTotalGoals(String playerID) {
        int total = 0;
        for (PerformanceRecord p : performanceList) {
            if (p.getPlayerID().equalsIgnoreCase(playerID)) {
                total += p.getGoals();
            }
        }
        return total;
    }

    public ArrayList<MatchRecord> getMatchList() { return matchList; }
    public ArrayList<PerformanceRecord> getPerformanceList() { return performanceList; }
}
