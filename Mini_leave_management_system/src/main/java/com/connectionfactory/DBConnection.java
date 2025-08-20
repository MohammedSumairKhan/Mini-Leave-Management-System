package com.connectionfactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static Connection con = null;
	private static final String URl = "jdbc:mysql://localhost:3306/leave_management_system";
	private static final String USER = "root";
	private static final String PASSWORD = "Sumair@1";

	public static Connection getConnection() {
		try {
			 // Step 1: Load MySQL Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Step 2: Establish connection
			con = DriverManager.getConnection(URl, USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Mysql Driver not found: " + e.getMessage());

		} catch (SQLException e) {
			System.out.println("Connection Failed: " + e.getMessage());
		}
		return con;
	}
}
