<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminHome</title>
</head>
<body>
	<h1>관리자페이지</h1>
	<%-- 홈 : <%=request.getContextPath() %>${path} --%>
	<%-- <a href="<%=request.getContextPath() %>${path}">링크</a> --%>
	<h3><a href="./member/admin.com">회원 상세</a></h3>
	<h3><a href="./corporation/admin.com">기업 상세</a></h3>
	<h3><a href="<%=request.getContextPath() %>/corporation/join/admin.com">기업 가입</a></h3>
	<h3><a href="./notice/write/admin.com">공지사항 글쓰기</a></h3>
	
</body>
</html>