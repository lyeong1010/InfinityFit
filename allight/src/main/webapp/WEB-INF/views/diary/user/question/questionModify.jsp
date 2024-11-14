<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(function() {
		//확인 버튼 클릭 시
		$("#update").click(function() {
			if($("#qtitle").val() ==""){
				alert("제목을 입력하세요");
				return false;
			}
			if($("#qcontent").val() ==""){
				alert("내용을 입력하세요");		
				return false;
			}
		$("#form").submit();
			$("#form").attr("action","${pageContext.request.contextPath}/question/update.com?no=${DETAIL.qno}");
			$("#form").submit();
		});
		//목록 버튼 클릭 시
		$("#list").click(function() {
							$(location).attr("href","${pageContext.request.contextPath}/question/list.com")
						});
		//취소 버튼 클릭 시
		$("#back").click(function() {
							$(location).attr("href","${pageContext.request.contextPath}/question/detail.com?no=${DETAIL.qno}");
						});
	})
</script>
</head>
<body>
	<form id="form" method="post">
		<div class="container">
			<!-- 검색창  -->
			<div class="boardContent">
				<div class="boardContent-buttons">
					<input type="button" value="목록" class="btn" id="list"> <input
						type="hidden" value="${DETAIL.qno}" name="qno">
					<c:if test="${DETAIL.qid eq sessionScope.MID}">
						<input type="button" value="확인" class="btn" id="update">
					</c:if>
					<input type="button" value="취소" class="btn" id="back">
				</div>

				<!-- 테이블  -->
				<table>
					<tr>
						<td class="board-title">제목</td>
						<td class="board-title" colspan="3"><input type="search" id="qtitle" value="${DETAIL.qtitle}" name="qtitle"></td>
					</tr>
					<tr>
						<td class="board-info"><a class="board-info-nick">작성자</a></td>
						<td class="board-info"><a class="board-info-nick">${DETAIL.qid}</a></td>
						<input type="hidden" value="${DETAIL.qid}" name="qid">
						<td class="board-info"><a class="board-info-others">작성일 </a></td>
						<td class="board-info"><a class="board-info-others"><input
								type="text" disabled="disabled" value="${DETAIL.qdate}"
								name="qdate"></a></td>
					</tr>
					<tr>
						<td class="board-content" colspan="4">
							<div class="board-content-div">
								내용 <br />
								<br />
								<textarea rows="12" cols="100" name="qcontent" id="qcontent" required="required" placeholder="내용을 입력하세요">${DETAIL.qcontent}</textarea>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>