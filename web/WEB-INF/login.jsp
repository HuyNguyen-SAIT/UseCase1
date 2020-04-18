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
        <link rel='stylesheet' type='text/css' href='js/style.css'/>
        <title>Login</title>
    </head>
    <body>
        <p class="red">${errorMessage}</p>
        <h1>Home Inventory</h1>
        <h2>Login</h2>
        <form action="login" method="POST">
        <table>
            <tbody>
            <tr>
                <td>
                    Username: 
                </td>
                <td>
                    <input type="text" name="usernameLogin" value="${usernameAgain}" title="Your username"/>
                </td>
            </tr>
            
            <tr>
                <td>
                    Password: 
                </td>
                <td>
                    <input type="password" name="passwordLogin" title="Your password"/>
                </td>
            </tr>
            
            <tr>
                <td colspan="2">
                    <input type="submit" value="Login">
                </td>
            </tr>
            </tbody>
        </table>
        </form>
                
                <br/><a href="register">New user? Register now!</a>
    </body>
</html>
