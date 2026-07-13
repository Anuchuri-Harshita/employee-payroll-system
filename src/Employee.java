
/**
 * Simple POJO representing one employee row.
 */
public class Employee {
    private int id;
    private String name;
    private String designation;
    private double basicSalary;
    private double hra;
    private double da;
    private double deductions;
 
    public Employee() { }
 
    public Employee(int id, String name, String designation, double basicSalary,
                     double hra, double da, double deductions) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
        this.deductions = deductions;
    }
 
    // Constructor without id, used when inserting a new employee
    public Employee(String name, String designation, double basicSalary,
                     double hra, double da, double deductions) {
        this(0, name, designation, basicSalary, hra, da, deductions);
    }
 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
 
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
 
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
 
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
 
    public double getHra() { return hra; }
    public void setHra(double hra) { this.hra = hra; }
 
    public double getDa() { return da; }
    public void setDa(double da) { this.da = da; }
 
    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }
 
    public double getNetSalary() {
        return basicSalary + hra + da - deductions;
    }
 
    @Override
    public String toString() {
        return String.format(
            "ID: %-4d | %-15s | %-18s | Basic: %-9.2f | HRA: %-8.2f | DA: %-8.2f | Deductions: %-9.2f | Net: %-10.2f",
            id, name, designation, basicSalary, hra, da, deductions, getNetSalary()
        );
    }
}
 
