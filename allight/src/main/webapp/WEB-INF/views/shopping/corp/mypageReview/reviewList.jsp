<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(function(){
	//삭제 버튼 클릭 시
/* 	$("#del").click(function(){
		if(confirm("삭제 하시겠습니까?")){
			$("#form").attr("action","${pageContext.request.contextPath}/review/delete/corp.com?no=${list.INO}");
			$("#form").submit();
		}
	}); */
   $('#Page').children().each(function(){
      if($(this).children('a').text()==${PINFO.nowPage}){
         $(this).attr('class','active');
      }
   })
})
</script>
</head>
<body>
	<div class="title3">상품 리뷰 관리</div>
	<div style="width:100%">
		<form id="sFrm" method="get"
		action="<%=request.getContextPath()%>/review/list/corp.com">
		<div class="searchDiv">
		<input type="text" id="search" name="search" placeholder="검색할 상품명을 입력하세요"/> <input type="submit"
				value="검색" onclick="return checkForm();"/>
				<a href="${pageContext.request.contextPath}/review/list/corp.com?"><input type="button" value="검색어 초기화"/></a>
		</div>
		</form>
		<table class="table">
			<tr>
				<th>NO</th>
				<th width="30%">상품명</th>
				<th>리뷰작성일</th>
				<th>별점</th>
				<th>리뷰내용</th>
				<th></th>
			</tr>
			<c:forEach items="${LIST}" var="list">
				<tr>
					<td>${list.NUM}</td>
					<td><a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${list.INO}">${list.INAME}</a></td>
					<td>${list.RDATE}</td>
					<td>${list.RGRADE}</td>
					<td>${list.RCONTENT}</td>
					<td>
					<a href="${pageContext.request.contextPath}/review/delete/corp.com?nowPage=${param.nowPage}&no=${list.RNO}">
					<input type="button" id="dbtn" name="dbtn" value="리뷰 삭제"></a></td>
				</tr>
			</c:forEach>
		</table>
		<div class="center">
			<ul class="pagination">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/review/list/corp.com?nowPage=${PINFO.nowPage-3}&search=${param.search}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/review/list/corp.com?nowPage=1&search=${param.search}">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li"><!-- 스크립트 적용해야 할것같아요 -->
						<a href="${pageContext.request.contextPath}/review/list/corp.com?nowPage=${i}&search=${param.search}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/review/list/corp.com?nowPage=${PINFO.nowPage+3}&search=${param.search}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/review/list/corp.com?nowPage=${PINFO.endPage}&search=${param.search}">»</a>
					</c:if>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>