package com.admindashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.connectionfactory.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String joiningDate = request.getParameter("joiningDate");
        String password = name + "@121";

        if(name == null || email == null || department == null || joiningDate == null) {
            response.getWriter().println("All fields are required!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            // Insert employee
            String sql = "INSERT INTO employees (name, email, department, joining_date,password) VALUES (?, ?, ?, ?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, department);
                ps.setDate(4, java.sql.Date.valueOf(joiningDate));
                ps.setString(5, password);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    // Get generated employee ID
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int employeeId = generatedKeys.getInt(1);

                        // Insert into leave_balance with default 20 leaves
                        String leaveBalanceSql = "INSERT INTO leave_balance (employee_id, total_leaves) VALUES (?, 20)";
                        try (PreparedStatement ps2 = con.prepareStatement(leaveBalanceSql)) {
                            ps2.setInt(1, employeeId);
                            ps2.executeUpdate();
                        }
                    }

                    response.getWriter().println("Employee added successfully!");
                } else {
                    response.getWriter().println("Error adding employee.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
