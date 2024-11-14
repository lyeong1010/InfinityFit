<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
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
	$(function() {
		//수정 버튼 클릭 시
		$("#up").click(function() {
							$(location).attr("href", "${pageContext.request.contextPath}/freeboard/update.com?no=${DETAIL.fno}");
						});
		//삭제 버튼 클릭 시
		$("#del").click(function() {
							if (confirm("삭제 하시겠습니까?")) {
								$(location).attr("href", "${pageContext.request.contextPath}/freeboard/delete.com?no=${DETAIL.fno}");
							}
						});
		//목록 버튼 클릭 시
		$("#list").click(function() {
							$(location).attr("href","${pageContext.request.contextPath}/freeboard/list.com")
						});
		
		//댓글 창 엔터처리
		$("#fccontent").keypress(function(e){
			var code = e.keyCode ? e.keyCode : e.which; 
			if (code == 13){ // EnterKey 
				$('#wcomm').click();
			}
		});

		//댓글쓰기 
		$("#wcomm").click(function() {
			if($("#fccontent").val()==""){
				alert("댓글을 입력하세요");
				return false;
			}
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
							$.ajax({
										type : "post", //데이터를 보낼 방식
										url : "${pageContext.request.contextPath}/freeboard/wcomment.com", //데이터를 보낼 url
										data : param, //보낼 데이터
										dataType : 'text',//받는데이터타입
										success : function(data) {
											location.href = "${pageContext.request.contextPath}/freeboard/detail.com?no=${DETAIL.fno}";
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
		$(".dcomm").click(function() {
							if (confirm("삭제 하시겠습니까?")) {
								var fcno = $(event.target).attr('data-no');
								 var param = {"fcno" : fcno}
								$.ajax({
											type : "post", //데이터를 보낼 방식
											url : "${pageContext.request.contextPath}/freeboard/dcomment.com", //데이터를 보낼 url
											data : param, //보낼 데이터
											dataType : 'text',
											success : function(data) {
												location.href = "${pageContext.request.contextPath}/freeboard/detail.com?no=${DETAIL.fno}";
											},
											error : function(request, status,error) {
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
function loginCheck (){
	if('${sessionScope.MID}'==''){
		if(confirm('로그인 후 이용가능한 서비스입니다.\n로그인하시겠습니까?')){
			$('#reUrlFrm').submit();
		}
	}
}

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
					<td class="board-info"><a class="board-info-nick">${DETAIL.fnick}</a></td>
					<td class="board-info"><a class="board-info-others">작성일 </a></td>
					<td class="board-info"><a class="board-info-others">${DETAIL.fdate}</a></td>
				</tr>
				<tr>
					<td class="board-content" colspan="4">
						<div class="board-content-div">
							<br /><c:forEach items="${FILE}" var="file">
							<c:set var="path" value="/item/img/" />
							<c:set var="name" value="${file.fiimg}" />
							<img src="<c:out value="${path}"/><c:out value="${name}"/>" />
							</c:forEach>
							<br />
							<pre>${DETAIL.fcontent}</pre>
						</div>
					</td>
				</tr>
			</table>

			<!-- 댓글  -->
			<div class="boardContent-Comment">
				<div class="boardContent-Comment-input">
					<form style="text-align: left">
						<a colspan="100%" class="board-comment-info"><a class="board-info-nick">작성자 </a>&nbsp;&nbsp; 
								<a class="board-info-others">
								<c:if test="${DETAIL.fid eq 'admin'}">
									<img src="${pageContext.request.contextPath}/resources/img/crown.png" style="width: 20px; height: 30px;"/></c:if>
								${sessionScope.MNICK}<br></a></a>
						<c:if test="${sessionScope.MID ne null}">
						<input type="textarea" class="input" id="fccontent" placeholder="댓글을 입력하세요" />
						<input type="button" class="button" id="wcomm" value="등록" onclick="loginCheck();"/>
						</c:if>
						<c:if test="${sessionScope.MID eq null}">
						<input type="textarea" class="input" id="fccontent" placeholder="회원 로그인이 필요합니다" readonly/> 
						<input type="button" class="button" id="wcomm" value="등록"  onclick="loginCheck();" disabled="true"/>
						</c:if>
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
						<form>
						<c:forEach items="${COMM}" var="c">
							<tr>
								<td colspan="100%" class="board-comment-info">
									<a class="board-info-nick">
								<c:if test="${c.fcid eq 'admin'}">
									<img src="${pageContext.request.contextPath}/resources/img/crown.png" style="width: 20px; height: 30px;"/></c:if>
								${c.fcnick}</a>&nbsp;&nbsp; 
									<a class="board-info-others">
										<fmt:formatDate value="${c.fcdate}" type="both"/></a>
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
									<a href="${pageContext.request.contextPath}/freeboard/like.com?no=${param.no}&commPage=${PINFO.nowPage}&num=${c.fcno}"> 
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
						</form>
					</table>

					<div class="center">
						<ul class="pagination" id="Page">
							<li><c:if test="${PINFO.nowPage > 3}">
									<a
										href="${pageContext.request.contextPath}/freeboard/detail.com?no=${param.no}&commPage=${PINFO.nowPage-3}">«</a>
								</c:if> <c:if test="${PINFO.nowPage <= 3}">
									<a
										href="${pageContext.request.contextPath}/freeboard/detail.com?no=${param.no }&commPage=1">«</a>
								</c:if></li>
							<!-- 현재 페이지일때 active -->
							<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}"
								var="i">
								<li id="li">
									<!-- 스크립트 적용해야 할것같아요 --> <a
									href="${pageContext.request.contextPath}/freeboard/detail.com?no=${param.no }&commPage=${i}">${i}</a>
								</li>
							</c:forEach>
							<li><c:if test="${PINFO.nowPage < PINFO.endPage-3}">
									<a
										href="${pageContext.request.contextPath}/freeboard/detail.com?no=${param.no }&commPage=${PINFO.nowPage+3}">»</a>
								</c:if> <c:if test="${PINFO.nowPage >= PINFO.endPage-2}">
									<a
										href="${pageContext.request.contextPath}/freeboard/detail.com?no=${param.no }&commPage=${PINFO.endPage}">»</a>
								</c:if></li>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
	
	<form action="${pageContext.request.contextPath}/login.com" id="reUrlFrm">
		<input type="hidden" name="reUrl" value="${pageContext.request.contextPath}/freeboard/detail.com?no=${param.no }">
	</form>
</body>
</html>