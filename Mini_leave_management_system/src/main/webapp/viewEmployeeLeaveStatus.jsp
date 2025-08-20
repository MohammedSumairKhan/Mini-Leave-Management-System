<%@ page import="java.sql.*" %>
<%@ page import="com.connectionfactory.DBConnection" %>
<%@ page session="true" %>

<%
    // Session check
    Integer employeeId = (Integer) session.getAttribute("employeeId");
    if (employeeId == null) {
        out.println("Please login first.");
        return;
    }

    try (Connection con = DBConnection.getConnection()) {
        String sql = "SELECT leave_type, start_date, end_date, reason, status FROM leave_requests WHERE employee_id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, employeeId);
        ResultSet rs = pst.executeQuery();

        // Check if there are any rows
        if (!rs.isBeforeFirst()) {
%>
            <p>No leave requests found.</p>
<%
        } else {
%>
            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Leave Type</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Reason</th>
                    <th>Status</th>
                </tr>
<%
            while (rs.next()) {
%>
                <tr>
                    <td><%= rs.getString("leave_type") %></td>
                    <td><%= rs.getDate("start_date") %></td>
                    <td><%= rs.getDate("end_date") %></td>
                    <td><%= rs.getString("reason") %></td>
                    <td><%= rs.getString("status") %></td>
                </tr>
<%
            } // while
%>
            </table>
<%
        } // else
        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Error fetching leave requests.");
    }
%>
