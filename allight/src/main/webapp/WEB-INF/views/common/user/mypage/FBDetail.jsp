<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	Date now = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
$(function(){
	   $('#Page').children().each(function(){
	      if($(this).children('a').text()==${PINFO.nowPage}){
	         $(this).attr('class','active');
	      }
	   })
	})
	$(function() {
		//수정 버튼 클릭 시
		$("#up").click(function() {
							$(location).attr("href", "${pageContext.request.contextPath}/mypage/freeboard/update.com?no=${DETAIL.fno}");
						});
		//삭제 버튼 클릭 시
		$("#del").click(function() {
							if (confirm("삭제 하시겠습니까?")) {
								$(location).attr("href", "${pageContext.request.contextPath}/mypage/freeboard/delete.com?no=${DETAIL.fno}");
							}
						});
		//목록 버튼 클릭 시
		$("#list").click(
						function() {
							$(location).attr("href","${pageContext.request.contextPath}/mypage/freeboard/list.com")
						});

		//댓글쓰기 
		$("#wcomm")
				.click(
						function() {
							var fno = "${DETAIL.fno}";
							var fcid = "${sessionScope.MID}";
							var fcnick = "${sessionScope.MNICK}";
							var fccontent = $("#fccontent").val();
							var param = {
								"fno" : fno,
								"fcnick" : fcnick,
								"fcid" : fcid,
								"fccontent" : fccontent
							};
							alert(JSON.stringify(param))
							$
									.ajax({
										type : "post", //데이터를 보낼 방식
										url : "${pageContext.request.contextPath}/mypage/freeboard/wcomment.com", //데이터를 보낼 url
										data : param, //보낼 데이터
										dataType : 'text',//받는데이터타입
										success : function(data) {
											alert("댓글이 등록되었습니다.");
											location.href = "${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${DETAIL.fno}";
										},
										error : function(request, status, error) {
											alert("code:" + request.status
													+ "\n" + "message:"
													+ request.responseText
													+ "\n" + "error:" + error);
										}
									});
						});

		//댓글 삭제
		$(".dcomm")
				.click(
						function() {
							if (confirm("삭제 하시겠습니까?")) {
								var fcno = $(event.target).attr('data-no');
								 var param = {"fcno" : fcno}
								$.ajax({
											type : "post", //데이터를 보낼 방식
											url : "${pageContext.request.contextPath}/mypage/freeboard/dcomment.com", //데이터를 보낼 url
											data : param, //보낼 데이터
											dataType : 'text',
											success : function(data) {
												alert("댓글이 삭제되었습니다.");
												location.href = "${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${DETAIL.fno}";
											},
											error : function(request, status,
													error) {
												alert("code:" + request.status
														+ "\n" + "message:"
														+ request.responseText
														+ "\n" + "error:"
														+ error);
											}
										});
							}
						});

	})
</script>
</head>
<body>
	<div class="container">
		<div class="boardContent">
			<div class="boardContent-buttons">
				<form id="form">
					<input type="button" value="목록" class="btn" id="list"> <input
						type="hidden" value="${DETAIL.fno}" name="no">
					<c:if test="${DETAIL.fid eq sessionScope.MID}">
						<input type="button" value="수정" class="btn" id="up">
						<input type="button" value="삭제" class="btn" id="del">
					</c:if>
				</form>
			</div>

			<!-- 테이블  -->
			<table>
				<tr>
					<td class="board-title">제목</td>
					<td class="board-title" colspan="3">${DETAIL.ftitle}</td>
				</tr>
				<tr>
					<td class="board-info"><a class="board-info-nick">작성자</a></td>
					<td class="board-info"><a class="board-info-nick">${DETAIL.fid}</a></td>
					<td class="board-info"><a class="board-info-others">작성일 </a></td>
					<td class="board-info"><a class="board-info-others">${DETAIL.fdate}</a></td>
				</tr>
				<tr>
					<td class="board-content" colspan="4">
						<div class="board-content-div">
							내용 <br /><c:forEach items="${FILE}" var="file">
							<c:set var="path" value="/item/img/" />
							<c:set var="name" value="${file.fiimg}" />
							<img src="<c:out value="${path}"/><c:out value="${name}"/>" />
							</c:forEach>
							<br />
							${DETAIL.fcontent}
						</div>
					</td>
				</tr>
			</table>

			<!-- 댓글  -->
			<div class="boardContent-Comment">
				<div class="boardContent-Comment-input">
					<form style="text-align: left">
						<a class="board-info-nick">작성자 ${sessionScope.MID}</a>&nbsp;&nbsp; 
						<a class="board-info-others">작성일 <%=sf.format(now)%></a>
							<input type="textarea" class="input" id="fccontent"
							placeholder="댓글을 입력하세요" /> <input type="button" class="button"
							id="wcomm" value="등록" />
					</form>
				</div>

				<div class="boardContent-Comment-comment"
					style="padding: 10px; font-size: 1.5rem;">
					댓글(<a style="color: #ff5656;">${DETAIL.fccount}개</a>)
				</div>

				<div class="boardContent-Comment-Table">
					<table width="100%" style="border-top: 1px solid gray;">
						<c:if test="${DETAIL.fccount==0}">
							<tr>
								<td>등록된 댓글이 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach items="${COMM}" var="c">
							<tr>
								<td colspan="100%" class="board-comment-info">
									<a class="board-info-nick">${c.fcnick}</a>&nbsp;&nbsp; 
									<a class="board-info-others"><fmt:formatDate value="${c.fcdate}" type="both"/></a>
								</td>
							</tr>
							<tr>
								<td width="80%">${c.fccontent}</td>
								<td style="padding: 0; text-align: center;">
									<c:if test="${empty sessionScope.MID}">
										<a onclick="loginCheck();" style="cursor:pointer;"> 
											<img class="like" src="${pageContext.request.contextPath}/resources/img/like.png" />
										</a>
									</c:if>
									<c:if test="${!empty sessionScope.MID}">
									<a href="${pageContext.request.contextPath}/mypage/freeboard/like.com?no=${param.no}&commPage=${PINFO.nowPage}&num=${c.fcno}"> 
										<c:if test="${c.isLiked}">
											<img class="like" src="${pageContext.request.contextPath}/resources/img/liked.png" />
										</c:if>
										<c:if test="${!c.isLiked}">
											<img class="like" src="${pageContext.request.contextPath}/resources/img/like.png" />
										</c:if>
									</a>
								</c:if>
								<a class="aNone">${c.amount}</a>
								
								<c:if test="${c.fcid eq sessionScope.MID || sessionScope.MTYPE == 1}">
										<a class="dcomm" data-no="${c.fcno}" style="color: #ff5656;">삭제</a>
								</c:if> 
							</td>
							</tr>
						</c:forEach>
					</table>

					<div class="center">
						<ul class="pagination" id="Page">
							<li><c:if test="${PINFO.nowPage > 3}">
									<a
										href="${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${param.no }&commPage=${PINFO.nowPage-3}">«</a>
								</c:if> <c:if test="${PINFO.nowPage <= 3}">
									<a
										href="${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${param.no }&commPage=1">«</a>
								</c:if></li>
							<!-- 현재 페이지일때 active -->
							<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}"
								var="i">
								<li id="li">
									<!-- 스크립트 적용해야 할것같아요 --> <a
									href="${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${param.no }&commPage=${i}">${i}</a>
								</li>
							</c:forEach>
							<li><c:if test="${PINFO.nowPage < PINFO.endPage-3}">
									<a
										href="${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${param.no }&commPage=${PINFO.nowPage+3}">»</a>
								</c:if> <c:if test="${PINFO.nowPage >= PINFO.endPage-2}">
									<a
										href="${pageContext.request.contextPath}/mypage/freeboard/detail.com?no=${param.no }&commPage=${PINFO.endPage}">»</a>
								</c:if></li>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>