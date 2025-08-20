package com.employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.connectionfactory.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ApplyLeaveServlet")
public class ApplyLeaveServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer employeeId = (Integer) session.getAttribute("employeeId");

        if (employeeId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String leaveType = request.getParameter("leaveType");
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String reason = request.getParameter("reason");

        try (Connection con = DBConnection.getConnection()) {

            Date fromDate = Date.valueOf(fromDateStr);
            Date toDate = Date.valueOf(toDateStr);

            // 1️ Fetch employee joining date and validate employee exists
            String joinSql = "SELECT joining_date FROM employees WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(joinSql)) {
                ps.setInt(1, employeeId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    response.getWriter().write("<script>alert('Employee not found!');window.location='employeedashboard.jsp';</script>");
                    return;
                }
                Date joiningDate = rs.getDate("joining_date");

                // 2️ Validate leave start date is not before joining date
                if (fromDate.before(joiningDate)) {
                    response.getWriter().write("<script>alert('Cannot apply leave before joining date!');window.location='employeedashboard.jsp';</script>");
                    return;
                }

                // 3️ Validate end date is after start date
                if (toDate.before(fromDate)) {
                    response.getWriter().write("<script>alert('End date cannot be before start date!');window.location='employeedashboard.jsp';</script>");
                    return;
                }
            }

            // 4️ Check available leave balance
            String balanceSql = "SELECT leaves_remaining FROM leave_balance WHERE employee_id=?";
            try (PreparedStatement ps = con.prepareStatement(balanceSql)) {
                ps.setInt(1, employeeId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int remaining = rs.getInt("leaves_remaining");
                    int requestedDays = (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
                    if (requestedDays > remaining) {
                        response.getWriter().write("<script>alert('You are applying more days than available balance!');window.location='applyLeave.jsp';</script>");
                        return;
                    }
                } else {
                    response.getWriter().write("<script>alert('Leave balance not found!');window.location='employeedashboard.jsp';</script>");
                    return;
                }
            }

            // 5️ Check for overlapping leave requests
            String overlapSql = "SELECT * FROM leave_requests WHERE employee_id=? AND status IN ('PENDING','APPROVED') AND "
                    + "((start_date <= ? AND end_date >= ?) OR (start_date <= ? AND end_date >= ?))";
            try (PreparedStatement ps = con.prepareStatement(overlapSql)) {
                ps.setInt(1, employeeId);
                ps.setDate(2, toDate);   // existing start <= new end
                ps.setDate(3, fromDate); // existing end >= new start
                ps.setDate(4, toDate);   // check overlap both sides
                ps.setDate(5, fromDate);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    response.getWriter().write("<script>alert('You have overlapping leave requests!');window.location='employeedashboard.jsp';</script>");
                    return;
                }
            }

            // ✅ All validations passed — insert leave request
            String sql = "INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, reason, status) "
                    + "VALUES (?, ?, ?, ?, ?, 'PENDING')";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, employeeId);
                pst.setString(2, leaveType);
                pst.setDate(3, fromDate);
                pst.setDate(4, toDate);
                pst.setString(5, reason);
                pst.executeUpdate();
            }

            response.getWriter().write("<script>alert('Leave applied successfully!');window.location='employeedashboard.jsp';</script>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('Error occurred!');window.location='employeedashboard.jsp';</script>");
        }
    }
}
