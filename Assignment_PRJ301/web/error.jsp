<%-- 
    Document   : error
    Created on : 29 Jun 2024, 20:08:46
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        An unexpected error has been occurred! <br/>
        <%= exception %>  
    </body>
</html>