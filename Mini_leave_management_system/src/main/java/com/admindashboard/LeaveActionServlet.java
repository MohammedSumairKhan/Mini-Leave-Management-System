package com.admindashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.connectionfactory.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LeaveActionServlet")
public class LeaveActionServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init() {
        con = DBConnection.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int leaveId = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action").toUpperCase(); // APPROVED or REJECTED

        try {
            // First, fetch current leave request info
            String fetchSql = "SELECT status, employee_id, DATEDIFF(end_date, start_date)+1 AS days FROM leave_requests WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(fetchSql)) {
                ps.setInt(1, leaveId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String currentStatus = rs.getString("status");
                    int employeeId = rs.getInt("employee_id");
                    int days = rs.getInt("days");

                    // Only update if status is PENDING
                    if ("PENDING".equalsIgnoreCase(currentStatus)) {
                        // Update leave_requests status
                        String updateSql = "UPDATE leave_requests SET status=?, reviewed_at=NOW() WHERE id=?";
                        try (PreparedStatement ps2 = con.prepareStatement(updateSql)) {
                            ps2.setString(1, action);
                            ps2.setInt(2, leaveId);
                            ps2.executeUpdate();
                        }

                        // If approved, update leave_balance
                        if ("APPROVED".equals(action)) {
                            String updateBalance = "UPDATE leave_balance SET leaves_taken = leaves_taken + ? WHERE employee_id=?";
                            try (PreparedStatement ps3 = con.prepareStatement(updateBalance)) {
                                ps3.setInt(1, days);
                                ps3.setInt(2, employeeId);
                                ps3.executeUpdate();
                            }
                        }
                    }
                }
            }

            // Send admin a confirmation message
            String message = "APPROVED".equals(action) ? "Leave Approved" : "Leave Rejected";
            resp.getWriter().write("<script>alert('" + message + "');window.location='admindashboard.jsp';</script>");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("<script>alert('Error occurred');window.location='admindashboard.jsp';</script>");
        }
    }
}
