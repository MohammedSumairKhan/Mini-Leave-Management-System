package com.admindashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.connectionfactory.DBConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewEmployeesServlet")
public class ViewEmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Employee> employeeList = new ArrayList<>();
		Connection con = null;
		try {

			con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, email, department, joining_date, leave_balance FROM employees");
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setEmail(rs.getString("email"));
				emp.setDepartment(rs.getString("department"));
				emp.setJoiningDate(rs.getDate("joining_date"));
				emp.setLeaveBalance(rs.getInt("leave_balance"));
				employeeList.add(emp);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("employees", employeeList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("admindashboard.jsp");
		dispatcher.forward(request, response);
		// System.out.println("Total employees fetched: " + employeeList.size());
																							
		
	}
}
