<%-- 
    Document   : grade.jsp
    Created on : Jul 16, 2024, 5:44:28 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Grades</title>
</head>
<body>
   <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
        }
    </style>
</head>
<body>
    <h2>Student Grades</h2>
    <form action="grade" method="POST">
    <table>
        <thead>
            <tr>
                <th>Exam</th>
                <th>Date</th>
                <th>Weight</th>
                <th>Score</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="grade" items="${grades}">
                <tr>
                    <td>${grade.exam.assessment.name}</td>
                    <td>${grade.exam.from}</td>
                    <td>${grade.exam.assessment.weight}</td>
                    <td>${grade.score}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
        </form>
</body>
</html>