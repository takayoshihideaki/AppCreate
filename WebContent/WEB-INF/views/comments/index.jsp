<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}"><!--　フラッシュがある場合出力  -->
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>

        </c:if>
         <c:if test="${iineflush != null}"><!--　フラッシュがある場合出力  -->
            <div id="flush_iine">
                <c:out value="${iineflush}"></c:out>
            </div>

        </c:if>
        <c:if test="${iineflush2 != null}"><!--　フラッシュがある場合出力  -->
            <div id="flush_iine_delete">
                <c:out value="${iineflush2}"></c:out>
            </div>

        </c:if>
        <h2>コメント一覧</h2>
        <div>
            <ul>
                <c:set var="stm" value="0" /><!--　変数stmを宣言し0を代入  -->
                <c:forEach var="comment" items="${comments}"><!-- コメントリストを一件づつ出力  -->
                    <li class="box2"><c:choose>
                            <c:when test="${sessionScope.login_user.id == comment.user.id}"><!-- ログインユーザーが作成したコメントであるか確認 -->
                                <a
                                    href="${pageContext.request.contextPath}/show?id=${comment.id}">
                                    <c:out value="${comment.id}" /> (自分)
                                </a>
                                <c:out value="${item}" />
                            </c:when>
                            <c:otherwise><!-- 条件に該当しない場合 -->
                                <a><c:out value="${comment.id}" /></a>
                            </c:otherwise>
                        </c:choose> ：【題名】<c:out value="${comment.title}">
                        </c:out>&emsp;&emsp;<fmt:formatDate value="${comment.updated_at}"
                            pattern="yyyy-MM-dd HH:mm:ss" />&emsp; &emsp; <c:choose><%--　コメントを更新日時を出力  --%>
                            <c:when test="${iineCountList2[stm] > 0}"><!--　iineCountList2[stm]が0より多いか確認 -->

                                <button type="button" class="button3">
                                    いいね!
                                    <c:out value="${iineCountList[stm]}" />
                                </button>
                                <a
                                    href="<c:url value='/IineDelete?comment_id=${comment.id}&user_id=${sessionScope.login_user.id}' />"><button
                                        type="button" class="button2">いいね! を取り消す</button></a><!-- コメントid,ログインユーザーidを/IineDeleteにリクエストで渡す -->
                                <br />
                                <br />
                                <br />
                                <c:out value="${comment.content}" /><!-- コメント内容を出力 -->
                            </c:when>
                            <c:otherwise>
                                <a
                                    href="<c:url value='/IineCreate?comment_id=${comment.id}&user_id=${sessionScope.login_user.id}' />">
                                    <button type="button" class="button3">
                                        いいね!
                                        <c:out value="${iineCountList[stm]}" />
                                    </button><!-- コメントid,ログインユーザーidを/IineCreateにリクエストで渡す -->
                                </a>
                                <br />
                                <br />
                                <br />
                                <c:out value="${comment.content}" /><!-- コメント内容を出力 -->

                            </c:otherwise>
                        </c:choose></li>

                    <c:set var="stm" value="${stm + 1}" /><!-- 次のリストを出力するため、変数stm　+ 1  -->
                </c:forEach>
            </ul>
        </div>
        <div id="pagination">
            （全 ${comments_count} 件）<br /><!-- 全コメント件数を出力 -->
            <c:forEach var="i" begin="1" end="${((comments_count - 1) / 15) + 1}"
                step="1">
                <c:choose>
                    <c:when test="${i == page}"><!-- 表示ページ数と変数iが同じ場合 -->
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/index?page=${i}"><c:out
                                value="${i}" /></a>&nbsp;<!--　/indexへ選択した変数iを渡してページ移動 -->
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="${pageContext.request.contextPath}/new">新規コメントの投稿</a><!-- 新規コメント投稿ページへ移動 -->
        </p>
    </c:param>
</c:import>