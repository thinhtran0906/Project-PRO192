
package Entities;
public class TrainingSession {

    private String trainingID;
    private String topic;

    public TrainingSession(String trainingID,
                           String topic) {

        this.trainingID = trainingID;
        this.topic = topic;
    }

    @Override
    public String toString() {
        return trainingID + " | " + topic;
    }
}
