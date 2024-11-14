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
		//검색
		$("#searb").click(function(){
			var form = $("#searchF").serialize();
			  $.ajax({
		            url : "${pageContext.request.contextPath}/question/search/admin.com",
		            type : 'get', 
		            data : form, 
		            success : function(data) {
		            	//alert(form);
		            	location.href = "${pageContext.request.contextPath}/question/search/admin.com?"+form;
		            },
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		            }
		        }); 
			});
		$('#Page').children().each(function(){
	      if($(this).children('a').text()==${PINFO.nowPage}){
	         $(this).attr('class','active');
	      }
	   });
	});
</script>
</head>
<body>
	<div class="title3">기업 문의 관리</div>
	<div class="container">
		<div class="searchDiv">
			<!-- 검색전 -->
			<c:if test="${empty param.type}">
			<form id="searchF">
				<select name="type" class="selectCss">
					<option value="title">제목</option>
					<option value="id">작성자</option>
				</select>
				<div class="shopSearchDiv">
				    <input type="text" name="word" class="shopTxt" required="required"/>
				    <button type="button" id="searb" class="shopBtn">
				    	<img src="${pageContext.request.contextPath}/resources/img/search.png" class="shopSearchImg"/>
			    	</button>
		    	</div>
	    	</form>
	    	</c:if>
	    	<!-- 검색후 -->
	    	<c:if test="${!empty param.type}">
			<form id="searchF">
				<c:if test="${param.type eq 'title'}">
				<select name="type" class="selectCss">
					<option value="title" selected="selected">제목</option>
					<option value="id">작성자</option>
				</select>
				</c:if>
				<c:if test="${param.type eq 'id'}">
				<select name="type" class="selectCss">
					<option value="title">제목</option>
					<option value="id" selected="selected">작성자</option>
				</select>
				</c:if>
				<div class="shopSearchDiv">
				    <input type="text" name="word" class="shopTxt" value="${param.word}" required="required"/>
				    <button type="button" id="searb" class="shopBtn">
				    	<img src="${pageContext.request.contextPath}/resources/img/search.png" class="shopSearchImg"/>
			    	</button>
		    	</div>
	    	</form>
	    	</c:if>
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
					<td><a href="${pageContext.request.contextPath}/question/detail/admin.com?no=${list.qno}&nowPage=${PINFO.nowPage}">${list.qtitle}</a></td>
					<td>${list.qid}</td>
					<td>${list.qdate}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty LIST}">
			<tr>
				<td colspan="4" style="text-align: center;">해당 내용이 없습니다.</td>
			</tr>
			</c:if>
		</table>

		<div class="center">
			<ul class="pagination" id="Page">
			<!-- 검색전 -->
			<c:if test="${empty param.type}">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/question/list/admin.com?nowPage=${PINFO.nowPage-3}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/question/list/admin.com?nowPage=1">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li"><!-- 스크립트 적용해야 할것같아요 -->
						<a href="${pageContext.request.contextPath}/question/list/admin.com?nowPage=${i}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/list/admin.com?nowPage=${PINFO.nowPage+3}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/list/admin.com?nowPage=${PINFO.endPage}">»</a>
					</c:if>
				</li>
			</c:if>
			<!-- 검색후 -->
			<c:if test="${!empty param.type}">
				<li>
					<c:if test="${PINFO.nowPage > 3}">
						<a href="${pageContext.request.contextPath}/question/search/admin.com?type=${param.type}&word=${param.word}&nowPage=${PINFO.nowPage-3}">«</a>
					</c:if>
					<c:if test="${PINFO.nowPage <= 3}">
						<a href="${pageContext.request.contextPath}/question/search/admin.com?type=${param.type}&word=${param.word}&nowPage=1">«</a>
					</c:if>
				</li>
				<!-- 현재 페이지일때 active --> 
				<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
					<li id="li">
						<a href="${pageContext.request.contextPath}/question/search/admin.com?type=${param.type}&word=${param.word}&nowPage=${i}">${i}</a>
					</li>
				</c:forEach>				
				<li>
					<c:if test="${PINFO.nowPage < PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/search/admin.com?type=${param.type}&word=${param.word}&nowPage=${PINFO.nowPage+3}">»</a>
					</c:if>
					<c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
						<a href="${pageContext.request.contextPath}/question/search/admin.com?type=${param.type}&word=${param.word}&nowPage=${PINFO.endPage}">»</a>
					</c:if>
				</li>
			</c:if>
			</ul>
		</div>
	</div>
</body>
</html>