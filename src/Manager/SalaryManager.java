package Manager;

import Entities.Player;
import Exceptions.InvalidInputException;
import Utils.Validator;
import java.util.Scanner;

public class SalaryManager {

    // Calculate bonus
    public double calculatePerformanceBonus(Player player, int points) {
        return player.calculateBonus(points);
    }

    // Calculate monthly salary
    public double calculateMonthlySalary(Player player, int points) {
        return player.getBaseSalary() + calculatePerformanceBonus(player, points);
    }

    // Validate salary
    public boolean validateSalaryRules(Player player) {
        return player.getBaseSalary() > 0;
    }

    // Task S12
    public void calculatePlayerSalary(PlayerManager pm,
                                      MatchManager mm,
                                      Scanner sc) {

        try {

            System.out.println("----------- CALCULATE PLAYER SALARY -----------");

            System.out.print("Enter Month: ");
            int month = Validator.validateMonth(
                    Validator.parseInt(sc.nextLine(), "Month"));

            System.out.print("Enter Year: ");
            int year = Validator.validateYear(
                    Validator.parseInt(sc.nextLine(), "Year"));

            System.out.print("Enter Player ID: ");
            String id = sc.nextLine().trim();

            Player p = pm.searchPlayerByID(id);

            if (p == null) {
                System.out.println("Player not found!");
                return;
            }

            int points = mm.getMonthlyPerformancePoints(id, month, year);

            double bonus = calculatePerformanceBonus(p, points);
            double total = calculateMonthlySalary(p, points);

            System.out.println();
            System.out.println("Player: " + p.getFullName());
            System.out.println("Type: " + p.getPlayerType());
            System.out.println("Base Salary: " + p.getBaseSalary());
            System.out.println("Monthly Performance Points: " + points);

            System.out.println("\nSalary Summary:");
            System.out.printf("Base Salary: %.0f VND%n", p.getBaseSalary());
            System.out.printf("Performance Bonus: %.0f VND%n", bonus);
            System.out.printf("Total Salary: %.0f VND%n", total);

        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }
}
