<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% Date now = new Date(); 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(function(){
		//확인 버튼 클릭 시
		$("#ok").click(function() {
			if($("#qtitle").val() ==""){
				alert("제목을 입력하세요");
				return false;
			}
			if($("#qcontent").val() ==""){
				alert("내용을 입력하세요");		
				return false;
			}
			$("#form").submit();
		});
		//목록 버튼 클릭 시
		$("#list").click(function(){
			$(location).attr("href","${pageContext.request.contextPath}/mypage/question/list.com")
		});
	})
</script>
</head>
<body>
<form id="form" action="${pageContext.request.contextPath}/mypage/question/write.com" method="post">
	<div style="width:100%">
		<div class="boardContent">
			<div class="boardContent-buttons">
				<input type="button" value="목록" class="btn" id="list">
				<input type="button" value="확인" class="btn" id="ok">
			</div>
			<table>
				<tr>
					<td class="board-title">제목</td>
					<td class="board-title" colspan="3">
						<input type="search" name="qtitle" id="qtitle" size="60%" required="required" placeholder="제목을 입력하세요">
						</td>
				</tr>
				<tr>
					<td class="board-info"><a class="board-info-nick">작성자</a></td>
					<td class="board-info"><a class="board-info-nick">${sessionScope.MNICK}</a></td>
					<td class="board-info"><a class="board-info-others">작성일 </a></td>
					<td class="board-info"><a class="board-info-others"><%=sf.format(now) %></a></td>
				</tr>
				<tr>
					<td class="board-content" colspan="4">
						<div class="board-content-div">내용 <br/><br/>
							<textarea rows="12" cols="100" id="qcontent" name="qcontent" required="required" placeholder="내용을 입력하세요"></textarea></div></td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>