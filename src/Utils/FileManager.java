package Utils;

import Entities.Player;
import Entities.RegularPlayer;
import Entities.StarPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import Entities.TrainingSession;
import java.time.LocalDate;
import Entities.MatchRecord;
import Entities.PerformanceRecord;
import Entities.AttendanceRecord;

public class FileManager {

    /**
     * Ghi danh sách cầu thủ xuống file .txt theo định dạng CSV (cách nhau bởi dấu phẩy)
     * Sử dụng Try-with-resources để tự động đóng luồng (Auto-close)
     */
    public static void savePlayersToFile(ArrayList<Player> playerList, String filename) {
        // Khai báo PrintWriter bên trong () của try sẽ giúp tự động đóng file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Player p : playerList) {
                // Lưu theo thứ tự: Type, ID, Name, Age, Nationality, Position, Shirt, Salary, Status
                pw.println(p.getPlayerType() + "," +
                           p.getPlayerID() + "," +
                           p.getFullName() + "," +
                           p.getAge() + "," +
                           p.getNationality() + "," +
                           p.getPosition() + "," +
                           p.getShirtNumber() + "," +
                           p.getBaseSalary() + "," +
                           p.getStatus());
            }
            System.out.println("Players data successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Đọc file .txt lên và nạp lại vào ArrayList
     * Có xử lý Đa hình (Khởi tạo lại Regular hoặc Star)
     */
    public static ArrayList<Player> loadPlayersFromFile(String filename) {
        ArrayList<Player> playerList = new ArrayList<>();
        File file = new File(filename);

        // Bắt lỗi mượt mà nếu file chưa tồn tại (chạy lần đầu)
        if (!file.exists()) {
            System.out.println("Data file not found. Starting with an empty player list.");
            return playerList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // Nếu đọc đúng 9 cột dữ liệu
                if (data.length == 9) {
                    String type = data[0].trim();
                    String id = data[1].trim();
                    String name = data[2].trim();
                    int age = Integer.parseInt(data[3].trim());
                    String nationality = data[4].trim();
                    String position = data[5].trim();
                    int shirtNumber = Integer.parseInt(data[6].trim());
                    double baseSalary = Double.parseDouble(data[7].trim());
                    String status = data[8].trim();

                    // ĐA HÌNH KHI ĐỌC FILE: Tạo đúng loại Object dựa vào cột Type
                    Player p;
                    if (type.equalsIgnoreCase("Star Player")) {
                        p = new StarPlayer();
                    } else {
                        p = new RegularPlayer();
                    }
                    
                    p.setPlayerID(id);
                    p.setFullName(name);
                    p.setAge(age);
                    p.setNationality(nationality);
                    p.setPosition(position);
                    p.setShirtNumber(shirtNumber);
                    p.setBaseSalary(baseSalary);
                    p.setStatus(status);

                    playerList.add(p);
                }
            }
            System.out.println("Players data successfully loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Data format error in file. Data might be corrupted: " + e.getMessage());
        }
        
        return playerList;
    }

    public static void saveTrainingToFile(ArrayList<TrainingSession> trainingList, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

            for (TrainingSession t : trainingList) {
               pw.println(
                    t.getTrainingID() + "," +
                    t.getDate() + "," +
                    t.getLocation() + "," +
                    t.getTopic() + "," +
                    String.join(";", t.getPlayerSnapshot()));
            }

            System.out.println("Training data successfully saved.");

        } catch (IOException e) {
            System.out.println("Error saving training: " + e.getMessage());
        }
    }
    public static ArrayList<TrainingSession> loadTrainingFromFile(String filename) {

    ArrayList<TrainingSession> trainingList = new ArrayList<>();
    File file = new File(filename);

    if (!file.exists()) {
        return trainingList;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            if (data.length >= 4) {

                String id = data[0].trim();
                LocalDate date = LocalDate.parse(data[1].trim());
                String location = data[2].trim();
                String topic = data[3].trim();
    TrainingSession training =
            new TrainingSession(id, date, location, topic);
            if (data.length == 5) {

        ArrayList<String> snapshot = new ArrayList<>();

            if (!data[4].trim().isEmpty()) {

            String[] ids = data[4].split(";");

            for (String s : ids) {
                snapshot.add(s.trim());
            }
        }

        training.setPlayerSnapshot(snapshot);
    }


                trainingList.add(training);
            }
        }

    } catch (Exception e) {
        System.out.println("Error loading training: " + e.getMessage());
    }

    return trainingList;
}
    public static void saveMatchToFile(ArrayList<MatchRecord> matchList, String filename) {

    try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

        for (MatchRecord m : matchList) {

            pw.println(
                    m.getMatchID() + "," +
                    m.getDate() + "," +
                    m.getOpponent() + "," +
                    m.getMatchType());

        }

        System.out.println("Match data successfully saved.");

    } catch (IOException e) {
        System.out.println("Error saving match: " + e.getMessage());
    }
}
    public static ArrayList<MatchRecord> loadMatchFromFile(String filename) {

    ArrayList<MatchRecord> matchList = new ArrayList<>();
    File file = new File(filename);

    if (!file.exists()) {
        return matchList;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            if (data.length == 4) {

                String id = data[0].trim();
                LocalDate date = LocalDate.parse(data[1].trim());
                String opponent = data[2].trim();
                String type = data[3].trim();

                matchList.add(
                        new MatchRecord(id, date, opponent, type)
                );
            }
        }

    } catch (Exception e) {
        System.out.println("Error loading match: " + e.getMessage());
    }

    return matchList;
}
    public static void savePerformanceToFile(ArrayList<PerformanceRecord> performanceList, String filename) {

    try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

        for (PerformanceRecord p : performanceList) {

            pw.println(
                    p.getMatchID() + "," +
                    p.getPlayerID() + "," +
                    p.getGoals() + "," +
                    p.getAssists() + "," +
                    p.getYellowCards() + "," +
                    p.getRedCards() + "," +
                    p.getMinutesPlayed());

        }

    } catch (IOException e) {
        System.out.println("Error saving performance: " + e.getMessage());
    }
}
    public static ArrayList<PerformanceRecord> loadPerformanceFromFile(String filename) {

    ArrayList<PerformanceRecord> performanceList = new ArrayList<>();

    File file = new File(filename);

    if (!file.exists()) {
        return performanceList;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            if (data.length == 7) {

                performanceList.add(
                        new PerformanceRecord(
                                data[0].trim(),
                                data[1].trim(),
                                Integer.parseInt(data[2].trim()),
                                Integer.parseInt(data[3].trim()),
                                Integer.parseInt(data[4].trim()),
                                Integer.parseInt(data[5].trim()),
                                Integer.parseInt(data[6].trim())
                        ));
            }
        }

    } catch (Exception e) {
        System.out.println("Error loading performance: " + e.getMessage());
    }

    return performanceList;
}
    public static void saveAttendanceToFile(ArrayList<AttendanceRecord> attendanceList, String filename) {

    try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

        for (AttendanceRecord a : attendanceList) {

            pw.println(
                    a.getAttendanceID() + "," +
                    a.getPlayerID() + "," +
                    a.getTrainingID() + "," +
                    a.getAttendanceStatus());

        }

        System.out.println("Attendance data successfully saved.");

    } catch (IOException e) {
        System.out.println("Error saving attendance: " + e.getMessage());
    }
}
    public static ArrayList<AttendanceRecord> loadAttendanceFromFile(String filename) {

    ArrayList<AttendanceRecord> attendanceList = new ArrayList<>();

    File file = new File(filename);

    if (!file.exists()) {
        return attendanceList;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            if (data.length == 4) {

                attendanceList.add(

                        new AttendanceRecord(

                                data[0].trim(),
                                data[1].trim(),
                                data[2].trim(),
                                data[3].trim()
                        )
                );

            }

        }

    } catch (Exception e) {

        System.out.println("Error loading attendance: " + e.getMessage());

    }

    return attendanceList;
}
}

    
