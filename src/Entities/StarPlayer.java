package Entities;

public class StarPlayer extends Player {

    @Override
    public double calculateSalary() {
        return getBaseSalary();
    }

    // BR26: Star Player bonus = Monthly Performance Points x 500,000 VND.
    @Override
    public double calculateBonus(int monthlyPerformancePoints) {
        return monthlyPerformancePoints * 500000.0;
    }

    @Override
    public String getPlayerType() {
        return "Star Player";
    }
}
