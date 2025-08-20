<%@ page import="java.sql.Date" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard</title>
    <link rel="stylesheet" type="text/css" href="css/employeedashboard.css">
    
</head>
<body>
<%
    // Session check
    Integer employeeId = (Integer) session.getAttribute("employeeId");
    if (employeeId == null) {
        response.sendRedirect("employeelogin.jsp");
        return;
    }

    // Fetch session attributes
    String name = (String) session.getAttribute("employeeName");
    String email = (String) session.getAttribute("employeeEmail");
    String dept = (String) session.getAttribute("employeeDept");
    Date joining = (Date) session.getAttribute("employeeJoin");
    Integer leaveBalanceObj = (Integer) session.getAttribute("employeeLeaveBalance");
    int leaveBalance = (leaveBalanceObj != null) ? leaveBalanceObj : 0;
%>

<h2>Welcome, <%= name %>!</h2>
<p><b>Name:</b> <%= name %></p>
<p><b>Email:</b> <%= email %></p>
<p><b>Department:</b> <%= dept %></p>
<p><b>Joining Date:</b> <%= joining %></p>
<p><b>Leave Balance:</b> <%= leaveBalance %></p>

<hr>

<h3>Apply for Leave</h3>
<form action="ApplyLeaveServlet" method="post">
    <label>Leave Type:</label>
    <select name="leaveType" required>
        <option value="Casual">Casual</option>
        <option value="Sick">Sick</option>
        <option value="Other">Other</option>
    </select><br><br>
    <label>From:</label>
    <input type="date" name="fromDate" required><br><br>
    <label>To:</label>
    <input type="date" name="toDate" required><br><br>
    <label>Reason:</label>
    <textarea name="reason"></textarea><br><br>
    <button type="submit">Apply Leave</button>
</form>

<hr>

<h3>Your Leave Requests</h3>
<jsp:include page="viewEmployeeLeaveStatus.jsp" />

</body>
</html>
