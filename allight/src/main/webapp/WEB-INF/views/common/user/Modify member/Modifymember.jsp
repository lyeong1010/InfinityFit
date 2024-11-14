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
	//닉네임중복확인
	$("#nickCheck").click(function() {
		var mnick = $("#mnick").val();
		$.ajax({
			url : "../../nickChk.com",
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
			},
			error : function(request, status, error) {
				alert("code:" + request.status+ "\n" + "message:"+ request.responseText+ "\n" + "error:" + error);
			}
		});
	});
	$("#modify").submit(function(){
		//닉네임 중복확인여부 체크
		//alert("중복체크들어간다");
		//수정되었지만 수정한 닉네임이 기존과 같다면 바로넘김
		if($("#mnick").val()=="${MEMINFO.mnick}"){
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
		//비밀번호 입력여부
		if($("#mpw").val().length==0){
			alert("비밀번호를 입력하지 않았습니다.")
			$("#mpw").focus();
			return false;
		}
		if($("#mpw2").val().length==0){
			alert("비밀번호를 입력하지 않았습니다.")
			$("#mpw2").focus();
			return false;
		}
		//비밀번호 길이 체크
		if($("#mpw").val().length<8 || $("#mpw").val().length>16){
			alert("비밀번호는 8~16자리로 입력해주세요.")
			$("#mpw").focus();
			return false;
		}
		//비밀번호 일치 여부
		if($("#mpw").val()!=$("#mpw2").val()){
		    alert('비밀번호가 일치 하지 않습니다')
		    frm.mpw2.value='';
		    frm.mpw2.focus();
		    return false;
		    }
		//전화번호 입력여부
		if($("#mtel").val().selectedIndex<1){
			alert("전화번호를 입력하지 않았습니다.")
			$("#mtel").focus();
			return false;
		}
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
	})
});

</script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="title3" style="margin-bottom:30px">개인정보 확인/수정</div>
	<form
		action="<%=request.getContextPath()%>/mypage/member/modify.com"
		method="post" name="modify" id="modify">
		<input type="hidden" id="mno" name="mno"
					value="${MEMINFO.mno}" />
		<input type="hidden" id="mno" name="mno" value="${MEMINFO.mno}" />
		<table>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">아이디 :</td>
				<td style="padding:15px;border-bottom:0;">${MEMINFO.mid}<input type="hidden" id="mid" name="mid"
					value="${MEMINFO.mid}" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">이름 :</td>
				<td style="padding:15px;border-bottom:0;">${MEMINFO.mname}<input type="hidden" id="mname" name="mname"
					value="${MEMINFO.mname}" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">이메일 :</td>
				<td style="padding:15px;border-bottom:0;">${MEMINFO.memail}<input type="hidden" id="memail" name="memail"
					value="${MEMINFO.memail}" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">성별 :</td>
				<td style="padding:15px;border-bottom:0;"><select name="msex" id="msex" required="required">
						<option value="">선택</option>
						<option value="남자">남자</option>
						<option value="여자">여자</option>
				</select></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">생년월일 :</td>
				<td style="padding:15px;border-bottom:0;"><input type="date" id="mbirth"
					name="mbirth" value="${MEMINFO.mbirth}" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">닉네임 :</td>
				<td style="padding:15px;border-bottom:0;"><input type="text" id="mnick"
					name="mnick" value="${MEMINFO.mnick}" />
					<button id="nickCheck" type="button" class="btn">중복확인</button>
					<input type="hidden" id="nickChecked" value="1" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">비밀번호 :</td>
				<td style="padding:15px;border-bottom:0;"><input type="password" id="mpw" name="mpw"
					value="${MEMINFO.mpw}" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">비밀번호 확인 :</td>
				<td style="padding:15px;border-bottom:0;"><input type="password" id="mpw2" name="mpw2"
					value="${MEMINFO.mpw}" /></td>
			</tr>
			<tr>
				<td width="30%" style="padding:15px;border-bottom:0;border-right:2px solid #bdbdbd;">핸드폰번호 :</td>
				<td style="padding:15px;border-bottom:0;"><input type="text" id="mtel" name="mtel"
					value="${MEMINFO.mtel}" /></td>
			</tr>
		</table>
		<div class="center" style="margin:40px">
			<input type="submit" value="수정" class="btn5"> 
		</div>
	</form>
</body>
</html>