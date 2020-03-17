<%-- 
    Document   : login
    Created on : 13-Mar-2020, 10:41:41 AM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h2>Login</h2>
        <form action="login" method="POST">
        <table>
            <tbody>
            <tr>
                <td>
                    Username: <input type="text" name="usernameLogin" value="${usernameAgain}" title="Your username"/>
                </td>
            </tr>
            
            <tr>
                <td>
                    Password: <input type="password" name="passwordLogin" title="Your password"/>
                </td>
            </tr>
            
            <tr>
                <td>
                    <input type="submit" value="Login">
                </td>
            </tr>
            </tbody>
        </table>
        </form>
        <font color="red" size="4">
        <p color="red">${errorMessage}</p>
        </font>
        
    </body>
</html>
