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
    </head>
    <body>
        <form action="take" method="POST">
            <table border="1px">
                <tr>
                    <th></th>
                    <c:forEach items="${requestScope.exams}" var="e">
                        <th>
                            ${e.assessment.name}
                            ${e.from} - ${e.assessment.weight}
                        </th>
                    </c:forEach>
                </tr>
                <c:forEach items="${requestScope.students}" var="s">
                 <tr>
                    <td>${s.name}</td>
                    <c:forEach items="${requestScope.exams}" var="e">
                        <td>
                            <input type="hidden" name="gradeid" value="${s.id}_${e.id}" />
                            <input type="text" name="grade${s.id}_${e.id}" 
                                   
                                   <c:forEach items="${requestScope.grades}" var="g">
                                       <c:if test="${g.exam.id eq e.id and g.student.id eq s.id}">
                                       value="${g.score}"
                                       </c:if>
                                   </c:forEach>
                                   />
                        </td>
                    </c:forEach>
                </tr>   
                </c:forEach>
            </table>
            <input type="hidden" name="cid" value="${param.cid}"/>
            <input type="submit" value="save" />
        </form>
    </body>
</html>
