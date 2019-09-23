<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>コメントアプリ</title>
<link rel="stylesheet" href="<c:url value='/css/reset.css' />">
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<link rel="stylesheet" href="<c:url value='/css/style2.css' />">
</head>

<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1><span>31ちゃんねる</span>
                <c:choose>
                <c:when test="${sessionScope.login_user != null}">
                    <c:out value="${sessionScope.login_user.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                         <a class="log" href="<c:url value='/logout' />">ログアウト</a>
                </c:when>
                <c:otherwise>
                    <a class="log" href="${pageContext.request.contextPath}/login">ログイン</a>
                </c:otherwise>
            </c:choose>
                </h1>
                &nbsp;&nbsp;&nbsp;
            </div>
        </div>
        <div id="content">${param.content}</div>
        <div id="footer">Comment Application by Hideaki Takayoshi.</div>
    </div>
</body>
</html>
