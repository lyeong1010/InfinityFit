<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	$("#writeForm").submit(function() {
		if($("#ntitle").val() ==""){
			alert("제목을 입력하세요");		
			return false;
		}
		if($("#ncontent").val() ==""){
			alert("내용을 입력하세요");		
			return false;
		}
	})
})
</script>
</head>
<body>
	<form action="<%=request.getContextPath()%>/notice/write/admin.com" id="writeForm" method="POST">
		<table>
			<tr>
				<td>아이디 :</td>
				<td>${sessionScope.MID}<input type="hidden" id="nid" name="nid" value="${sessionScope.MID}" /></td>
			</tr>
			<tr>
				<td>닉네임 :</td>
				<td>${sessionScope.MNICK}<input type="hidden" id="nnick" 	name="nnick" value="${sessionScope.MNICK}" /></td>
			</tr>
			<tr>
				<td>제목 :</td>
				<td><input type="text" id="ntitle" name="ntitle" placeholder="제목을 입력하세요"/></td>
			</tr>
			<tr>
				<td>내용 :</td>
				<td><textarea rows="12" cols="100" name="ncontent" id="ncontent" placeholder="내용을 입력하세요"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center">
				<input type="submit"	id="wFrm" value="완료"> 
				<a href="<%=request.getContextPath()%>/admin.com"><input type="button" value="취소"></a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>