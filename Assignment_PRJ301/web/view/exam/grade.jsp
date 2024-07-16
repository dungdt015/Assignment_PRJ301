<%-- 
    Document   : grade.jsp
    Created on : Jul 16, 2024, 9:03:17 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Grades</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/student_style.css">
    </head>
    <body>
        <div class="container">
            <h1>Grades</h1>
            <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>Exam ID</th>
                        <th>Exam Date</th>
                        <th>Duration</th>
                        <th>Assessment</th>
                        <th>Subject</th>
                        <th>Score</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${grades}" var="grade">
                        <tr>
                            <td>${grade.exam.id}</td>
                            <td>${grade.exam.date}</td>
                            <td>${grade.exam.duration} minutes</td>
                            <td>${grade.exam.assessment.name}</td>
                            <td>${grade.exam.assessment.subject.name}</td>
                            <td>${grade.score}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
            <div class="final-result">
                <h2>Final Score: ${finalScore}</h2>
                <div style="display: flex; align-items: center; justify-content: center;">
                    <h2>Status:</h2>
                    <h2 style="margin-left: 10px; color: ${status == 'Passed' ? 'green' : 'red'};">${status}</h2>
                </div>
            </div>
    </body>
</html>
