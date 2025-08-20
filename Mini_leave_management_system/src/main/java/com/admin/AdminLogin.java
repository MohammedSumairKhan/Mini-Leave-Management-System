package com.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import com.connectionfactory.DBConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	Connection con = null;

	@Override
	public void init() throws ServletException {
		con = DBConnection.getConnection();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("username");
		String password = req.getParameter("password");
		try {

			String sql = "SELECT password_hash FROM admin WHERE username=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String storedHashedPassword = rs.getString("password_hash");

				// Password verify
				if (BCrypt.checkpw(password, storedHashedPassword)) {
					
					//if login success then forwards the admin dashboard
					 req.getRequestDispatcher("admindashboard.jsp").forward(req, resp); 
					
				} else {
					
					 req.setAttribute("errorMessage", "Invalid username or password!");
					 req.getRequestDispatcher("adminlogin.jsp").forward(req, resp);
					
				}
			} else {
				req.setAttribute("errorMessage", "User not found!");
				 req.getRequestDispatcher("adminlogin.jsp").forward(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
