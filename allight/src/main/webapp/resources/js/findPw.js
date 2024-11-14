$(function(){
	$("#next").click(function(){
		//아이디 입력여부 검사
		if($("#mid").val().length==0){
			alert("아이디를 입력해주세요")
			$("#mid").focus();
			return false;
		}
		//이메일 입력여부 검사
		if($("#memail").val().length==0){
			alert("이메일을 정확히 입력해주세요")
			$("#memail").focus();
			return false;
		}
		if($("#memail2").val().length==0){
			alert("이메일을 정확히 입력해주세요")
			$("#memail2").focus();
			return false;
		}
		if($("#checkCODE").css('display')=='none'){
			alert("이메일 인증을 완료해 주세요1.")
			return false;
		}
	});	

	//이메일 인증
	$("#check_pw_mail").click(function() {
		alert("인증메일을 보내시겠습니까?");
		var email = document.getElementById("memail").value;
		var email2 = document.getElementById("memail2").value;

		//이메일 입력 유효성 확인
		if(email==''||email2==''){   
			alert("이메일을 정확히 입력해주세요");
			email.focus();
			return false;
		}

		var memail = email+"@"+email2;

		//이메일 확인 후 코드 전송
		$.ajax({
			url : "checkPwMail.com?email="+ memail,
			type : "POST",
			success : function(data) { 
				if (data != 1) {
					//이메일이 이미 존재할 때 -> 사용가능(코드값 data로 받기)
					alert("메일을 성공적으로 보냈습니다.메일함을 확인해주세요")
					$("#checkCODE").css('display','');
					$("#check_pw_mail").html('재전송');
					realcode=data;
				} else if(data == 1) {
					//이메일이 존재하지 않을 때 -> 사용불가
					alert("입력하신 이메일이 존재하지 않습니다")
					$("#memail").val("");
					$("#memail").focus();
				}
			}, error : function() {
				console.log("실패");
			}
		});
	});
	
    //인증번호 확인
    $("#check_pw_code").click(function() {
    
       var usercode = document.getElementById("usercode").value;
    
       //이메일 입력 유효성 확인
       if(realcode!=usercode){   
          alert("인증코드가 다릅니다");
          usercode.focus();
          return false;
       }else{
          var mail=$("#email").val()+"@"+$("#email2").val();
          //$("#memail").val(mail);
          $("#memail").attr("readonly",true);
          $("#memail").css('display','');
          $("#memail-input").css('display','none');
          
          alert("인증이 완료되었습니다");
          $("#check_pw_code").text("인증완료");
          $('#usercode').attr('disabled',true);
       }
    });//end of 인증번호 확인
});

