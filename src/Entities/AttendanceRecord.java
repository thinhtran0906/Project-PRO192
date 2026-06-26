/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

public class AttendanceRecord {
    private String attendanceID;
    private String playerID;
    private String trainingID;
    private String attendanceStatus;

    public AttendanceRecord() {
    }

    public AttendanceRecord(String attendanceID, String playerID,
            String trainingID, String attendanceStatus) {

        this.attendanceID = attendanceID;
        this.playerID = playerID;
        this.trainingID = trainingID;
        this.attendanceStatus = attendanceStatus;
    }

    public String getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(String trainingID) {
        this.trainingID = trainingID;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return String.format(
                "| %-10s | %-10s | %-10s | %-10s |",
                attendanceID,
                playerID,
                trainingID,
                attendanceStatus
        );
    }
}
