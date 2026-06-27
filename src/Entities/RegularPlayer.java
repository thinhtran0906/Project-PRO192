package Entities;

public class RegularPlayer extends Player {

    @Override
    public double calculateSalary() {
        return getBaseSalary();
    }

    // BR26: Regular Player bonus = 0 VND.
    @Override
    public double calculateBonus(int monthlyPerformancePoints) {
        return 0;
    }

    @Override
    public String getPlayerType() {
        return "Regular Player";
    }
}
