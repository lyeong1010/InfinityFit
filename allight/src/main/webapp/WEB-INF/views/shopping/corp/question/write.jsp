<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(function(){
		//확인 버튼 클릭 시
		$("#ok").click(function(){
			if($('#qtitle').val()==null || $('#qtitle').val() == ""){
				alert("제목을 작성해주세요.")
				return false;
			}
			if($('#qcontent').val()==null || $('#qcontent').val() == ""){
				alert("내용을 작성해주세요.")
				return false;
			}
			$("#form").submit();
		});
		//목록 버튼 클릭 시
		$("#list").click(function(){
			$(location).attr("href","${pageContext.request.contextPath}/question/list/corp.com")
		});
	})
</script>
</head>
<body>
<form id="form" action="${pageContext.request.contextPath}/question/writepro/corp.com" method="post">
	<div class="container">
		<div class="boardContent">
			<div class="boardContent-buttons">
				<input type="button" value="목록" class="btn" id="list">
				<input type="button" value="확인" class="btn" id="ok">
			</div>
			<table>
				<tr>
					<td class="board-title">제목</td>
					<td class="board-title" colspan="3"><input type="search" name="qtitle" id="qtitle" size="60%" required="required" placeholder="제목을 입력하세요"></td>
				</tr>
				<tr>
					<td class="board-info"><a class="board-info-nick">작성자</a></td>
					<td class="board-info"><a class="board-info-nick">${sessionScope.COID}</a></td>
					<td class="board-info"><a class="board-info-others">작성일 </a></td>
					<td class="board-info"><a class="board-info-others"><f:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd"/></a></td>
				</tr>
				<tr>
					<td class="board-content" colspan="4">
						<div class="board-content-div">내용 <br/><br/>
							<textarea rows="12" cols="100" name="qcontent" id="qcontent" required="required" placeholder="내용을 입력하세요"></textarea></div></td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>