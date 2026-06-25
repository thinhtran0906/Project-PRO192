package UI;

import Entities.Player;
import java.util.Scanner;
import Manager.PlayerManager;

public class Menu {
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        PlayerManager pm = new PlayerManager();

        int choice;

        do {
            System.out.println("\n===== PLAYER MANAGEMENT =====");
            System.out.println("1. Add Player");
            System.out.println("2. Display Players");
            System.out.println("3. Search Player");
            System.out.println("4. Update Player");
            System.out.println("5. Deactivate Player");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            try {
            choice = Integer.parseInt(sc.nextLine());
            
            } catch (NumberFormatException e) {
                
            System.out.println("Please enter a number!");
            choice = 0;
            }

            switch (choice) {
                case 1:
                    pm.addPlayer();
                    break;
                case 2:
                    pm.displayPlayers();
                    break;
                case 3:
                    System.out.print("Enter Player ID: ");
                    String playerID = sc.nextLine();

                    Player player = pm.searchPlayerByID(playerID);

                    if (player != null) {
                        System.out.println(player);
                    } else {
                        System.out.println("Player not found!");
                    }
                    break;
                case 4:
                    pm.updatePlayer();
                    break;
                case 5:
                    pm.deactivatePlayer();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }
}
