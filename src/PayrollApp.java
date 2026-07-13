import java.util.List;
import java.util.Scanner;

/**
 * Console-based menu that drives the EmployeeDAO.
 * Run this class's main() method to use the app.
 */
public class PayrollApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeDAO dao = new EmployeeDAO();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAllEmployees();
                case 3 -> viewPayslip();
                case 4 -> updateEmployee();
                case 5 -> deleteEmployee();
                case 6 -> {
                    running = false;
                    System.out.println("Exiting...!");
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        DBConnection.closeConnection();
    }

    private static void printMenu() {
        System.out.println("\n===== EMPLOYEE PAYROLL SYSTEM =====");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. View Payslip (by ID)");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Exit");
    }

    private static void addEmployee() {
        System.out.println("\n-- Add New Employee --");
        String name = readString("Name: ");
        String designation = readString("Designation: ");
        double basic = readDouble("Basic Salary: ");
        double hra = readDouble("HRA: ");
        double da = readDouble("DA: ");
        double deductions = readDouble("Deductions: ");

        Employee emp = new Employee(name, designation, basic, hra, da, deductions);

        if (dao.addEmployee(emp)) {
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Failed to add employee.");
        }
    }

    private static void viewAllEmployees() {
        System.out.println("\n-- All Employees --");
        List<Employee> employees = dao.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    private static void viewPayslip() {
        int id = readInt("Enter Employee ID: ");
        Employee e = dao.getEmployeeById(id);

        if (e == null) {
            System.out.println("No employee found with ID " + id);
            return;
        }

        System.out.println("\n---------- PAYSLIP ----------");
        System.out.println("Employee ID   : " + e.getId());
        System.out.println("Name          : " + e.getName());
        System.out.println("Designation   : " + e.getDesignation());
        System.out.println("Basic Salary  : " + e.getBasicSalary());
        System.out.println("HRA           : " + e.getHra());
        System.out.println("DA            : " + e.getDa());
        System.out.println("Deductions    : " + e.getDeductions());
        System.out.println("------------------------------");
        System.out.println("NET SALARY    : " + e.getNetSalary());
        System.out.println("------------------------------");
    }

    private static void updateEmployee() {
        int id = readInt("Enter Employee ID to update: ");
        Employee existing = dao.getEmployeeById(id);

        if (existing == null) {
            System.out.println("No employee found with ID " + id);
            return;
        }

        System.out.println("Leave blank to keep current value.");

        String name = readString("Name [" + existing.getName() + "]: ");
        if (!name.isBlank()) existing.setName(name);

        String designation = readString("Designation [" + existing.getDesignation() + "]: ");
        if (!designation.isBlank()) existing.setDesignation(designation);

        existing.setBasicSalary(readDoubleOrKeep("Basic Salary", existing.getBasicSalary()));
        existing.setHra(readDoubleOrKeep("HRA", existing.getHra()));
        existing.setDa(readDoubleOrKeep("DA", existing.getDa()));
        existing.setDeductions(readDoubleOrKeep("Deductions", existing.getDeductions()));

        if (dao.updateEmployee(existing)) {
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Failed to update employee.");
        }
    }

    private static void deleteEmployee() {
        int id = readInt("Enter Employee ID to delete: ");
        if (dao.deleteEmployee(id)) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Failed to delete employee (check the ID).");
        }
    }

    // ---------- Small input helpers ----------

    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine(); // consume newline
        return val;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }

    private static double readDoubleOrKeep(String label, double currentValue) {
        System.out.print(label + " [" + currentValue + "]: ");
        String input = sc.nextLine().trim();
        if (input.isBlank()) return currentValue;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping old value.");
            return currentValue;
        }
    }
}