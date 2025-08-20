package com.admindashboard;

import java.sql.Date;

public class Employee {
    private int id;
    private String name;
    private String email;
    private String department;
    private Date joiningDate;
    private int leaveBalance;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(int id, String name, String email, String department, Date joiningDate, int leaveBalance ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.joiningDate = joiningDate;
        this.leaveBalance = leaveBalance;
    }

    // Getters and Setters

    public int getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", department=" + department
				+ ", joiningDate=" + joiningDate + ", leaveBalance=" + leaveBalance + "]";
	}

}
