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
			// 내용 입력 여부 체크
			if(${!empty src}){
				if($('#src').attr('src')=="" && $('#fcontent').val().trim()=="" && !$('input[name=files]').val()){
					alert('내용을 입력해주세요.')
					return false;
				}
			}
			if(!$('#ftitle').val()){
				alert('제목을 입력해주세요.')
				return false;
			}
			if($('#fcontent').val().trim()=="" && !$('input[name=files]').val()){
				alert('내용을 입력해주세요.');
				return false;
				
			}
			if(confirm('게시글을 등록하시겠습니까?')){
				$("#form").submit();
			}
		});
		
		//목록 버튼 클릭 시
		$("#list").click(function() {
			if(contirm('목록으로 가시겠습니까?\n작성하신 내용은 저장되지 않습니다.')){
				$(location).attr("href","${pageContext.request.contextPath}/freeboard/list.com")
			}
		});
		
		//취소 버튼 클릭 시
		$('#no').click(function(){
			if(contirm('게시글 작성을 취소하시겠습니까?\n작성하신 내용은 저장되지 않습니다.')){
				location.href = "${pageContext.request.contextPath}/myFAE.com?num=${num}";
			}
		})
		
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
			    tr+= 	'<td><input type="file" name="files" id="files'+cnt+'"/></td><td></td>';
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
		
		// 이미지 삭제 버튼 눌렀을 때
		if(${!empty src}){
			$('#imgdelbtn').click(function(){
				if(confirm('사진을 삭제하시겠습니까?\n삭제된 사진은 다시 복구할 수 없습니다.')){
					$('#img').attr('class',"hidden");
					$('#imgdel').val(1);
				}
			})
		}
		
	})
</script>
</head>
<body>
	<form id="form" method="post" enctype = "multipart/form-data"
	action="${pageContext.request.contextPath}/freeboard/diaryWrite.com" >
	
		<div class="container">
			<div class="boardContent">
				<div class="boardContent-buttons">
					<input type="button" value="목록" class="btn" id="list">
				</div>
				<table>
					<tr>
						<td class="board-title">제목</td>
						<td class="board-title" colspan="3"><input type="text" id="ftitle" name="ftitle" size="60%" required="required" value="${FDTO.ftitle}" placeholder="제목을 입력하세요"></td>
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
							<option value="다이어리" selected="selected">다이어리</option>
						</select></a></td>
					</tr>
					<c:if test="${!empty src}">
						<tr>
							<td colspan="3">
								<img src="${src}" id="img" style="max-width:500px">
							</td>
							<td>
								<input type="button" id="imgdelbtn" value="지우기"/>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="board-content" colspan="4">
							<div class="board-content-div">내용 <br/> <br/>
								<textarea rows="12" cols="100" name="fcontent" id="fcontent" required="required" placeholder="내용을 입력하세요">${FDTO.fcontent}</textarea>
							</div>
						</td>
					</tr>
		 			 <tr>
	 			</tr>
	 			<tr>
	 				<th>첨부파일</th>
	 				<td colspan="2"><input type="file" name="files" id="files" value="${FDTO.files}"/></td>
	 				<td>
	 					<input type="button" value="추가" id="aBtn"/>
	 					<input type="button" value="삭제" id="dtn"/>
	 				</td>
	 			</tr>
	 			<tr id="copy">
	 				<td colspan="2">
	 					<input type="button" value="글쓰기" class="btn" id="ok">
	 					<input type="button" value="취소" class="btn" id="no">
 					</td>
	 			</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="num" value="${num}"/>
		<c:if test="${!empty src}">
			<input type="hidden" name="imgdel" id="imgdel" value="0"/>
		</c:if>
		<c:if test="${empty src}">
			<input type="hidden" name="imgdel" id="imgdel" value="2"/>
		</c:if>
	</form>
	
</body>
</html>