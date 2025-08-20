package com.employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.connectionfactory.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
            response.getWriter().println("All fields are required!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            // Delete employee
            String sql = "DELETE FROM employees WHERE name = ? AND email = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);

                int rowsDeleted = ps.executeUpdate(); // returns number of affected rows
                if (rowsDeleted > 0) {
                    response.getWriter().println("Employee Deleted");
                } else {
                    response.getWriter().println("No matching employee found");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
