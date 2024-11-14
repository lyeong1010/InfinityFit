<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<% response.setContentType("text/html"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<script>
$(function(){
   $('#Page').children().each(function(){
      if($(this).children('a').text()==${PINFO.nowPage}){
         $(this).attr('class','active');
      }
   })
})

function checkForm() {
	if (document.getElementById("search").value == "") {
		alert("검색어를 입력해주세요")
		return false;
	}
}
	</script>
</head>
<body>
	<div style="width:100%">
	<div class="title3">자유게시판</div>
	<form id="sFrm" method="get" action="<%=request.getContextPath()%>/mypage/freeboard/list.com">
		<div class="searchDiv">
		<select name="ftype" class="btn">
			<c:if test="${param.ftype=='' || param.ftype eq null}">
				<option value="" selected>선택</option>
				<option value="일반">일반</option>
				<option value="다이어리">다이어리</option>
			</c:if>
			<c:if test="${param.ftype=='일반'}">
				<option value="">선택</option>
				<option value="일반" selected>일반</option>
				<option value="다이어리">다이어리</option>
			</c:if>
			<c:if test="${param.ftype=='다이어리'}">
				<option value="">선택</option>
				<option value="일반">일반</option>
				<option value="다이어리" selected>다이어리</option>
			</c:if>
				</select>
		
			<select name="type" class="btn">
			<c:if test="${param.type=='fall'|| param.type eq null || param.type==''}">
				<option value="fall" selected>제목+내용</option>
				<option value="ftitle">제목</option>
				<option value="fcontent">내용</option>
			</c:if>
			<c:if test="${param.type=='ftitle'}">
				<option value="fall" >제목+내용</option>
				<option value="ftitle"selected>제목</option>
				<option value="fcontent">내용</option>
			</c:if>
			<c:if test="${param.type=='fcontent'}">
				<option value="fall" >제목+내용</option>
				<option value="ftitle">제목</option>
				<option value="fcontent" selected>내용</option>
			</c:if>
			</select> 
			<input type="text" class="btn" id="search" name="search" placeholder="검색어를 입력하세요" value="${param.search}"/> 
			<input type="submit" class="btn" value="검색" onclick="return checkForm();"/>
			<a href="<%=request.getContextPath()%>/mypage/freeboard/list.com">
				<input type="button" class="btn" value="검색어 초기화"/></a>
		</div>
		</form>
		<table class="table">
			<tr>
				<th>글번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach items="${LIST}" var="list">
				<tr class="center">
					<td class="center">${list.fno}</td>
					<td class="center">${list.fnick}</td>
					<td class="center"><a
						href="${pageContext.request.contextPath}/mypage/freeboard/detail.com?ftype=${param.ftype }&type=${param.type}&search=${param.search}&nowPage=${PINFO.nowPage}&no=${list.fno}">
							[${list.ftype}] ${list.ftitle}</a></td>
					<td class="center"><fmt:formatDate value="${list.fdate}" pattern="yyyy-MM-dd HH시 mm분"/></td>
					<td class="center">${list.fhit}</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${empty LIST}">
			<tr ><td rowspan="5">게시물이 없습니다.</td></tr>
		</c:if>
		<div class="right" style="margin:20px 0 0;">
			<a href="<%=request.getContextPath() %>/mypage/freeboard/write.com"><input type="button" class="btn" value="글쓰기"/></a>
		</div>

		<div class="center">
			<ul class="pagination" id="Page">
				<li><c:if test="${PINFO.nowPage > 3}">
						<a
							href="${pageContext.request.contextPath}/mypage/freeboard/list.com?ftype=${param.ftype }&type=${param.type}&search=${param.search}&nowPage=${PINFO.nowPage-3}">«</a>
					</c:if> <c:if test="${PINFO.nowPage <= 3}">
						<a
							href="${pageContext.request.contextPath}/mypage/freeboard/list.com?ftype=${param.ftype }&type=${param.type}&search=${param.search}&nowPage=1">«</a>
					</c:if></li>
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li">
						<a href="${pageContext.request.contextPath}/mypage/freeboard/list.com?ftype=${param.ftype }&type=${param.type}&search=${param.search}&nowPage=${i}">${i}</a>
					</li>
				</c:forEach>
				<li><c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a
							href="${pageContext.request.contextPath}/mypage/freeboard/list.com?ftype=${param.ftype }&type=${param.type}&search=${param.search}&nowPage=${PINFO.nowPage+3}">»</a>
					</c:if> <c:if test="${PINFO.nowPage >= PINFO.endPage-2}">
						<a
							href="${pageContext.request.contextPath}/mypage/freeboard/list.com?ftype=${param.ftype }&type=${param.type}&search=${param.search}&nowPage=${PINFO.endPage}">»</a>
					</c:if></li>
			</ul>
		</div>
	</div>
</body>
</html>