package Entities;

public class StarPlayer extends Player {

    private double bonus;

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + bonus;
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
