package Manager;

import java.util.ArrayList;
import java.util.Scanner;
import Entities.Player;
import Exceptions.InvalidInputException;
import Utils.Validator;

/**
 * Reporting module (FR18, FR19).
 * Reads players from PlayerManager and performance/match data from MatchManager.
 */
public class ReportManager {

    private PlayerManager playerManager;
    private MatchManager matchManager;
    private Scanner sc;

    public ReportManager(PlayerManager playerManager, MatchManager matchManager, Scanner sc) {
        this.playerManager = playerManager;
        this.matchManager = matchManager;
        this.sc = sc;
    }

    // FR18 / BR25-27: Salary Summary report for a selected month and year.
    public void salarySummaryReport() {
        try {
            System.out.print("Enter Month: ");
            int month = Validator.validateMonth(Validator.parseInt(sc.nextLine(), "Month"));
            System.out.print("Enter Year: ");
            int year = Validator.validateYear(Validator.parseInt(sc.nextLine(), "Year"));

            ArrayList<Player> players = playerManager.getPlayerList();
            if (players.isEmpty()) {
                System.out.println("No players to report!");
                return;
            }

            System.out.printf("%n----------- SALARY SUMMARY REPORT (%02d/%d) -----------%n", month, year);
            System.out.printf("%-6s %-22s %-16s %-14s %-12s %-14s%n",
                    "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
            System.out.println("------------------------------------------------------------------------------------");

            double totalCost = 0;
            for (Player p : players) {
                int points = matchManager.getMonthlyPerformancePoints(p.getPlayerID(), month, year);
                double bonus = p.calculateBonus(points);
                double total = p.getBaseSalary() + bonus;
                totalCost += total;
                System.out.printf("%-6s %-22s %-16s %-14.0f %-12.0f %-14.0f%n",
                        p.getPlayerID(), p.getFullName(), p.getPlayerType(),
                        p.getBaseSalary(), bonus, total);
            }
            System.out.println("------------------------------------------------------------------------------------");
            System.out.printf("Total Monthly Salary Cost: %.0f VND%n", totalCost);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // FR19 / BR28: All-time Top Goal Scorers, from all match performance records.
    // Uses a manual Selection Sort algorithm to rank players from highest to lowest goals.
    public void topGoalScorersReport() {
        ArrayList<Player> players = playerManager.getPlayerList();
        if (players.isEmpty()) {
            System.out.println("No players to report!");
            return;
        }

        int n = players.size();

        // Step 1: accumulate each player's total goals across ALL matches (BR28).
        Player[] arr = new Player[n];
        int[] goals = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = players.get(i);
            goals[i] = matchManager.getTotalGoals(arr[i].getPlayerID());
        }

        // Step 2: Selection Sort -> sort by number of goals from high to low (descending).
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (goals[j] > goals[maxIndex]) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                // swap goals
                int tmpGoal = goals[i];
                goals[i] = goals[maxIndex];
                goals[maxIndex] = tmpGoal;
                // swap the matching player so player and goal stay aligned
                Player tmpPlayer = arr[i];
                arr[i] = arr[maxIndex];
                arr[maxIndex] = tmpPlayer;
            }
        }

        // Step 3: print the ranked result.
        System.out.println("\n----------- ALL-TIME TOP GOAL SCORERS -----------");
        System.out.printf("%-5s %-10s %-22s %-12s %-6s%n", "Rank", "Player ID", "Name", "Position", "Goals");
        System.out.println("-----------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-5d %-10s %-22s %-12s %-6d%n",
                    i + 1, arr[i].getPlayerID(), arr[i].getFullName(), arr[i].getPosition(), goals[i]);
        }
    }
}
