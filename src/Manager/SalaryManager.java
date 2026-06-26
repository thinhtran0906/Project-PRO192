public class SalaryManager {
    public double calculateMonthlySalary(double baseSalary) {
        return baseSalary;
    }
    public double calculatePerformanceBonus(double baseSalary, double bonusRate) {
        return baseSalary * bonusRate;
    }

    
     public boolean validateSalaryRules(double baseSalary) {
        if (baseSalary > 0) {
            return true;
        } else {
            return false;
        }
    }
}
