<%@ page import="java.util.List" %>
<%@ page import="com.leaverequest.LeaveRequest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<LeaveRequest> leaveList = (List<LeaveRequest>) request.getAttribute("leaveRequests");
%>

<table border="1" cellspacing="0" cellpadding="5">
    <tr>
        <th>Employee</th>
        <th>Leave Type</th>
        <th>From</th>
        <th>To</th>
        <th>Actions</th>
    </tr>
    <%
        if (leaveList != null && !leaveList.isEmpty()) {
            for (LeaveRequest lr : leaveList) {
    %>
    <tr>
        <td><%= lr.getEmployeeName() %></td>
        <td><%= lr.getLeaveType() %></td>
        <td><%= lr.getStartDate() %></td>
        <td><%= lr.getEndDate() %></td>
        <td>
           <a href="LeaveActionServlet?id=<%= lr.getId() %>&action=APPROVED">Approve</a> |
		   <a href="LeaveActionServlet?id=<%= lr.getId() %>&action=REJECTED">Reject</a>
           
        </td>
    </tr>
    <%
            }
        } else {
    %>
    <tr>
        <td colspan="5" style="text-align:center;">No leave requests found.</td>
    </tr>
    <%
        }
    %>
</table>
