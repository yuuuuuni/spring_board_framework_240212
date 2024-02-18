<%@ page contentType="text/html;charset=UTF-8" language="java" %> <!-- HTML 써야할 때 해당 속성 기입 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%-- JSP의 반복문 써야할 때 해당 속성 기입 --%>

<html>
<head>
    <title>list</title>
</head>
<body>
    <table>
        <tr>
            <th>id</th>
            <th>title</th>
            <th>writer</th>
            <th>date</th>
            <th>hits</th>
        </tr>
        <c:forEach items="${boardList}" var="board">
            <tr>
                <td>${board.id}</td>
                <td>
                    <a href="/board?id=${board.id}">${board.boardTitle}</a>
                </td>
                <td>${board.boardWriter}</td>
                <td>${board.boardCreatedTime}</td>
                <td>${board.boardHits}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>