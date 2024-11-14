//alert("id.js 들어옴")
$(function(){
	//이름 입력여부 검사
	$("#next").click(function(){
		if($("#mname").val().length==0){
			alert("이름을 입력해주세요")
			$("#mname").focus();
			return false;
		}
		//이메일 입력여부 검사
		if($("#memail").val().length==0){
			alert("이메일을 입력해주세요")
			$("#memail").focus();
			return false;
		}
	})
});