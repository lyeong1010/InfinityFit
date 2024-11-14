<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% Date now = new Date(); 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<div style="width:100%">
		<div class="boardContent">
			<div class="boardContent-buttons">
			<a href="<%=request.getContextPath()%>/notice.com?type=${param.type}&search=${param.search}&nowPage=${param.nowPage}">
				<input type="button" value="목록" class="btn"></a>
				
				<c:if test = "${sessionScope.MTYPE eq 1}">
					<a href="<%=request.getContextPath()%>/notice/modify/admin.com?nno=${LIST.nno}">
						<input type="button" value="수정" class="btn" >
					</a>
					<a href="<%=request.getContextPath()%>/notice/delete/admin.com?nno=${LIST.nno}">
						<input type="button" value="삭제" class="btn">
						</a>
				</c:if>
				
			</div>
			
			<!-- 테이블  -->
			<table>
				<tr>
					<td class="board-title">제목 : ${LIST.ntitle}</td>
				</tr>
				<tr>
					<td class="board-info">
					<a class="board-info-nick">닉네임 : 
					<c:if test="${LIST.nid eq 'admin'}">
									<img src="${pageContext.request.contextPath}/resources/img/crown.png" style="width: 20px; height: 30px;"/></c:if>
					${LIST.nnick}</a>
						| <a class="board-info-others">조회</a> 
						<a style="color: #ff5656; font-size: 1.4rem;">${LIST.nhit }</a> 
						<a class="board-info-others"> | ${LIST.ndate }</a>
					</td>
				</tr>
				<tr>
					<td class="board-content"><div class="board-content-div"><div style="white-space:pre-line;"><c:out value="${LIST.ncontent}" /></div></div></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>