package com.leavereport;

public class LeaveReport {
		private int employeeId;
	    private String employeeName;
	    private int leavesTaken;
	    private int leavesRemaining;
		public int getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(int employeeId) {
			this.employeeId = employeeId;
		}
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		public int getLeavesTaken() {
			return leavesTaken;
		}
		public void setLeavesTaken(int leavesTaken) {
			this.leavesTaken = leavesTaken;
		}
		public int getLeavesRemaining() {
			return leavesRemaining;
		}
		public void setLeavesRemaining(int leavesRemaining) {
			this.leavesRemaining = leavesRemaining;
		}
		@Override
		public String toString() {
			return "LeaveReport [employeeId=" + employeeId + ", employeeName=" + employeeName + ", leavesTaken="
					+ leavesTaken + ", leavesRemaining=" + leavesRemaining + "]";
		}
	    
	    

}
