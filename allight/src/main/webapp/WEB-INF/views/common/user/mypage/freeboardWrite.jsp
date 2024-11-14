<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	Date now = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(function() {
		//확인 버튼 클릭 시
		$("#ok").click(function() {
			$("#form").submit();
		});
		//목록 버튼 클릭 시
		$("#list").click(function() {
			$(location).attr("href","${pageContext.request.contextPath}/mypage/freeboard/list.com")
		});
		
		var cnt = 1; //첨부파일수를 저장하는 변수
		//추가버튼 클릭시  첨부파일 동적으로 (최대 5개)추가 
		$("#aBtn").click(function(){
			cnt++;
			if( cnt==6 ){
				alert("첨부파일은 최대 5개까지 가능합니다");
				cnt=5;
				return;
			}
			//추가할 대상 
			var tr = '<tr>';
			    tr+= 	'<th>첨부파일</th>';
			    tr+= 	'<td><input type="file" name="files" id="files'+cnt+'"/></td>';
			    tr+= '</tr>';
			//원하는 위치에 붙인다
			$("#copy").before(tr);
		});
		//삭제버튼 클릭시   첨부파일 동적으로 삭제  (최소1개남기고)
		$("#dtn").click(function(){
			if( cnt==1 ){
				alert("첨부파일은 최소 1개가 있어야 합니다");
				return;
			}
			//제거
			var tr = $("#files"+cnt).parents("tr");
			tr.remove();
			cnt--;
		});
	})
</script>
</head>
<body>
	<form id="form" method="post" enctype = "multipart/form-data"
	action="${pageContext.request.contextPath}/mypage/freeboard/write.com" >
	
		<div class="container">
			<div class="boardContent">
				<div class="boardContent-buttons">
					<input type="button" value="목록" class="btn" id="list">
				</div>
				<table>
					<tr>
						<td class="board-title">제목</td>
						<td class="board-title" colspan="3"><input type="text"	name="ftitle" size="60%" required="required" placeholder="제목을 입력하세요"></td>
					</tr>
					<tr>
						<td class="board-info"><a class="board-info-nick">작성자</a></td>
						<td class="board-info"><a class="board-info-nick">${sessionScope.MNICK}</a></td>
						<td class="board-info"><a class="board-info-others">작성일 </a></td>
						<td class="board-info"><a class="board-info-others"><%=sf.format(now)%></a></td>
					</tr>
					<tr>
					<td class="board-info"><a class="board-info-others">말머리 </a></td>
					<td class="board-info"><a class="board-info-others">
						<select name="ftype" class="selectCss">
							<option value="일반">일반</option>
							<option value="다이어리">다이어리</option>
						</select></a></td>
					</tr>
					<tr>
						<td class="board-content" colspan="4">
							<div class="board-content-div">내용 <br/> <br/>
								<textarea rows="12" cols="100" name="fcontent"	required="required" placeholder="내용을 입력하세요"></textarea>
							</div>
						</td>
					</tr>
		 			 <tr>
	 				<th>첨부파일</th>
	 				<td>
	 					<input type="button" value="추가" id="aBtn"/>
	 					<input type="button" value="삭제" id="dtn"/>
	 				</td>
	 			</tr>
	 			<tr>
	 				<th>첨부파일</th>
	 				<td><input type="file" name="files" id="files"/></td>
	 			</tr>
	 			<tr id="copy">
	 				<td colspan="2"><input type="button" value="글쓰기" class="btn" id="ok"></td>
	 			</tr>
				</table>
			</div>
		</div>
	</form>
	
</body>
</html>