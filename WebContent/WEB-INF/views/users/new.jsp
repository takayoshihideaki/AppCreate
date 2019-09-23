<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>ユーザー新規登録ページ</h2>

        <form method="POST" action="<c:url value='/users/create' />">
            <c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>


<label for="name">氏名</label><br />
<input type="text" name="name" value="${user.name}" />
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />



<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>
        </form>

        <p><a href="${pageContext.request.contextPath}/index">コメント一覧へ</a></p>
    </c:param>
</c:import>