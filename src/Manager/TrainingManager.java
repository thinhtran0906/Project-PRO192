package Manager;

import Entities.TrainingSession;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import Exceptions.InvalidInputException;
import Utils.Validator;
import Entities.AttendanceRecord;
import Entities.Player;
import Utils.FileManager;

public class TrainingManager {

    private ArrayList<TrainingSession> trainingList;
    private Scanner sc;
    private ArrayList<AttendanceRecord> attendanceList;

   public TrainingManager(Scanner sc) {
    this.sc = sc;
    trainingList = FileManager.loadTrainingFromFile("training.txt");
    attendanceList = new ArrayList<>();
}
    public void saveTraining() {
    FileManager.saveTrainingToFile(trainingList, "training.txt");
}
    public TrainingSession searchById(String id) {
        for (TrainingSession t : trainingList) {
            if (t.getTrainingID().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }
    private AttendanceRecord findAttendance(String trainingID, String playerID) {
    for (AttendanceRecord a : attendanceList) {
        if (a.getTrainingID().equalsIgnoreCase(trainingID)
                && a.getPlayerID().equalsIgnoreCase(playerID)) {
            return a;
        }
    }
    return null;
    }

    // FR7 / BR1, BR12: create a training session
    public void createTrainingSession() {
        try {
            System.out.print("Training ID: ");
            String id = Validator.requireNonEmpty(sc.nextLine(), "Training ID");
            if (searchById(id) != null) {
                System.out.println("Training ID already exists!");
                return;
            }

            System.out.print("Date (dd/MM/yyyy): ");
            LocalDate date = Validator.validateDate(sc.nextLine());

            System.out.print("Location: ");
            String location = Validator.requireNonEmpty(sc.nextLine(), "Location");

            System.out.print("Training Topic: ");
            String topic = Validator.requireNonEmpty(sc.nextLine(), "Topic");

            trainingList.add(new TrainingSession(id, date, location, topic));
            saveTraining();
            System.out.println("Training session created successfully.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // FR11: view training history
    public void displayTrainingHistory() {
        if (trainingList.isEmpty()) {
            System.out.println("No training history found!");
            return;
        }
    
        System.out.printf("%-6s %-12s %-22s %s%n", "ID", "Date", "Location", "Topic");
        System.out.println("------------------------------------------------------------");
        for (TrainingSession t : trainingList) {
            System.out.println(t);
        }
    }
   public void recordAttendance(PlayerManager pm) {

    System.out.print("Training ID: ");
    String id = sc.nextLine();

    TrainingSession training = searchById(id);

       if (training == null) {
        System.out.println("Training not found!");
        return;
    }
    ArrayList<Player> activePlayers = pm.getActivePlayers();

    System.out.println("Training ID: " + training.getTrainingID());
    System.out.println("Date: " + training.getDate());
    System.out.println("Total Active Players: " + activePlayers.size());

    System.out.println("Active Players:");

    for (Player p : activePlayers) {
    System.out.println(p.getPlayerID() + " - " + p.getFullName());
}
    System.out.println("Enter absent Player IDs (separated by commas).");
    System.out.println("Leave blank if everyone is present.");
    System.out.print("Absent Player IDs: ");

    String input = sc.nextLine().trim();
    String[] absentIDs;

if (input.isEmpty()) {
    absentIDs = new String[0];
} else {
    absentIDs = input.split(",");
}
    for (String idAbsent : absentIDs) {

    boolean found = false;

    for (Player p : activePlayers) {

        if (p.getPlayerID().equalsIgnoreCase(idAbsent.trim())) {
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("Player ID " + idAbsent + " is invalid!");
        return;
    }
}
    for (int i = 0; i < absentIDs.length; i++) {

    for (int j = i + 1; j < absentIDs.length; j++) {

        if (absentIDs[i].trim().equalsIgnoreCase(absentIDs[j].trim())) {

            System.out.println("Duplicate Player ID!");

            return;
        }
    }
}

    int present = 0;
    int absent = 0;
    for (Player player : activePlayers) {

        String status = "Present";

        for (String idAbsent : absentIDs) {
            if (player.getPlayerID().equalsIgnoreCase(idAbsent.trim())) {
                status = "Absent";
                break;
            }
        }

        AttendanceRecord attendance = new AttendanceRecord(
                training.getTrainingID() + "_" + player.getPlayerID(),
                player.getPlayerID(),
                training.getTrainingID(),
                status);

        AttendanceRecord old = findAttendance(
                training.getTrainingID(),
                player.getPlayerID());

             if (old != null) {
        attendanceList.remove(old);
}

        attendanceList.add(attendance);

             if (status.equals("Present")) {
            present++;
        } else {
            absent++;
        }
    }

    System.out.println("Training attendance recorded successfully.");

    System.out.println("Summary:");

    System.out.println("Present: " + present);

    System.out.println("Absent: " + absent);
}
    }



