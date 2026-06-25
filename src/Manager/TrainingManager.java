import Entities.TrainingSession;
import java.util.ArrayList;

public class TrainingManager {

    private ArrayList<TrainingSession> trainingList;

    public TrainingManager() {
        trainingList = new ArrayList<>();
    }

    public void addTrainingSession(
            TrainingSession session) {

        trainingList.add(session);
    }

    public void displayTrainingHistory() {

        for (TrainingSession t : trainingList) {
            System.out.println(t);
        }
    }
}