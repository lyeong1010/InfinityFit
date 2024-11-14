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
		//삭제 버튼 클릭 시
		$("#del").click(function(){
			if(confirm("삭제 하시겠습니까?")){
				$("#form").attr("action","${pageContext.request.contextPath}/question/delete/user/admin.com");
				$("#form").submit();
			}
		});
				
		//목록 버튼 클릭 시
		$("#list").click(function(){
			$(location).attr("href","${pageContext.request.contextPath}/question/list/user/admin.com")
		});
		
		//댓글 창 엔터처리
		$("#qccontent").keypress(function(e){
			var code = e.keyCode ? e.keyCode : e.which; 
			if (code == 13){ // EnterKey 
				$('#wcomm').click();
			}
		});
		
		//댓글쓰기 
		$("#wcomm").click(function(){
			if($("#qccontent").val()==""){
				alert('댓글을 입력하세요.');
				return false;
			}
			var qcid = "${sessionScope.MID}";
			var qno = "${DETAIL.qno}";
			var qcnick =  "${sessionScope.MNICK}";
			var qccontent = $("#qccontent").val();
			var param = {"qno" : qno, "qcid" : qcid, "qcnick" : qcnick , "qccontent" : qccontent};
		$.ajax({
			type: "post", //데이터를 보낼 방식
			url: "${pageContext.request.contextPath}/question/wcomment.com", //데이터를 보낼 url
			data: param, //보낼 데이터
			dataType: 'text',
			success: function(data){
		            location.href = "${pageContext.request.contextPath}/question/detail/user/admin.com?no=${DETAIL.qno}";
		            },
		    error:function(request,status,error){
		    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		             }
			});
		});
		
		//댓글 삭제
		$(".dcomm").click(function(){
			 if(confirm("삭제 하시겠습니까?")){
				 var qcno = $(event.target).attr('data-no');
				 var param = {"qcno" : qcno}
			$.ajax({
				type: "post", //데이터를 보낼 방식
				url: "${pageContext.request.contextPath}/question/dcomment.com", //데이터를 보낼 url
				data: param, //보낼 데이터
				dataType: 'text',
				success: function(data){
			            alert("댓글이 삭제되었습니다.");
			            location.href = "${pageContext.request.contextPath}/question/detail/user/admin.com?no=${DETAIL.qno}";},
			    error:function(request,status,error){
			    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
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
					<input type="button" value="목록" class="btn" id="list">
					<input type="hidden" value="${DETAIL.qno}" name="no">
					<c:if test="${sessionScope.MTYPE eq 1}">
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
						<div class="board-content-div">내용 <br/><br/>
							${DETAIL.qcontent}</div></td>
				</tr>
			</table>
		
			<!-- 댓글  -->
			<div class="boardContent-Comment">
				<div class="boardContent-Comment-input">
					<form style="text-align: left">
						<a colspan="100%" class="board-comment-info"><a class="board-info-nick">작성자 </a>&nbsp;&nbsp; 
								<a class="board-info-others">${sessionScope.MNICK}<br></a></a>
						<input type="hidden" id="qcid" value="${sessionScope.MID}"/>
						<input type="textarea" class="input" id="qccontent" placeholder="댓글을 입력하세요" /> 
						<input type="button" class="button" id="wcomm" value="등록" />
					</form>
				</div>
		
				<div class="boardContent-Comment-comment" style="padding: 10px; font-size: 1.5rem;">
					댓글(<a style="color: #ff5656;">${DETAIL.qcount}개</a>)
				</div>
		
				<div class="boardContent-Comment-Table">
					<table width="100%" style="border-top: 1px solid gray;">
						<c:forEach items="${COMM}" var="c">
						<tr>
							<td colspan="100%" class="board-comment-info"><a class="board-info-nick">
							<c:if test="${c.qcid eq 'admin'}">
								<img src="${pageContext.request.contextPath}/resources/img/crown.png" style="width: 20px; height: 30px;"/></c:if>
								${c.qcnick}
							</a>&nbsp;&nbsp;
								<a class="board-info-others">
									${c.qcdate}</a></td>
						</tr>
						<tr>
							<td width="80%">${c.qccontent}</td>
							<td style="padding: 0; text-align: center;">
								<c:if test="${1 eq sessionScope.MTYPE}">
									<a class="dcomm" data-no="${c.qcno}" style="color: #ff5656;">삭제(관리자)</a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>