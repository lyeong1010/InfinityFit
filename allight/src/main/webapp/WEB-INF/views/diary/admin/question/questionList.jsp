<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
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
	})
</script>
</head>
<body>
	<div class="title3">문의사항</div>
	<div  style="width:100%">
		<div class="searchDiv">
			<form id="searchForm" action="<%=request.getContextPath()%>/question/list/user/admin.com" method="GET">
				<c:if test="${param.type eq 'qtitle' || param.type eq null}">
				<select name="type" class="selectCss">
					<option value="qtitle" selected>제목</option>
					<option value="qnick">작성자</option>
				</select>
				</c:if>
				<c:if test="${param.type eq 'qid'}">
				<select name="type" class="selectCss">
					<option value="qtitle">제목</option>
					<option value="qnick" selected>작성자</option>
				</select>
				</c:if>
			<input type="text" id="search" name="search" placeholder="검색어를 입력하세요" value="${param.search}"/> 
			<input type="submit" value="검색" onclick="return checkForm();"/>
			<a href="<%=request.getContextPath()%>/question/list/user/admin.com"><input type="button" value="초기화"/></a>
	    	</form>
		</div>
		<table class="table">
			<tr>
				<th>NO</th>
				<th width="60%">제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:forEach items="${LIST}" var="list">
				<tr>
					<td>${list.qno}</td>
					<td><a href="${pageContext.request.contextPath}/question/detail/user/admin.com?no=${list.qno}&nowPage=${PINFO.nowPage}">${list.qtitle}</a></td>
					<td>${list.qnick}</td>
					<td>${list.qdate}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty LIST}">
			<tr>
				<td colspan="4" style="text-align: center;">문의사항이 없습니다.</td>
			</tr>
			</c:if>
		</table>

		<div class="center">
			<ul class="pagination" id="Page">
			<!-- 검색전 -->
			<c:if test="${empty param.type}">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/question/list/user/admin.com?nowPage=${PINFO.nowPage-3}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/question/list/user/admin.com?nowPage=1">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li"><!-- 스크립트 적용해야 할것같아요 -->
						<a href="${pageContext.request.contextPath}/question/list/user/admin.com?nowPage=${i}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/list/user/admin.com?nowPage=${PINFO.nowPage+3}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/list/user/admin.com?nowPage=${PINFO.endPage}">»</a>
					</c:if>
				</li>
			</c:if>
			<!-- 검색후 -->
			<c:if test="${!empty param.type}">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/question/search/user/admin.com?type=${param.type}&word=${param.word}&nowPage=${PINFO.nowPage-3}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/question/search/user/admin.com?type=${param.type}&word=${param.word}&nowPage=1">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li">
						<a href="${pageContext.request.contextPath}/question/search/user/admin.com?type=${param.type}&word=${param.word}&nowPage=${i}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/search/user/admin.com?type=${param.type}&word=${param.word}&nowPage=${PINFO.nowPage+3}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/search/user/admin.com?type=${param.type}&word=${param.word}&nowPage=${PINFO.endPage}">»</a>
					</c:if>
				</li>
			</c:if>
			</ul>
		</div>
	</div>
</body>
</html>