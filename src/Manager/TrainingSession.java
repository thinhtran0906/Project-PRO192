package Manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrainingSession {

    private String trainingID;
    private LocalDate date;
    private String location;
    private String topic;

    public TrainingSession(String trainingID, LocalDate date, String location, String topic) {
        this.trainingID = trainingID;
        this.date = date;
        this.location = location;
        this.topic = topic;
    }

    public String getTrainingID() { return trainingID; }
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public String getTopic() { return topic; }

    @Override
    public String toString() {
        return String.format("%-6s %-12s %-22s %s",
                trainingID,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                location,
                topic);
    }
}
