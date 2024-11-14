<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품후기 작성</title>

<script type="text/javascript">
$(function(){
	// 상품 문의 등록 제출
	$('#writeSubmit').click(function(){
		if($('#iqtitle').val()=='' || $('#iqtitle').val()==null){
			alert('제목을 입력하세요.')
			return false;
		}
		
		if($('#icontent').val()=='' || $('#icontent').val()==null){
			alert('내용을 입력하세요.')
			return false;
		}
		
		if(confirm('상품문의를 등록하시겠습니까?')){
			$('reviewWriteFrm').submit();
		}
	})
	
	// 상품 문의 등록 취소
	$('#writeCansel').click(function(){
		if(confirm('상품문의 등록을 취소하시겠습니까?')){
			window.history.back();
		}
	})
})
</script>
</head>

<body>
	<form action="" id="reviewWriteFrm">
		<table>
			<tr>
				<td width="20%">만족도</td>
				<td>
					<select name="rgrade">
						<option value="5">★★★★★</option>
						<option value="4">★★★★☆</option>
						<option value="3">★★★☆☆</option>
						<option value="2">★★☆☆☆</option>
						<option value="1">★☆☆☆☆</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea rows="10" cols="200" name="rcontent" style="width:100%"></textarea>
				</td>
			</tr>
		</table>
		<input type="hidden" name="ino" value="${param.ino}"/>	
		<input type="hidden" name="rid" value="${sessionScope.MID}"/>	
		<input type="hidden" name="rnick" value="${sessionScope.MNICK}"/>	
		<input type="hidden" name="rdate" value="${sessionScope.DATE}"/>
		
		<input type="button" value="등록" id="writeSubmit"/>
		<input type="button" value="취소" id="writeCansel"/>
	</form>
</body>
</html>