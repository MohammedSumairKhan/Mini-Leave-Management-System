<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
   <link rel="stylesheet" type="text/css" href="css/dashboard.css">
</head>
<body>
    <div class="sidebar">
        <div>
            <h2>Admin Panel</h2>
            <a href="#addEmployee">Add Employee</a>
            <a href="ViewEmployeesServlet">View Employees</a>
            <a href="ViewLeaveRequestsServlet">Approve / Reject Leaves</a>
            <a href="LeaveReportServlet">Leave Reports</a>
            <a href="deleteEmployee.html">Delete Employee</a>
        </div>
       <a href="adminlogin.html" class="logout-icon" title="Logout">&#x1F511; Logout</a>
    </div>

    <div class="main">
        <!-- Add Employee -->
        <div class="card" id="addEmployee">
            <h3>Add Employee</h3>
            <form action="AddEmployeeServlet" method="post">
                <input type="text" name="name" placeholder="Employee Name">
                <input type="email" name="email" placeholder="Employee Email">
                <input type="text" name="department" placeholder="Department">
                <input type="date" name="joiningDate">
                <button type="submit">Add Employee</button>
            </form>
        </div>

        <!-- View Employees -->
        <div class="card" id="viewEmployees">
            <h3>View Employees</h3>
            
                <jsp:include page="viewemployee.jsp" />
           
        </div>

        <!-- Approve / Reject Leave Requests -->
        <div class="card" id="leaveRequests">
            <h3>Leave Requests</h3>
            
        		 <jsp:include page="leaveRequest.jsp" />
         
        </div>

        <!-- Leave Reports -->
        <div class="card" id="leaveReports">
            <h3>Leave Reports</h3>
            
            	<jsp:include page="leaveReports.jsp" />
            
        </div>
    </div>
</body>
</html>
