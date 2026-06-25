package Entities;

public abstract class Player {
    protected String playerID;
    protected String fullName;
    protected int age;
    protected String nationality;
    protected String position;
    protected int shirtNumber;
    protected double baseSalary;
    protected String status;

    public Player() {
    }
    public Player(String playerID, String fullName, int age,
                  String nationality, String position,
                  int shirtNumber, double baseSalary, String status) {

        this.playerID = playerID;
        this.fullName = fullName;
        this.age = age;
        this.nationality = nationality;
        this.position = position;
        this.shirtNumber = shirtNumber;
        this.baseSalary = baseSalary;
        this.status = status;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 16 && age <= 45) {
            this.age = age;
        } else {
            System.out.println("Invalid age!");
        }
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
        "| %-8s | %-22s | %-3d | %-12s | %-4s | %-3d | %-12.2f | %-10s |",
        playerID,
        fullName,
        age,
        nationality,
        position,
        shirtNumber,
        calculateSalary(),
        status
        );
    }
    public abstract double calculateSalary();
}
