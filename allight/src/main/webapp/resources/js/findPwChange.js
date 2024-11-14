$(function(){
	$("#next").click(function(){
		//비밀번호 입력여부 검사
		if($("#mpw").val().length==0){
			alert("비밀번호를 입력해주세요")
			$("#mpw").focus();
			return false;
		}
		//비밀번호 입력여부 검사
		if($("#mpw2").val().length==0){
			alert("비밀번호확인을 입력해주세요")
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
	});
});