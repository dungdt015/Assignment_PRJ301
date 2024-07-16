<%-- 
    Document   : grade_detail.jsp
    Created on : Jul 16, 2024, 11:17:21 PM
    Author     : admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Grade Details</title>
    <link href="${pageContext.request.contextPath}/file/text/grade_detail.css" rel="stylesheet" type="text/css"/>
    <style>
        .passed {
            color: #4CAF50;
            font-weight: bold;
        }
        .not-passed {
            color: #FF0000;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>Grade Details</h1>
    <table>
        <thead>
            <tr>
                <th>Assessment Name</th>
                <th>Score</th>
                <th>Weight</th>
                <th>Weighted Score</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="totalWeightedScore" value="0" />
            <c:forEach var="grade" items="${grades}">
                <tr>
                    <td><c:out value="${grade.exam.assessment.name}" /></td>
                    <td><c:out value="${grade.score}" /></td>
                    <td><c:out value="${grade.exam.assessment.weight}" /></td>
                    <td><c:out value="${grade.weightedScore}" /></td>
                </tr>
                <c:set var="totalWeightedScore" value="${totalWeightedScore + grade.weightedScore}" />
            </c:forEach>
        </tbody>
    </table>
    
    <div style="margin-top: 20px; text-align: center;">
        <h2>Total Weighted Score: 
            <fmt:formatNumber value="${totalWeightedScore}" maxFractionDigits="2" />
        </h2>
        <c:choose>
            <c:when test="${totalWeightedScore >= 5}">
                <p class="passed">PASSED</p>
            </c:when>
            <c:otherwise>
                <p class="not-passed">NOT PASSED</p>
            </c:otherwise>
        </c:choose>
    </div>

    <a href="javascript:history.back()" class="back-btn">Quay lại</a>
</body>
</html>