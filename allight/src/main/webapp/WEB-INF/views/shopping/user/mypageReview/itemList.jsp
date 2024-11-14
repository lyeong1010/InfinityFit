<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function checkForm() {
	if (document.getElementById("search").value == "") {
		alert("검색어를 입력해주세요")
		return false;
	}
}
$(function(){
	$('#Page').children().each(function(){
      if($(this).children('a').text()==${PINFO.nowPage}){
         $(this).attr('class','active');
      }
    })
	//작성 버튼 클릭 시
	$("#up").click(function(){
		$(location).attr("href","${pageContext.request.contextPath}/mypage/review/write.com?no=${list.INO}");
	});
	//삭제 버튼 클릭 시
	$("#del").click(function(){
		if(confirm("삭제 하시겠습니까?")){
			$("#form").attr("action","${pageContext.request.contextPath}/mypage/review/delete.com?no=${list.INO}");
			$("#form").submit();
		}
	});
})
</script>
</head>
<body>
	<div class="title3">상품리뷰!</div>
	<div style="width:100%">
	<form id="sFrm" method="get"
		action="<%=request.getContextPath()%>/mypage/review/list.com">
		<div class="searchDiv">
			<select name="type" class="selectCss">
				<option value="iname">상품명</option>
			</select> <input type="text" id="search" name="search" placeholder="검색어를 입력하세요"/> <input type="submit"
				value="검색" onclick="return checkForm();"/>&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/mypage/review/list.com"><input type="button"
				value="검색어 초기화"/></a>
		</div>
		</form>
		<table class="table">
			<tr>
				<th>NO</th>
				<th>상품명</th>
				<th>리뷰작성일</th>
				<th>별점</th>
				<th>리뷰내용</th>
				<th></th>
			</tr>
			<c:forEach items="${LIST}" var="list">
				<tr>
					<td class="center">${list.NUM}</td>
					<td class="center">${list.INAME}</td>
					<td class="center"><fmt:formatDate value="${list.RDATE}" pattern="yyyy-MM-dd"/></td>
					<td class="center"><c:forEach begin="1" end="${list.RGRADE}">★</c:forEach><c:forEach begin="1" end="${5-list.RGRADE}">☆</c:forEach></td>
					<td class="center">${list.RCONTENT}</td>
					<td class="center">
					<a href="${pageContext.request.contextPath}/mypage/review/update.com?nowPage=${param.nowPage}&no=${list.INO}&type=${param.type}&search=${param.search}">
					<input type="button" id="wbtn" name="ubtn" value="수정" class="btn2">
					</a>
					<a href="${pageContext.request.contextPath}/mypage/review/delete.com?nowPage=${param.nowPage}&no=${list.INO}&type=${param.type}&search=${param.search}">
					<input type="button" id="dbtn" name="dbtn" value="삭제" class="btn2"></a></td>
				</tr>
			</c:forEach>
		</table>
		<div class="center">
			<ul class="pagination" id="Page">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/mypage/review/list.com?nowPage=${PINFO.nowPage-3}&type=${param.type}&search=${param.search}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/mypage/review/list.com?nowPage=1&type=${param.type}&search=${param.search}">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li"><!-- 스크립트 적용해야 할것같아요 -->
						<a href="${pageContext.request.contextPath}/mypage/review/list.com?nowPage=${i}&type=${param.type}&search=${param.search}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/mypage/review/list.com?nowPage=${PINFO.nowPage+3}&type=${param.type}&search=${param.search}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/mypage/review/list.com?nowPage=${PINFO.endPage}&type=${param.type}&search=${param.search}">»</a>
					</c:if>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>