package com.leavereport;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.connectionfactory.DBConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LeaveReportServlet")
public class LeaveReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<LeaveReport> reports = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT e.id, e.name, lb.leaves_taken, lb.leaves_remaining " +
                         "FROM employees e " +
                         "JOIN leave_balance lb ON e.id = lb.employee_id";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                LeaveReport report = new LeaveReport();
                report.setEmployeeId(rs.getInt("id"));
                report.setEmployeeName(rs.getString("name"));
                report.setLeavesTaken(rs.getInt("leaves_taken"));
                report.setLeavesRemaining(rs.getInt("leaves_remaining"));
                reports.add(report);
            }

            request.setAttribute("reports", reports);
            RequestDispatcher rd = request.getRequestDispatcher("admindashboard.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}	
