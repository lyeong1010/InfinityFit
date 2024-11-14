<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head title="개인정보 수정 완료">
</head>
<body>
		
<form action="<%=request.getContextPath()%>/corporation/modify/corp.com" method="POST">
		
	<div class="center">
		<div class="title3">개인정보 확인/수정</div>
		<h3>개인정보 수정이 완료되었습니다.</h3>
	</div>	
	<div class="center" style="margin:30px">
		<input type="button" class="btn5" value="확인" onclick="location.href='<%=request.getContextPath()%>/main.com'">
		<input type="button" class="btn" value="이전" onclick="location.href='<%=request.getContextPath()%>/mypage/member/modify.com'">
	</div>
</form>		
</body>
</html>