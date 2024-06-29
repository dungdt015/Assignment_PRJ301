<%-- 
    Document   : login
    Created on : 29 Jun 2024, 22:20:39
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="login" method="POST">
            username <input type="text" name="username"/> </br>
            password <input type="text" name="password"/>   </br>
            <input type="submit" value="Login"/>
        </form>
    </body>
</html>
