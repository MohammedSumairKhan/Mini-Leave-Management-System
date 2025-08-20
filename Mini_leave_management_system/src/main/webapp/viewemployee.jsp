<%@ page import="java.util.List" %>
<%@ page import="com.admindashboard.Employee" %>
<!-- viewEmployees.jsp -->
<table>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
        <th>Joining Date</th>
        <th>Leave Balance</th>
    </tr>
    <%
        List<Employee> employees = (List<Employee>) request.getAttribute("employees");
        if (employees != null && !employees.isEmpty()) {
            for (Employee emp : employees) {
    %>
    <tr>
        <td><%= emp.getName() %></td>
        <td><%= emp.getEmail() %></td>
        <td><%= emp.getDepartment() %></td>
        <td><%= emp.getJoiningDate() %></td>
        <td><%= emp.getLeaveBalance() %></td>
    </tr>
    <%
            }
        } else {
    %>
    <tr>
        <td colspan="5" style="text-align:center">No employees found.</td>
    </tr>
    <%
        }
    %>
</table>

