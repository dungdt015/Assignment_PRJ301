<%-- 
    Document   : take
    Created on : 29 Jun 2024, 20:27:08
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .center-table {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .table-container {
                background-color: #f8f9fa;
                padding: 20px;
                padding-bottom: 80px; /* Add padding to bottom */
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 1200px;
                font-size: 1rem;
                position: relative;
            }
            table {
                width: 100%;
                table-layout: fixed;
            }
            th, td {
                text-align: center;
                vertical-align: middle;
                border: 1px solid #007bff;
                padding: 10px;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            .btn-save {
                background-color: #007bff;
                color: white;
                position: absolute;
                right: 20px;
                bottom: 20px;
            }
            h2 {
                text-align: center;
                margin-bottom: 20px;
            }
        </style>
        <script>
            function calculateScores() {
                const weightMap = {
                    'WS1': 0.05,
                    'WS2': 0.05,
                    'PT1': 0.05,
                    'PT2': 0.05,
                    'Assignment': 0.4,
                    'Final Exam': 0.2,
                    'Practical Exam': 0.2
                };
                const rows = document.querySelectorAll('tbody tr');
                rows.forEach(row => {
                    const inputs = row.querySelectorAll('input[type="text"]');
                    let total = 0;
                    inputs.forEach(input => {
                        const examName = input.name.match(/score\d+_(.+)/)[1]; // Extract the exam name
                        const weight = weightMap[examName] || 0;
                        total += (parseFloat(input.value) || 0) * weight;
                    });
                    row.querySelector('.total-score').innerText = total.toFixed(2);
                    row.querySelector('.status').innerText = total >= 5 ? 'PASS' : 'NOT PASS';
                });
            }
            document.addEventListener('DOMContentLoaded', () => {
                const inputs = document.querySelectorAll('input[type="text"]');
                inputs.forEach(input => {
                    input.addEventListener('input', calculateScores);
                });
                calculateScores(); // Initial calculation on page load
            });
        </script>
    </head>
    <body>
        <div class="container center-table">
            <div class="table-container">
                <h2>Exams</h2>
                <form action="take" method="POST">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th></th>
                                <c:forEach items="${requestScope.exams}" var="e">
                                    <th>
                                        ${e.assessment.name} (${e.assessment.weight}) <br/>
                                        ${e.from}
                                    </th>
                                </c:forEach>
                                <th>Total Score</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.students}" var="s">
                                <tr>
                                    <td>${s.name}</td>
                                    <c:forEach items="${requestScope.exams}" var="e">
                                        <td>
                                            <input type="text" class="form-control" name="score${s.id}_${e.assessment.name}"
                                                <c:forEach items="${requestScope.grades}" var="g">
                                                    <c:if test="${e.id eq g.exam.id and s.id eq g.student.id}">
                                                        value="${g.score}"
                                                    </c:if>
                                                </c:forEach>
                                            />
                                            <input type="hidden" name="gradeid" value="${s.id}_${e.id}"/>
                                        </td>
                                    </c:forEach>
                                    <td class="total-score">0.00</td>
                                    <td class="status">NOT PASS</td>
                                </tr>
                            </c:forEach>    
                        </tbody>
                    </table>
                    <input type="hidden" name="cid" value="${param.cid}" />
                    <button type="submit" class="btn btn-save">Save</button>
                </form>
            </div>
        </div>
        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>