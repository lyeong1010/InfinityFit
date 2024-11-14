<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head title="탈퇴 화면">
</head>
<body>
<form action="<%=request.getContextPath()%>/corporation/modify/corp.com"
		method="POST">
		
	<div class="center">
		<div class="title3">회원탈퇴</div>
		<h3>비밀번호 입력이 잘못되었습니다.<br/>다시 시도해주세요.</h3>
	</div>	
	<div class="center" style="margin:30px">
		<input class="btn" type="button" value="이전" onclick="location.href='<%=request.getContextPath()%>/mypage/member/delete.com'">
	</div>
</form>	
</body>
</html>