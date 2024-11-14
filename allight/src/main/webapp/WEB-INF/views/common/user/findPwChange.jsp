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
	<script src="${pageContext.request.contextPath}/resources/js/findPwChange.js"></script>
	<script type="text/javascript"></script>
</head>
<body>
	<h2>비밀번호 찾기</h2>
	<hr />
	<form action="./findPwChange.com" id="findPwChange" method="post">
		<input type="hidden" id="mid" name=mid value="${param.mid}">
		<table>
			<h4>비밀번호를 변경해 주세요</h4>
			<hr/>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" id="mpw" name="mpw" value="${param.mpw}"></td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td><input type="password" id="mpw2" name="mpw2" value="${param.mpw2}"></td>
			</tr>
			<tr>
				<td>${fail}</td>
			</tr>
			<tr>
				<td class="center">
					<input type="reset" value="취소" onclick="location.href='${pageContext.request.contextPath}/login.com'"></button>&nbsp;
					<input id="next" type="submit" value="다음" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>