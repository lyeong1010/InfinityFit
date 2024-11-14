<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="searchDiv">
			<select name="search" class="selectCss">
				<option>제목</option>
				<option>내용</option>
			</select> <input type="text" name="content" /> 
			<input type="submit" value="검색" class="btn" />
		</div>
		<table class="table">
			<tr>
				<th>???</th>
				<th>???</th>
				<th>???</th>
			</tr>
			<tr>
				<td>???</td>
				<td>???</td>
				<td>???</td>
			</tr>
		</table>
		<div class="right">
			<a class="btn">글쓰기</a>
		</div>

		<div class="center">
			<ul class="pagination">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/question/list/corp.com?nowPage=${PINFO.nowPage-3}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/question/list/corp.com?nowPage=1">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li"><!-- 스크립트 적용해야 할것같아요 -->
						<a href="${pageContext.request.contextPath}/question/list/corp.com?nowPage=${i}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/list/corp.com?nowPage=${PINFO.nowPage+3}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/list/corp.com?nowPage=${PINFO.endPage}">»</a>
					</c:if>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>