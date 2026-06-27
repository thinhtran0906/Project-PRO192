package Manager;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import Exceptions.InvalidInputException;
import Utils.Validator;

public class TrainingManager {

    private ArrayList<TrainingSession> trainingList;
    private Scanner sc;

    public TrainingManager(Scanner sc) {
        this.sc = sc;
        trainingList = new ArrayList<>();
    }

    public TrainingSession searchById(String id) {
        for (TrainingSession t : trainingList) {
            if (t.getTrainingID().equalsIgnoreCase(id)) {
                return t;
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
}
