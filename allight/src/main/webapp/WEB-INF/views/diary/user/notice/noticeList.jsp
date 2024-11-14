<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<% response.setContentType("text/html"); %>
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
	<div class="title3">공지사항</div>
	<div style="width:100%">
	<form id="sFrm" method="get"
		action="<%=request.getContextPath()%>/notice.com">
		<div class="searchDiv">
		
			<select name="type" class="selectCss">
				<c:if test="${param.type=='nall' || param.type eq null}">
					<option value="nall" selected>제목+내용</option>
					<option value="ntitle">제목</option>
					<option value="ncontent">내용</option>
				</c:if>
				<c:if test="${param.type=='ntitle'}">
					<option value="nall" >제목+내용</option>
					<option value="ntitle" selected>제목</option>
					<option value="ncontent">내용</option>
				</c:if>
				<c:if test="${param.type=='ncontent'}">
					<option value="nall" >제목+내용</option>
					<option value="ntitle">제목</option>
					<option value="ncontent" selected>내용</option>
				</c:if>
			</select>
			<input type="text" id="search" name="search" placeholder="검색어를 입력하세요"/> 
			<input type="submit" value="검색" onclick="return checkForm();"/>
			<a href="<%=request.getContextPath()%>/notice.com"><input type="button" value="초기화"/></a>
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
			<c:forEach items="${LIST}" var="notice">
				<tr class="center">
					<td class="center">${notice.NNO}</td>
					<td class="center">
					<c:if test="${notice.NID eq 'admin'}">
						<img src="${pageContext.request.contextPath}/resources/img/crown.png" style="width: 20px; height: 30px;"/></c:if>
					${notice.NNICK}</td>
					<td class="center">
					<a href="${pageContext.request.contextPath}/notice/detail.com?type=${param.type}&search=${param.search}&nowPage=${PINFO.nowPage}&nno=${notice.NNO}">
							${notice.NTITLE}</a></td>
					<td class="center">
						<fmt:formatDate value="${notice.NDATE}" pattern="yyyy-MM-dd HH시 mm분"/></td>
					<td class="center">${notice.NHIT}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty LIST}">
			<tr><td colspan="5" class="center">공지사항이 없습니다.</td></tr>
		</c:if>
		</table>
		
		<c:if test = "${sessionScope.MID == 'admin'}">
			<div class="right">
				<a href="<%=request.getContextPath() %>/notice/write/admin.com" class="btn">글쓰기</a>
			</div>
		</c:if>

		<div class="center">
			<ul class="pagination" id="Page">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/notice.com?type=${param.type}&search=${param.search}&nowPage=${PINFO.nowPage-3}">«</a>
					</c:if> 
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/notice.com?type=${param.type}&search=${param.search}&nowPage=1">«</a>
					</c:if>
				</li>
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li">
					 	<a href="${pageContext.request.contextPath}/notice.com?type=${param.type}&search=${param.search}&nowPage=${i}">${i}</a>
					</li>
				</c:forEach>
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/notice.com?type=${param.type}&search=${param.search}&nowPage=${PINFO.nowPage+3}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/notice.com?type=${param.type}&search=${param.search}&nowPage=${PINFO.endPage}">»</a>
					</c:if>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>