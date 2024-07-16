<%-- 
    Document   : lecturer
    Created on : 29 Jun 2024, 20:08:38
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exams</title>

        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .center-form {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .form-container {
                background-color: #f8f9fa;
                padding: 40px; /* Increased padding */
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 100%; /* Adjusted width */
                max-width: 900px; /* Set a maximum width */
                font-size: 1.5rem; /* Increased font size by 2px */
            }
            .form-control {
                font-size: 1.5rem; /* Increased font size by 2px */
            }
            .btn {
                font-size: 1.5rem; /* Increased font size by 2px */
            }
            .btn-success {
                background-color: #007bff;
                color: white;
                width: 150px; /* Increased button width */
                margin-top: 20px; /* Added margin to move it down */
            }
        </style>
    </head>
    <body>

        <div class="container center-form">
            <div class="form-container">
                <c:if test="${requestScope.exams eq null}">
                    <form action="lecturer" method="POST"> 
                        <input type="hidden" name="lid" value="${param.lid}"/>
                        <div class="form-group">
                            <label for="courseSelect">Course</label>
                            <select class="form-control" id="courseSelect" name="cid">
                                <c:forEach items="${requestScope.courses}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">View</button>
                    </form>
                </c:if>
                <c:if test="${requestScope.exams ne null}">
                    <form action="take" method="GET">
                        <input type="hidden" name="cid" value="${param.cid}"/>
                        <c:forEach items="${requestScope.exams}" var="e">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="eid" value="${e.id}" id="exam${e.id}">
                                <label class="form-check-label" for="exam${e.id}">
                                    ${e.assessment.name}-(${e.from}:${e.assessment.weight}%)
                                </label>
                            </div>
                        </c:forEach>
                        <button type="submit" class="btn btn-success">Take</button>
                    </form>
                </c:if>
            </div>
        </div>
        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

