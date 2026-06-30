package Manager;

import Entities.Player;

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
}
