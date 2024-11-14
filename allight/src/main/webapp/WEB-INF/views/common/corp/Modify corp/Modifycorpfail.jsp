<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<form action="<%=request.getContextPath()%>/corporation/modify/corp.com" method="POST">
		
	<div class="center">
		<div class="title3">기업정보 확인/수정</div>
		<h3>기업정보 수정에 실패했습니다.<br/>잠시후 다시 시도해주세요.</h3>
	</div>	
	<div class="center" style="margin:30px">
		<input type="button" value="이전" onclick="location.href='<%=request.getContextPath()%>/corporation/modify/corp.com?cono=5'">
	</div>		
</form>
</body>
</html>