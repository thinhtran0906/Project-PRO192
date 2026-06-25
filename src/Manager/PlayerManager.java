package Manager;

    import java.util.ArrayList;
    import java.util.Scanner;
    import Entities.Player;
    import Entities.RegularPlayer;
    import Entities.StarPlayer;

    public class PlayerManager {

    private ArrayList<Player> playerList;
    private Scanner sc;
    
    public PlayerManager(){
    playerList = new ArrayList<>();
    sc = new Scanner(System.in);
}

    public void addPlayer() {
         try {
        Player p;
        System.out.println("Choose Player Type:");
        System.out.println("1. Regular Player");
        System.out.println("2. Star Player");

        int type = Integer.parseInt(sc.nextLine());
        
        if(type == 1){
        p = new RegularPlayer();
        }
        else{
        StarPlayer sp = new StarPlayer();

        System.out.print("Enter Bonus: ");
        sp.setBonus(Double.parseDouble(sc.nextLine()));

        p = sp;
}
        
        System.out.println(" Enter Player ID: ");
        p.setPlayerID(sc.nextLine());
        if ( searchPlayerByID(p.getPlayerID()) != null ){
            System.out.println(" Player ID exists ");
            return; 
        }
        System.out.println(" Enter your Full Name: ");
        p.setFullName(sc.nextLine()); 
        System.out.println(" Enter your Age: ");
        p.setAge(Integer.parseInt(sc.nextLine())); 
        System.out.println(" Enter Nationality: ");
        p.setNationality(sc.nextLine());
        System.out.println(" Enter position: ");
        p.setPosition(sc.nextLine());
        System.out.println(" Enter your Shirt Number: ");
        p.setShirtNumber(Integer.parseInt(sc.nextLine()));
         
        double salary;
        while (true) {
            try {
                System.out.print("Enter Base Salary: ");
                salary = Double.parseDouble(sc.nextLine());
                if (salary > 0) {
            break;
        }
        System.out.println("Salary must be greater than 0!");
    } catch (NumberFormatException e) {
        System.out.println("Invalid number format! Please enter numbers only.");
    }
}
        p.setBaseSalary(salary);
    
        p.setStatus("Active");
        playerList.add(p);
        System.out.println(" Player add successfull");
         
    } catch (NumberFormatException e) {
         System.out.println(" Error" + e.getMessage());    
    }
}
    
    public void displayPlayers() {
        if ( playerList.isEmpty() ) {
            System.out.print(" List of players is empty ");
            return; 
        }
        for (int i = 0; i < 95; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.printf("| %-8s | %-22s | %-3s | %-12s | %-4s | %-3s | %-12s | %-10s |\n",
                "ID", "Full Name", "Age", "Nationality", "Pos", "No.", "Base Salary", "Status");
        for (int i = 0; i < 95; i++) {
            System.out.print("=");
        }
        System.out.println();
        for ( Player p : playerList) {
            System.out.println(p);

            System.out.println(
                "Salary = " + p.calculateSalary()
                );
        }
        for (int i = 0; i < 95; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.println(" Total players: " + playerList.size());
    }

    public Player searchPlayerByID(String playerID) {
        for (Player p : playerList) {
        if (p.getPlayerID().equalsIgnoreCase(playerID)) {
            return p;
        }
    }
    return null;
    }

    public void updatePlayer() {
        System.out.print("Enter the ID of the player to update: ");
        String id = sc.nextLine();
        Player p = searchPlayerByID(id);

    if (p != null) {
        System.out.print("Enter new full name: ");
        p.setFullName(sc.nextLine());
        try {

            System.out.print("Enter new age: ");
            p.setAge(Integer.parseInt(sc.nextLine()));

            System.out.println("Update successful!");

        } catch (NumberFormatException e) {

            System.out.println("Invalid age! Update failed.");

        }
    } else {
        System.out.println("Player not found with ID: " + id);
    }
    }

    public void deactivatePlayer() {
    System.out.print("Enter Player ID: ");
    String id = sc.nextLine();

    Player p = searchPlayerByID(id);
    if (p != null) {
        p.setStatus("Inactive");
        System.out.println("Player deactivated successfully!");
    } else {
        System.out.println("Player not found!");
    }
    }
}
