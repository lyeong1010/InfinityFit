<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑</title>

<script type="text/javascript">
$(function(){
	//정렬
	if(${SORT}==2){ //높은 가격순
		$('.sort li:nth-child(1)').addClass('sort-on');
	}else if(${SORT}==1){ //낮은 가격순
		$('.sort li:nth-child(3)').addClass('sort-on');
	}else if(${SORT}==0){ //인기순
		$('.sort li:nth-child(5)').addClass('sort-on');
	}
	
	// 현재 페이지일 경우 li에 active 클래스 적용
	$('.pagination').children().each(function(){
		if($(this).children('a').text()==${PINFO.nowPage}){
			$(this).attr('class','active');
		}
	})
})

</script>
</head>
<body>
	<div class="container">
		<div class="shopSearchDiv">
			<form id="searchFrm">
			    <input type="text" name="searchWord" class="shopTxt"/>
			    <button type="submit" class="shopBtn">
			    	<img src="${pageContext.request.contextPath}/resources/img/search.png" class="shopSearchImg"/>
		    	</button>
	    	</form>
	    </div>
	    
	    <%-- 카테고리명 --%>
		<a class="title2">
			<c:if test="${empty SEARCHWORD}">
				<c:if test="${ICATEGORY eq null or ICATEGORY eq 'all'}">전체보기</c:if>
				<c:if test="${ICATEGORY eq '식단'}">식단</c:if>
				<c:if test="${ICATEGORY eq '간식'}">간식</c:if>
				<c:if test="${ICATEGORY eq '운동복'}">운동복</c:if>
				<c:if test="${ICATEGORY eq '기타'}">기타</c:if>
			</c:if>
			<c:if test="${!empty SEARCHWORD}">&nbsp;검색결과:&nbsp;${SEARCHWORD}</c:if>
		</a>
		
		<%-- 정렬(인기순/낮은가격순/높은가격순) --%>
		<c:if test="${empty SEARCHWORD}">
			<ul class="sort">
				<li><a href="${pageContext.request.contextPath}/shopping/list.com?sort=2&icategory=${ICATEGORY}">높은가격순</a></li>
				<li>|</li>
				<li><a href="${pageContext.request.contextPath}/shopping/list.com?sort=1&icategory=${ICATEGORY}">낮은가격순</a></li>
				<li>|</li>
				<li><a href="${pageContext.request.contextPath}/shopping/list.com?sort=0&icategory=${ICATEGORY}">인기순</a></li>
			</ul>
		</c:if>
		<c:if test="${!empty SEARCHWORD}">
			<div style="height:45px;"></div>
		</c:if>
		
		<div class="row">
			<c:forEach var="list" items="${LIST}">
				<div class="col-md-3">
					<div class="product-grid">
						<div class="product-image">
							<a href="${pageContext.request.contextPath}/shopping/detail.com?ino=${list.ino}"> 
							<img class="pic" src="${list.imgimage}" onerror="this.src='${pageContext.request.contextPath}/resources/img/no-img.png'">
							</a> 
							<c:if test="${list.istock == 0}">
								<span class="product-new-label">품절</span>
							</c:if>
						</div>
						<div class="product-content">
							<div class="title">${list.iname}</div>
							<div class="price" style="color:#ef5777"><fmt:formatNumber value="${list.iprice}" pattern="#,###"/></div>
							<div class="brand">${list.icorp}</div>
						</div>
					</div>
				</div>
			</c:forEach>
			
			<%-- 검색된 내용이 없을 때 --%>
			<c:if test="${NoSearch}"><br/>검색된 내용이 없습니다.</c:if>
			
		</div>
		

		<%-- 검색 내용 없을 때만 페이징 처리!! 검색 내용 있으면 페이징 처리 안할 거... --%>
		<c:if test="${empty SEARCHWORD}">
			<div class="center">
				<ul class="pagination">
		            <li>
		               <c:if test="${PINFO.nowPage > 3}">
		                  <a href="${pageContext.request.contextPath}/shopping/list.com?icategory=${ICATEGORY}&nowPage=${PINFO.nowPage-3}&sort=${SORT}">«</a>
		               </c:if>
		               <c:if test="${PINFO.nowPage <= 3}">
		                  <a href="${pageContext.request.contextPath}/shopping/list.com?icategory=${ICATEGORY}&nowPage=1&sort=${SORT}">«</a>
		               </c:if>
		            </li>
		            <!-- 현재 페이지일때 active --> 
		            <c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}" var="i">
		               <li>
		                  <a href="${pageContext.request.contextPath}/shopping/list.com?icategory=${ICATEGORY}&nowPage=${i}&sort=${SORT}">${i}</a>
		               </li>
		            </c:forEach>            
		            <li>
		               <c:if test="${PINFO.nowPage < PINFO.endPage-3}">
		                  <a href="${pageContext.request.contextPath}/shopping/list.com?icategory=${ICATEGORY}&nowPage=${PINFO.nowPage+3}&sort=${SORT}">»</a>
		               </c:if>
		               <c:if test="${PINFO.nowPage >= PINFO.endPage-3}">
		                  <a href="${pageContext.request.contextPath}/shopping/list.com?icategory=${ICATEGORY}&nowPage=${PINFO.endPage}&sort=${SORT}">»</a>
		               </c:if>
		            </li>
		        </ul>
			</div>
		</c:if>
	</div>
</body>
</html>
