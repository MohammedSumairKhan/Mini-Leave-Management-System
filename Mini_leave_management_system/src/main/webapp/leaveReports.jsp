<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leavereport.LeaveReport" %>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>Employee</th>
        <th>Total Leaves Taken</th>
        <th>Remaining Leaves</th>
    </tr>

    <%
        List<LeaveReport> reports = (List<LeaveReport>) request.getAttribute("reports");
        if (reports != null && !reports.isEmpty()) {
            for (LeaveReport r : reports) {
    %>
        <tr>
            <td><%= r.getEmployeeName() %></td>
            <td><%= r.getLeavesTaken() %></td>
            <td><%= r.getLeavesRemaining() %></td>
        </tr>
    <%
            }
        } else {
    %>
        <tr>
            <td colspan="3" style="text-align:center">No data available</td>
        </tr>
    <%
        }
    %>
</table>
