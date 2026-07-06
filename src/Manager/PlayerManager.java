package Manager;

import java.util.ArrayList;
import java.util.Scanner;
import Entities.Player;
import Entities.RegularPlayer;
import Entities.StarPlayer;
import Exceptions.InvalidInputException;
import Utils.Validator;
import Utils.FileManager;

public class PlayerManager {

    private ArrayList<Player> playerList;
    private Scanner sc;

    public PlayerManager(Scanner sc) {
        this.sc = sc;
        playerList = FileManager.loadPlayersFromFile("players.txt");
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
public ArrayList<Player> getActivePlayers() {
    ArrayList<Player> activePlayers = new ArrayList<>();

    for (Player p : playerList) {
        if (p.getStatus().equalsIgnoreCase("Active")) {
            activePlayers.add(p);
        }
    }

    return activePlayers;
}
    // FR1 / BR1-6, BR11: add a new player
    public void addPlayer() {
        try {
            Player p;
            System.out.println("Choose Player Type:");
            System.out.println("1. Regular Player");
            System.out.println("2. Star Player");

            int type = Validator.parseInt(sc.nextLine(), "Player type");

            if (type == 1) {
                p = new RegularPlayer();
            } else if (type == 2) {
                StarPlayer sp = new StarPlayer();
                p = sp;
            } else {
                System.out.println("Invalid player type!");
                return;
            }

            System.out.print("Enter Player ID: ");
            String id = Validator.requireNonEmpty(sc.nextLine(), "Player ID");
            if (searchPlayerByID(id) != null) {          // BR1
                System.out.println("Player ID already exists!");
                return;
            }
            p.setPlayerID(id);

            System.out.print("Enter Full Name: ");
            p.setFullName(Validator.requireNonEmpty(sc.nextLine(), "Full name"));

            p.setAge(readAge());                          // BR4
            p.setNationality(readNonEmpty("Nationality"));// BR2

            String position = readPosition();            // BR3
            p.setPosition(position);

            int shirt = readShirtNumber();               // BR5
            if (isShirtNumberTaken(shirt, null)) {       // BR6
                System.out.println("Shirt number " + shirt + " is already used by another active player!");
                return;
            }
            p.setShirtNumber(shirt);

            p.setBaseSalary(readSalary());               // BR11
            p.setStatus("Active");

            playerList.add(p);
            savePlayers();
            System.out.println("Player added successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayPlayers() {
        if (playerList.isEmpty()) {
            System.out.println("List of players is empty.");
            return;
        }
        printLine();
        System.out.printf("| %-8s | %-22s | %-3s | %-12s | %-4s | %-3s | %-12s | %-10s |%n",
                "ID", "Full Name", "Age", "Nationality", "Pos", "No.", "Base Salary", "Status");
        printLine();
        for (Player p : playerList) {
            System.out.println(p);
            System.out.println("Salary = " + p.calculateSalary());
        }
        printLine();
        System.out.println("Total players: " + playerList.size());
    }

    public Player searchPlayerByID(String playerID) {
        for (Player p : playerList) {
            if (p.getPlayerID().equalsIgnoreCase(playerID)) {
                return p;
            }
        }
        return null;
    }

    // FR2: update position, shirt number, base salary, status (type cannot change)
    public void updatePlayer() {
        System.out.print("Enter the ID of the player to update: ");
        String id = sc.nextLine().trim();
        Player p = searchPlayerByID(id);
        if (p == null) {
            System.out.println("Player not found with ID: " + id);
            return;
        }
        try {
            System.out.println("Current: " + p.getFullName() + " | " + p.getPosition()
                    + " | No." + p.getShirtNumber() + " | " + p.getStatus());

            p.setPosition(readPosition());

            int shirt = readShirtNumber();
            if (isShirtNumberTaken(shirt, p.getPlayerID())) {   // BR6
                System.out.println("Shirt number already used by another active player! Update aborted.");
                return;
            }
            p.setShirtNumber(shirt);

            p.setBaseSalary(readSalary());

            System.out.print("Enter new Status (Active/Inactive): ");
            String status = sc.nextLine().trim();
            if (!status.equalsIgnoreCase("Active") && !status.equalsIgnoreCase("Inactive")) {
                throw new InvalidInputException("Status must be Active or Inactive!"); // BR8
            }
            // BR7: reactivating must not duplicate an active shirt number
            if (status.equalsIgnoreCase("Active") && isShirtNumberTaken(p.getShirtNumber(), p.getPlayerID())) {
                System.out.println("Cannot activate: shirt number duplicates another active player!");
                return;
            }
            p.setStatus(status.equalsIgnoreCase("Active") ? "Active" : "Inactive");

            savePlayers();
            System.out.println("Player updated successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // FR3 / BR10: deactivate (never physically delete)
    public void deactivatePlayer() {
        System.out.print("Enter Player ID: ");
        String id = sc.nextLine().trim();
        Player p = searchPlayerByID(id);
        if (p != null) {
            p.setStatus("Inactive");
            savePlayers();
            System.out.println("Player deactivated successfully!");
        } else {
            System.out.println("Player not found!");
        }
    }

    public void savePlayers() {
        FileManager.savePlayersToFile(playerList, "players.txt");
    }

    public void loadPlayers() {
        playerList = FileManager.loadPlayersFromFile("players.txt");
    }

    // BR6: is this shirt number already used by another ACTIVE player?
    private boolean isShirtNumberTaken(int shirt, String excludeID) {
        for (Player p : playerList) {
            if (excludeID != null && p.getPlayerID().equalsIgnoreCase(excludeID)) {
                continue;
            }
            if (p.getStatus().equalsIgnoreCase("Active") && p.getShirtNumber() == shirt) {
                return true;
            }
        }
        return false;
    }

    // ---- validated input helpers: loop until the user enters something valid ----

    private int readAge() {
        while (true) {
            try {
                System.out.print("Enter Age (16-45): ");
                return Validator.validateAge(Validator.parseInt(sc.nextLine(), "Age"));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readShirtNumber() {
        while (true) {
            try {
                System.out.print("Enter Shirt Number (1-99): ");
                return Validator.validateShirtNumber(Validator.parseInt(sc.nextLine(), "Shirt number"));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readPosition() {
        while (true) {
            try {
                System.out.print("Enter Position (Goalkeeper/Defender/Midfielder/Forward): ");
                return Validator.validatePosition(sc.nextLine());
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private double readSalary() {
        while (true) {
            try {
                System.out.print("Enter Base Salary: ");
                return Validator.validateSalary(Validator.parseDouble(sc.nextLine(), "Base salary"));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readNonEmpty(String fieldName) {
        while (true) {
            try {
                System.out.print("Enter " + fieldName + ": ");
                return Validator.requireNonEmpty(sc.nextLine(), fieldName);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printLine() {
        for (int i = 0; i < 95; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
    
    // FR5 (Task S5): View Player Details based on Player ID
    public void viewPlayerDetails() {
        System.out.print("Enter Player ID to view details: ");
        String id = sc.nextLine().trim();
        
        // Tận dụng lại hàm tìm kiếm ID nội bộ của em
        Player p = searchPlayerByID(id); 
        
        if (p == null) {
            System.out.println("Player with ID '" + id + "' not found in the system!");
            return;
        }

        // IN RA MỘT CÁI PROFILE CARD DẠNG DỌC SIÊU ĐẸP VÀ CHUYÊN NGHIỆP
        System.out.println("\n===============================================");
        System.out.println("               PLAYER DETAILS             ");
        System.out.println("===============================================");
        System.out.printf("  Player ID:    %s%n", p.getPlayerID());
        System.out.printf("  Full Name:    %s%n", p.getFullName());
        System.out.printf("  Player Type:  %s%n", p.getPlayerType()); // Đa hình lấy nhãn Regular/Star
        System.out.printf("  Age:          %d years old%n", p.getAge());
        System.out.printf("  Nationality: %s%n", p.getNationality());
        System.out.printf("  Position:    %s%n", p.getPosition());
        System.out.printf("  Shirt Number: %d%n", p.getShirtNumber());
        System.out.printf("  Base Salary:  %,.2f VND%n", p.getBaseSalary());
        System.out.printf("  Status:       %s%n", p.getStatus());
        System.out.println("===============================================");
    }
}
