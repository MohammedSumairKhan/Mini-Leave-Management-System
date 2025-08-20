package com.leaverequest;

import java.sql.Date;

public class LeaveRequest {
	 private int id;
	    private String employeeName;
	    private String leaveType;
	    private Date startDate;
	    private Date endDate;
	    private String status;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		public String getLeaveType() {
			return leaveType;
		}
		public void setLeaveType(String leaveType) {
			this.leaveType = leaveType;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "LeaveRequest [id=" + id + ", employeeName=" + employeeName + ", leaveType=" + leaveType
					+ ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
		}
	    
	    
}
