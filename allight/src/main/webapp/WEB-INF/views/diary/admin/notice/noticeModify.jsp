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
	<form action="<%=request.getContextPath()%>/notice/modify/admin.com?nno=${param.nno}" method="POST" id="writeForm">
		<table>
			<tr>
				<td>제목 :</td>
				<td><input type="text" id="ntitle" name="ntitle" value="${LIST.ntitle }"/></td>
			</tr>
			<tr>
				<td>내용 : </td>
				<td><textarea rows="12" cols="100" id="ncontent" name="ncontent" placeholder="내용을 입력하세요">${LIST.ncontent}</textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center">
				<input type="submit" value="완료"> 
				<a href="<%=request.getContextPath()%>/notice.com"><input type="button" value="취소"></a></td>
			</tr>
		</table>
	</form>
</body>
</html>