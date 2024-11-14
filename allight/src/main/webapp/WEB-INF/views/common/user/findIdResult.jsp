<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title></title>
	<!-- jQuery CDN -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<h2>아이디 찾기 결과</h2>
	<hr />
	<table>
		<c:if test="${MEM.mid != null && !MEM.mid.isEmpty()}">
			<tr>
				<td class="center"><h4>회원님의 아이디는 다음과 같습니다.</h4></td>
			</tr>
			<tr>
				<td class="center"><h3>${MEM.mid}</h3></td>
			</tr>
		</c:if>
		<c:if test="${MEM.mid == null || MEM.mid.isEmpty()}">
			<tr>
				<td class="center">존재하지 않는 회원입니다.</td>
			</tr>
		</c:if>
		<tr>
			<td class="center">
				<button id="home" type="button" onclick="location.href='${pageContext.request.contextPath}/main.com'">&nbsp;홈 &nbsp;</button>
				<button id="login" type="button" value="login" onclick="location.href='${pageContext.request.contextPath}/login.com'">로그인</button>&nbsp;
			</td>
		</tr>
	</table>
	
</body>
</html>