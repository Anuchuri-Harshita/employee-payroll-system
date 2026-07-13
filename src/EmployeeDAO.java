import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object: all raw SQL / JDBC calls live here,
 * kept separate from the console menu logic in PayrollApp.
 */
public class EmployeeDAO {

    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, designation, basic_salary, hra, da, deductions) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getDesignation());
            ps.setDouble(3, emp.getBasicSalary());
            ps.setDouble(4, emp.getHra());
            ps.setDouble(5, emp.getDa());
            ps.setDouble(6, emp.getDeductions());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // not found
    }

    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name=?, designation=?, basic_salary=?, hra=?, da=?, deductions=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getDesignation());
            ps.setDouble(3, emp.getBasicSalary());
            ps.setDouble(4, emp.getHra());
            ps.setDouble(5, emp.getDa());
            ps.setDouble(6, emp.getDeductions());
            ps.setInt(7, emp.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("designation"),
            rs.getDouble("basic_salary"),
            rs.getDouble("hra"),
            rs.getDouble("da"),
            rs.getDouble("deductions")
        );
    }
}