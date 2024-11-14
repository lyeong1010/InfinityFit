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
	var msex='${LIST.msex}';
	if(msex=='여자' || msex==""){
		$('#w').attr('checked','checked');
	}else if(msex=='남자'){
		$('#m').attr('checked','checked');
	}
	
	var mbirth='${LIST.mbirth}';
	if(mbirth!=""){
		$('#d').attr('value',mbirth);
	}
	
	$('#check').click(function(){
		if($('#he').val()==""){
			alert('키를 입력하세요');
			$('#he').focus();
			return false;
		}
		if($('#we').val()==null){
			alert('현재 체중을 입력하세요');
			$('#we').focus();
			return false;
		}
		if($('#gwe').val()==null){
			alert('목표 체중을 입력하세요');
			$('#gwe').focus();
			return false;
		}
		if($('#te').val()==null){
			alert('제충 감량 기간을 입력하세요');
			$('#te').focus();
			return false;
		}
		$("#form").submit();
	});
})
</script>
</head>
<body>
<form id="form" action="./recipeCheck.com" method="post">
	<div id="wrap2">
		<div class="title2">칼로리 처방</div>
		<table class="table" style="margin-bottom:40px;">
			<tr>
				<th width="25%" style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">성별</th>
				<td style="padding:20px;border-bottom:0;"><label><input type="radio" id="w" name="sex" value="F" style="height:15px;"/>여자</label>
					<label><input type="radio" id="m" name="sex" value="M" style="height:15px;"/>남자</label></td>
			</tr>
			<tr>
				<th style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">키</th>
				<td style="padding:20px;border-bottom:0;"><input type="text" name="crheight" id="he" required="required" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">cm</td>
			</tr>
			<tr>
				<th style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">현재 체중</th>
				<td style="padding:20px;border-bottom:0;"><input type="text" name="crweight" id="we" required="required" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">kg</td>
			</tr>
			<tr>
				<th style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">목표 체중</th>
				<td style="padding:20px;border-bottom:0;">
					<input type="text" name="crgoalweight" id="gwe" required="required" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">kg
				</td>
			</tr>
			<tr>
				<th style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">생년월일</th>
				<td style="padding:20px;border-bottom:0;">
					<input type="date" id="d" name="birth" value="2000-01-01" required="required">
					</td>
			</tr>
			<tr>
				<th style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">체중 감량 기간</th>
				<td style="padding:20px;border-bottom:0;">
					<input type="text" name="crterm" id="te" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
					<input type="radio" name="type" value="개월" checked="checked" style="height:15px;"/>개월
					<input type="radio" name="type" value="주" style="height:15px;"/>주
					<input type="radio" name="type" value="일" style="height:15px;"/>일</td>
			</tr>
			<tr>
				<th style="padding:20px;border-bottom:0;border-right:2px solid #bdbdbd;">평소 활동량</th>
				<td style="padding:20px;border-bottom:0;">
					<label><input type="radio" name="cractive" value="1" checked="checked" style="height:15px;"/>활동안함 (운동을 전혀 안 해요.)</label><br/>
					<label><input type="radio" name="cractive" value="2" style="height:15px;"/>가벼운 활동 (평소 가벼운 운동이나 스포츠를 즐겨요)</label><br/>
					<label><input type="radio" name="cractive" value="3" style="height:15px;"/>보통 활동 (평소 적당한 운동이나 스포츠를 즐겨요.)</label><br/>
					<label><input type="radio" name="cractive" value="4" style="height:15px;"/>많은 활동 (평소 강렬한 운동이나 스포츠를 즐겨요.)</label><br/>
					<label><input type="radio" name="cractive" value="5" style="height:15px;"/>격심한 활동 (평소 매우 심한 운동을 하거나 육체를 쓰는 직업이예요.)</label></td>
			</tr>
		</table>
		<div class="center">
			<a class="btn3" id="check">칼로리 처방 받기</a>
		</div>
	</div>
</form>
</body>
</html>