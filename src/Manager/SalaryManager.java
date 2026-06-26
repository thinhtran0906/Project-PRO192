import java.util.ArrayList;
import java.util.Scanner;

public class SalaryManager {
    ArrayList<Double> salaries = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void addSalary() {
        System.out.print("Enter salary: ");
        salaries.add(sc.nextDouble());
    }

    public void displaySalaries() {
        for (int i = 0; i < salaries.size(); i++) {
            System.out.println("Salary " + i + ": " + salaries.get(i));
        }
    }

    public void updateSalary() {
        System.out.print("Enter position: ");
        int i = sc.nextInt();

        System.out.print("Enter new salary: ");
        salaries.set(i, sc.nextDouble());
    }

    public void deactivateSalary() {
        System.out.print("Enter position: ");
        int i = sc.nextInt();

        salaries.set(i, 0.0);
    }
}
