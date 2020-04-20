<%-- 
    Document   : registration
    Created on : 1-Apr-2020, 12:43:35 PM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register New User</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="js/register.js"></script>
        <link rel='stylesheet' type='text/css' href='js/style.css'/>
    </head>
    
    <body>
        <a href="login">Go back</a>
        <p class="red">${error}</p>
        <h1>Register New User</h1>
        <form action="register" method="post">
            <table>
            
                <tr>
                    <td>
                        Username:   
                    </td>
                    <td>
                        <input type="text" required name="username" id="username" value="${usernameReg}">
                    </td>
                    <td>
                        Status: <span class="red" id="username_message">...</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Password: 
                    </td>
                    <td>
                        <input type="password" required name="password">
                    </td>
                    
                </tr>
                <tr>
                    <td>
                        Confirm password: 
                    </td>
                    <td>
                        <input type="password" name="passwordcheck">
                    </td>
                </tr>
                <tr>
                    <td>
                        Firstname: 
                    </td>
                    <td>
                        <input type="text" required name="fname" value="${fnameReg}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Lastname: 
                    </td>
                    <td>
                        <input type="text" required name="lname" value="${lnameReg}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Email: 
                    </td>
                    <td>
                        <input type="email" required name="email" value="${emailReg}">
                    </td>
                </tr>
            
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Register">
                    </td>
                </tr>
            </table>
        </form>
       
       
    </body>
</html>

