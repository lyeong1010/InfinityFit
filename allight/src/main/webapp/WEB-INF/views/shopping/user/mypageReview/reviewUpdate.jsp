<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(function(){
		//확인 버튼 클릭 시
		$("#ok").click(function(){
			if($("#rcontent").val() ==""){
				alert("내용을 입력하세요");		
				return false;
			}
			$("#form").submit();
		});
		//목록 버튼 클릭 시
		$("#list").click(function(){
			$(location).attr("href","${pageContext.request.contextPath}/mypage/review/list.com")
		});
	})
</script>
</head>
<body>
<form id="form" action="${pageContext.request.contextPath}/mypage/review/update.com?nowPage=${param.nowPage}&no=${param.no}" method="post">
	<div style="width:100%">
		<div class="boardContent">
			<div class="boardContent-buttons">
				<input type="button" value="목록" class="btn" id="list">
				<input type="button" value="확인" class="btn" id="ok">
			</div>
			<table>
				<tr>
					<td class="board-title" >별점</td>
					<td class="board-title" colspan="3">
					<select name="rgrade">
						<option value="5">★★★★★</option>
						<option value="4">★★★★☆</option>
						<option value="3">★★★☆☆</option>
						<option value="2">★★☆☆☆</option>
						<option value="1">★☆☆☆☆</option>
					</select>
				</td>
				</tr>
				<tr>
					<td class="board-info"><a class="board-info-nick">작성자</a></td>
					<td class="board-info"><a class="board-info-nick" >${sessionScope.MID}
					<input type="hidden" name="rid" value="${sessionScope.MID}"></a></td>
					<td class="board-info"><a class="board-info-others">닉네임</a></td>
					<td class="board-info"><a class="board-info-others" >${sessionScope.MNICK}
					<input type="hidden" name="rnick" value="${sessionScope.MNICK}"></a></td>
				</tr>
				<tr>
					<td class="board-content" colspan="4">
						<div class="board-content-div">내용 <br/><br/>
							<textarea rows="12" cols="100" id="rcontent" name="rcontent" placeholder="내용을 입력하세요">${LIST.rcontent }</textarea></div></td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>