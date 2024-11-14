<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
		
<form action="<%=request.getContextPath()%>/corporation/modify/corp.com"
		method="POST">
		
	<div class="center">
		<div class="title3">회원탈퇴</div>
		<h3>회원탈퇴되었습니다.<br/>Allight를 이용해주셔서 감사합니다.</h3>
	</div>	
	<div class="center" style="margin:30px">
		<input class="btn" type="button" value="홈으로" onclick="location.href='<%=request.getContextPath()%>/main.com'">
	</div>
</form>	
</body>
</html>