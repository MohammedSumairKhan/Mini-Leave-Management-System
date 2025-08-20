package com.leaverequest;

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

@WebServlet("/ViewLeaveRequestsServlet")
public class ViewLeaveRequestsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	
        List<LeaveRequest> leaveList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT lr.id, e.name AS employee_name, lr.leave_type, lr.start_date, lr.end_date, lr.status "
                       + "FROM leave_requests lr "
                       + "JOIN employees e ON lr.employee_id = e.id "
                       + "WHERE lr.status = 'pending'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setId(rs.getInt("id"));
                lr.setEmployeeName(rs.getString("employee_name"));
                lr.setLeaveType(rs.getString("leave_type"));
                lr.setStartDate(rs.getDate("start_date"));
                lr.setEndDate(rs.getDate("end_date"));
                lr.setStatus(rs.getString("status"));
                leaveList.add(lr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("leaveRequests", leaveList);
        RequestDispatcher rd = request.getRequestDispatcher("admindashboard.jsp");
        rd.forward(request, response);
    }
}

