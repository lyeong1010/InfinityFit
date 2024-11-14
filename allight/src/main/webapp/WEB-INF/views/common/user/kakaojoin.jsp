<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
$(function() {
	$("#mnick").change(function(){
		//alert("닉네임 수정됨");
		$('#nickChecked').val("0");
	})
	//닉네임중복확인.
	$("#nickCheck").click(function() {
		var mnick = $("#mnick").val();
		$.ajax({
			url : "./nickChk.com",
			type : "POST",
			dataType : "text",
			async : false,
			data :{ 
				mnick : $("#mnick").val()
			},
			success : function(data) {
				if (data == "fail") {
					alert("이미 등록된 닉네임입니다.");
					$("#mnick").focus();
				} else {
					alert("사용 가능한 닉네임입니다.");
					$('#nickChecked').val("1");
				}
			}
		});
	});
	
	$("#com").click(function(){
		//닉네임 중복확인여부 체크
		//alert("중복체크들어간다");
		//수정되었지만 수정한 닉네임이 기존과 같다면 바로넘김 
		if($("#mnick").val()=="${mdto.mnick}"){
			$('#nickChecked').val("1");
		}
		if($("#nickChecked").val()=="0"){
			alert("닉네임 중복확인을 해주세요.")
			return false;
		}
		//닉네임 입력 여부
		if($("#mnick").val().length==0){
			alert("닉네임을 입력하지 않았습니다.")
			$("#mnick").focus();
			return false;
		}
		//닉네임 길이 체크
		if($("#mnick").val().length<3 || $("#mnick").val().length>8){
			alert("닉네임은 3~8자리로 입력해주세요.")
			$("#mnick").focus();
			return false;
		}
		//전화번호 입력여부 
		if($("#mtel1").val().length==0){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel1").focus();
			return false;
		}
		if($("#mtel2").val().length==0){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel2").focus();
			return false;
		}
		
		if($("#mtel option:selected").val()=='선택'){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel").focus();
			return false;
		}
		if($("#msex option:selected").val()=='선택'){
			alert("성별을 선택하지 않았습니다.")
			$("#msex").focus();
			return false;
		}
		$("#form").submit()
	})
});

</script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="form" action="<%=request.getContextPath()%>/kakaoj.com" method="post">
	<input type="hidden" name="mid" value="${mdto.mid}"/>
		<table>
			<tr>
				<td>이름</td>
				<td><input type="text" id="mname" name="mname" value="${mdto.mname}" /></td>
			</tr>
			<tr>
				<td>닉네임</td>
				<td><input type="text" id="mnick" name="mnick"/>
					<button id="nickCheck" type="button">중복확인</button>
					<input type="hidden" id="nickChecked" value="0"/></td>
			</tr>
			<tr>
				<td>생일</td>
				<td><input type="date" id="mbirth" name="mbirth" value="2000-01-01"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td>${mdto.memail}</td>
			</tr>
			<tr>
				<td>핸드폰번호</td>
				<td><select id="mtel" name="mtel" style="height: 20">
						<option selected>선택</option>
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="016">016</option>
						<option value="017">017</option>
						<option value="019">019</option>
				</select>&nbsp;- <input id="mtel1" type="text" name="mtel1" maxlength="4" size="5">
				&nbsp;- <input id="mtel2" type="text" name="mtel2" maxlength="4" size="5"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td><select name="msex" id="msex" required="required">
						<option>선택</option>
						<option value="남자">남자</option>
						<option value="여자">여자</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center">
				<input type="button" id="com" value="수정"></td>
			</tr>
		</table>
	</form>
</body>
</html>