<%-- 
    Document   : resetNewPassword
    Created on : 17-Mar-2020, 4:45:22 PM
    Author     : 794458
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel='stylesheet' type='text/css' href='js/style.css'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form method="post" action="reset">
            <input type="text" required name="newPassword"><br>
            <input type="submit" value="Submit">
            <input type="hidden" name="action" value="np">
        </form>
    </body>
</html>
