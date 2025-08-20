package com.employee;

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
import jakarta.servlet.http.HttpSession;

@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {
		
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        
	        String username = request.getParameter("username");
	        String password = request.getParameter("password"); // assume stored hashed or plain text
	        
	        try (Connection con = DBConnection.getConnection()) {
	            String sql = "SELECT id, name, email, department, joining_date, leave_balance, password FROM employees WHERE name = ? AND password=?";
	            PreparedStatement pst = con.prepareStatement(sql);
	            pst.setString(1, username);
	            pst.setString(2, password);
	            ResultSet rs = pst.executeQuery();
	            
	            if(rs.next()) {
	                HttpSession session = request.getSession();
	                session.setAttribute("employeeId", rs.getInt("id"));
	                session.setAttribute("employeeName", rs.getString("name"));
	                session.setAttribute("employeeEmail", rs.getString("email"));
	                session.setAttribute("employeeDept", rs.getString("department"));
	                session.setAttribute("employeeJoin", rs.getDate("joining_date"));
	                session.setAttribute("employeeLeaveBalance", rs.getInt("leave_balance"));
	                
	                response.sendRedirect("employeedashboard.jsp");
	            } else {
	            	 response.sendRedirect("employeelogin.jsp?error=Invalid+Email+or+Password");
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
}
