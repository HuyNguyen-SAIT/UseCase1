<%-- 
    Document   : reset
    Created on : 17-Mar-2020, 4:40:24 PM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel='stylesheet' type='text/css' href='js/style.css'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>
        <a href="login">Back</a>
        <h1>Reset Password</h1>
        <p>Please enter your email address to reset your password</p>
        <form action="reset" method="POST">
            
            Email Address: <input type="text" required name="resetEmail">
            <input type="submit" value="Submit">
            <input type="hidden" name="action" value="recoveryemail">
        </form>
        <h2 class="red">${error}</h2>
    </body>
</html>
