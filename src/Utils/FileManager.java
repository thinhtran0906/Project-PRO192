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
}