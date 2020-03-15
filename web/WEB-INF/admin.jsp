<%-- 
    Document   : admin
    Created on : 13-Mar-2020, 10:42:37 AM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrator</title>
    </head>
    <body>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <h1>Manage Users</h1>
        
        <c:forEach var="user" items="${userList}" >
        <table>
            <tr>
                <th>
                    Username
                </th>
                <th>
                    First Name
                </th>
                <th>
                    Last Name
                </th>
                <th>
                    Delete
                </th>
                <th>
                    Edit
                </th>
             
            </tr>
            <tr>
                <td> 
                    ${user.username}
                </td>
                
                <td>
                    ${user.firstName}
                </td>
                
                <td>
                    ${user.lastName}
                </td>
                
                <td>
                    <form action="admin" method="POST">
                   <input type="submit" value="Delete" >
                    
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="selectedUser" value="${user.username}">  
                    </form>
                </td>
                
                <td>
                    <form action="admin" method="POST">
                    <input type="submit" value="Edit">
                    <input type="hidden" name="action" value="view">
                    <input type="hidden" name="selectedUser" value="${user.username}">  
                    </form>
                </td>
            </tr>
        </table>
        </c:forEach>
        
        
        <h2>${addorsave} User</h2>
            <form action="admin" method="POST">
                Username: <input type="text" name="username" value="${selectedUser.username}" ${readonly} id="username"><br>
                Password: <input type="password" name="password" value="${selectedUser.password}" id="password"><br>
                First name: <input type="text" name="firstname" value="${selectedUser.firstName}" id="firstname"><br>
                Last name: <input type="text" name="lastname" value="${selectedUser.lastName}" id="lastname"><br>
                Email: <input type="email" name="email" value="${selectedUser.email}" id="email"><br>
                <input type="hidden" name="action" value="${addorsave}">
                <input type="submit" value="${addorsave}">
            </form>
            ${message}
    </body>
</html>
