package UI;

import java.util.Scanner;
import Entities.Player;
import Manager.PlayerManager;
import Manager.TrainingManager;
import Manager.MatchManager;
import Manager.ReportManager;
import Manager.SalaryManager;

public class Menu {

    // One shared Scanner for the whole program (avoids the multi-Scanner bug).
    private final Scanner sc = new Scanner(System.in);

    private final PlayerManager playerManager = new PlayerManager(sc);
    private final TrainingManager trainingManager = new TrainingManager(sc);
    private final MatchManager matchManager = new MatchManager(sc);
    private final SalaryManager salaryManager = new SalaryManager();
    private final ReportManager reportManager = new ReportManager(playerManager, matchManager, salaryManager, sc);

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("  FOOTBALL PLAYER MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Manage Players");
            System.out.println("2. Training and Match Management");
            System.out.println("3. Reports");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            choice = readInt();
            switch (choice) {
                case 1: managePlayersMenu(); break;
                case 2: trainingAndMatchMenu(); break;
                case 3: reportsMenu(); break;
                case 4: System.out.println("Thank you for using the Football Player Management System."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private void managePlayersMenu() {
        int choice;
        do {
            System.out.println("\n----- MANAGE PLAYERS -----");
            System.out.println("1. Add Player");
            System.out.println("2. View All Players");
            System.out.println("3. Search Player by ID");
            System.out.println("4. Update Player");
            System.out.println("5. Deactivate Player");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            choice = readInt();
            switch (choice) {
                case 1: playerManager.addPlayer(); break;
                case 2: playerManager.displayPlayers(); break;
                case 3: searchPlayer(); break;
                case 4: playerManager.updatePlayer(); break;
                case 5: playerManager.deactivatePlayer(); break;
                case 6: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }

    private void trainingAndMatchMenu() {
        int choice;
        do {
            System.out.println("\n----- TRAINING AND MATCH MANAGEMENT -----");
            System.out.println("1. Create Training Session");
            System.out.println("2. View Training History");
            System.out.println("3. Create Match Record");
            System.out.println("4. View Match History");
            System.out.println("5. Add / Update Player Performance");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            choice = readInt();
            switch (choice) {
                case 1: trainingManager.createTrainingSession(); break;
                case 2: trainingManager.displayTrainingHistory(); break;
                case 3: matchManager.createMatch(); break;
                case 4: matchManager.viewMatchHistory(); break;
                case 5: matchManager.addOrUpdatePerformance(playerManager); break;
                case 6: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }

    private void reportsMenu() {
        int choice;
        do {
            System.out.println("\n----- REPORTS -----");
            System.out.println("1. Salary Summary Report");
            System.out.println("2. All-time Top Goal Scorers");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");

            choice = readInt();
            switch (choice) {
                case 1: reportManager.salarySummaryReport(); break;
                case 2: reportManager.topGoalScorersReport(); break;
                case 3: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    private void searchPlayer() {
        System.out.print("Enter Player ID: ");
        String id = sc.nextLine().trim();
        Player player = playerManager.searchPlayerByID(id);
        if (player != null) {
            System.out.println(player);
        } else {
            System.out.println("Player not found!");
        }
    }

    // Reads a menu option without crashing on non-numeric input.
    private int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number!");
            return -1;
        }
    }
}
