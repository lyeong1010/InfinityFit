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
	$(function() {
		$('#Page').children().each(function(){
	      if($(this).children('a').text()==${PINFO.nowPage}){
	         $(this).attr('class','active');
	      }
	   })
		//수정 버튼 클릭 시
		$("#up").click(function() {
					$(location).attr("href", "${pageContext.request.contextPath}/mypage/question/update.com?no=${DETAIL.qno}");
				});
		//삭제 버튼 클릭 시
		$("#del").click(function() {
							if (confirm("삭제 하시겠습니까?")) {
								$("#form").attr("action","${pageContext.request.contextPath}/mypage/question/delete.com");
								$("#form").submit();
							}
						});
		//목록 버튼 클릭 시
		$("#list").click(function() {
							$(location).attr("href","${pageContext.request.contextPath}/mypage/question/list.com")
						});

		//댓글쓰기 
		$("#wcomm").click(function() {
							var qno = "${DETAIL.qno}";
							var qcid = "${sessionScope.MID}";
							var qcnick = "${sessionScope.MNICK}";
							var qccontent = $("#qccontent").val();
							var param = {
								"qno" : qno,
								"qcid" : qcid,
								"qcnick" : qcnick,
								"qccontent" : qccontent
							};
							alert(JSON.stringify(param))
							$.ajax({
										type : "post", //데이터를 보낼 방식
										url : "${pageContext.request.contextPath}/question/wcomment.com", //데이터를 보낼 url
										data : param, //보낼 데이터
										dataType : 'text',//받는데이터타입
										success : function(data) {
											alert("댓글이 등록되었습니다.");
											location.href = "${pageContext.request.contextPath}/mypage/question/detail.com?no=${DETAIL.qno}";
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
								var qcno = $("#qcno").val();
								var param = {
									"qcno" : qcno
								}
								$.ajax({
											type : "post", //데이터를 보낼 방식
											url : "${pageContext.request.contextPath}/question/dcomment.com", //데이터를 보낼 url
											data : param, //보낼 데이터
											dataType : 'text',
											success : function(data) {
												alert("댓글이 삭제되었습니다.");
												location.href = "${pageContext.request.contextPath}/mypage/question/detail.com?no=${DETAIL.qno}";
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
	<div style="width:100%">
		<div class="boardContent">
			<div class="boardContent-buttons">
				<form id="form">
					<input type="button" value="목록" class="btn" id="list"> <input
						type="hidden" value="${DETAIL.qno}" name="no">
					<c:if test="${DETAIL.qid eq sessionScope.MID}">
						<input type="button" value="수정" class="btn" id="up">
						<input type="button" value="삭제" class="btn" id="del">
					</c:if>
				</form>
			</div>

			<!-- 테이블  -->
			<table>
				<tr>
					<td class="board-title">제목</td>
					<td class="board-title" colspan="3">${DETAIL.qtitle}</td>
				</tr>
				<tr>
					<td class="board-info"><a class="board-info-nick">작성자</a></td>
					<td class="board-info"><a class="board-info-nick">${DETAIL.qnick}</a></td>
					<td class="board-info"><a class="board-info-others">작성일 </a></td>
					<td class="board-info"><a class="board-info-others">${DETAIL.qdate}</a></td>
				</tr>
				<tr>
					<td class="board-content" colspan="4">
						<div class="board-content-div">
							내용 <br /><hr>
							<br /> ${DETAIL.qcontent}
						</div>
					</td>
				</tr>
			</table>

			<c:if test="${sessionScope.MTYPE==1 || DETAIL.qid eq sessionScope.MID}">
			<!-- 댓글  -->
			<div class="boardContent-Comment">
				<div class="boardContent-Comment-input">
					<form style="text-align: left">
							<a colspan="100%" class="board-comment-info"><a class="board-info-nick">작성자 </a>&nbsp;&nbsp; 
								<a class="board-info-others">${sessionScope.MNICK}<br></a></a>
							<input type="textarea" class="input" id="qccontent" placeholder="댓글을 입력하세요" /> 
							<input type="button" class="button" id="wcomm" value="등록" />
					</form>
				</div>
			</c:if>

				<div class="boardContent-Comment-Table">
					<table width="100%" style="border-top: 1px solid gray;">
						<c:if test="${DETAIL.qcount==0}">
							<tr>
								<td>등록된 댓글이 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach items="${COMM}" var="c">
							<input type="hidden" id="qcno" value="${c.qcno}" />
							<tr>
								<td colspan="100%" class="board-comment-info"><a
									class="board-info-nick">${c.qcnick}</a>&nbsp;&nbsp; <a
									class="board-info-others">${c.qcdate}</a></td>
							</tr>
							<tr>
								<td width="80%">${c.qccontent}</td>
								<td style="padding: 0; text-align: center;"><c:if
										test="${c.qcid eq sessionScope.MID}">
										<a class="dcomm" style="color: #ff5656;">삭제</a>
									</c:if> <c:if test="${sessionScope.MTYPE == null }">
										<a class="dcomm" style="color: #ff5656;">삭제</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>

					<div class="center">
						<ul class="pagination">
							<li><c:if test="${PINFO.nowPage > 3}">
									<a
										href="${pageContext.request.contextPath}/mypage/question/detail.com?no=${param.no }&commPage=${PINFO.nowPage-3}">«</a>
								</c:if> <c:if test="${PINFO.nowPage <= 3}">
									<a
										href="${pageContext.request.contextPath}/mypage/question/detail.com?no=${param.no }&commPage=1">«</a>
								</c:if></li>
							<!-- 현재 페이지일때 active -->
							<c:forEach begin="${PINFO.startPage}" end="${PINFO.endPage}"
								var="i">
								<li id="li">
									<!-- 스크립트 적용해야 할것같아요 --> <a
									href="${pageContext.request.contextPath}/mypage/question/detail.com?no=${param.no }&commPage=${i}">${i}</a>
								</li>
							</c:forEach>
							<li><c:if test="${PINFO.nowPage < PINFO.endPage-3}">
									<a
										href="${pageContext.request.contextPath}/mypage/question/detail.com?no=${param.no }&commPage=${PINFO.nowPage+3}">»</a>
								</c:if> <c:if test="${PINFO.nowPage >= PINFO.endPage-2}">
									<a
										href="${pageContext.request.contextPath}/mypage/question/detail.com?no=${param.no }&commPage=${PINFO.endPage}">»</a>
								</c:if></li>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>