package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.connectionfactory.DBConnection;

public class AdminRegister {
	public static void main(String[] args) {
		//Scanner to take user input
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter the username: ");
		String username = scan.nextLine();
		
		System.out.println("Enter the password: ");
		String plainPassword = scan.nextLine(); 
		
		try {
			// 1. Hash password using BCrypt
			String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

			// 2. DB connection
			Connection con = DBConnection.getConnection();

			// 3. Insert into admin table
			String query = "INSERT INTO admin (username, password_hash) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, hashedPassword);
			

			int rows = ps.executeUpdate();
			
			if (rows > 0) {
				System.out.println("Admin registered successfully!");
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
