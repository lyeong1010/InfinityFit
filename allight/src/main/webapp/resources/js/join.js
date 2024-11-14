$(function() {
	//아이디중복확인
	$("#idCheck").click(function() {
		var mid = $("#mid").val();
		var idreg=/^[a-z][a-zA-Z0-9]{6,12}$/;
		if(!(idreg.test(mid))){
			alert("아이디는 영문 및 숫자로만 입력해주세요");
			$("#mid").val("");
			$("#mid").focus();
			return false;
		}else{
		// JSON.parse(제이슨데이터); 수신
		// JSON.stringfy(자바스크립트오브젝트); 송신
			$.ajax({
				url : "idChk.com",
				type : "POST",
				dataType : "text",
				async : false,
				data :{ 
					mid : $("#mid").val()
				},
				success : function(data) {
					//alert("들어옴11111");
					//중복된 아이디가 있으면
					if (data == "fail") {
						alert("이미 등록된 아이디입니다.");
						$("#mid").val("");
						$("#mid").focus();
					} else {
						//중복된 아이디가 없으면
						//alert("mid"+mid)
						alert("사용 가능한 아이디입니다.");
						$('#idChecked').val("1");
					}
				},
				error : function(request, status, error) {
					alert("code:" + request.status+ "\n" + "message:"+ request.responseText+ "\n" + "error:" + error);
				}
			});
		}
	});//아이디 중복확인 종료
	
	//닉네임중복확인
	$("#nickCheck").click(function() {
		var mnick = $("#mnick").val();
		$.ajax({
			url : "nickChk.com",
			type : "POST",
			dataType : "text",
			async : false,
			data :{ 
				mnick : $("#mnick").val()
			},
			success : function(data) {
//				alert("들어옴11111");
				if (data == "fail") {
					alert("이미 등록된 닉네임입니다.");
					$("#mnick").val("");
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
	});//닉네임 중복확인 종료

	//이메일 인증
	$("#check_mail").click(function() {
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

		//이메일 중복확인 후 코드 전송
		$.ajax({
			url : "checkMail.com?email="+ memail,
			type : "POST",
			success : function(data) {   
				if (data == 1) {
					//이메일이 이미 존재할 때 -> 사용불가
					alert("이미 사용중인 메일입니다.")
					$("#memail").val("");
					$("#memail").focus();
				} else {
					//이메일이 존재하지 않을 때 -> 사용가능(코드값 data로 받기)
					alert("메일을 성공적으로 보냈습니다.메일함을 확인해주세요")
					$("#checkCODE").css('display','');
					$("#check_mail").html('재전송');
					realcode=data;
				}
			}, error : function() {
				console.log("실패");
			}
		});
	});
	
    //인증번호 확인
    $("#check_code").click(function() {
    
       var usercode = document.getElementById("usercode").value;
    
       //이메일 입력 유효성 확인
       if(realcode!=usercode){   
          alert("인증코드가 다릅니다");
          usercode.focus();
          return false;
       }else{
          var mail=$("#email").val()+"@"+$("#email2").val();
          $("#memail").attr("readonly",true);
          $("#memail").css('display','');
          $("#memail-input").css('display','none');
          
          alert("인증이 완료되었습니다");
          $("#check_code").text("인증완료");
          $("#usercode").attr('disabled',true);
       }
    });
    
	//전체선택 누르면 개별 체크박스 모두 선택되게
	$("#every_agree").click(function(){
		//alert("ㄷㅇㅇㄴ");
		var chk = $('#every_agree').is(":checked");
		if(chk){
			//$(".cboxes").prop('checked',true)
			$(".yaok2").prop("checked", true);
			$(".privacy1").prop("checked", true);
			$(".privacy2").prop("checked", true);
		}else{
			//$(".cboxes").prop('checked',false);
			$(".yaok2").prop("checked", false);
			$(".privacy1").prop("checked", false);
			$(".privacy2").prop("checked", false);
		}
	});
    
	//개별 체크박스누르면 전체선택 해제되게
	$(".yaok2").click(function(){//개별에서 class이름
		//alert("ㄷㅇㅇㄴ");
		$("#every_agree").prop("checked", false);//전체선택에서 id
	});
	$(".privacy1").click(function(){//개별에서 class이름
		//alert("ㄷㅇㅇㄴ");
		$("#every_agree").prop("checked", false);//전체선택에서 id
	});
	$(".privacy2").click(function(){//개별에서 class이름
		//alert("ㄷㅇㅇㄴ");
		$("#every_agree").prop("checked", false);//전체선택에서 id
	});
	//가입눌렀을때
	$("#join").submit(function(){
//		alert($('#checkCODE').css('display')=='none')
		
		var mtel=$("#mtel").val();
		var mtel1=$("#mtel1").val();
		var mtel2=$("#mtel2").val();
		var mtel=mtel+"-"+mtel1+"-"+mtel2
		mtel=document.getElementById("mtel").value;
		
		//아이디 입력여부 검사
		if($("#mid").val().length==0){
			alert("아이디를 입력해주세요")
			$("#mid").focus();
			return false;
		}
		//아이디 길이 체크
		if($("#mid").val().length<6 || $("#mid").val().length>12){
			alert("아이디는 6~12자리의 영문/숫자로 입력해주세요.")
			$("#mid").focus();
			return false;
		}
		//아이디 중복확인 여부 체크
		if($("#idChecked").val()=="0"){
			alert("아이디 중복확인을 해주세요.")
			return false;
		}
		
		//이름 입력 여부
		if($("#mname").val().length==0){
			alert("이름을 입력하지 않았습니다.")
			$("#mname").focus();
			return false;
		}
		
		//닉네임 중복확인여부 체크
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
		
		//생년월일 입력 여부
		if($("#mbirth").val().length==0){
			alert("생년월일을 입력하지 않았습니다.")
			$("#mbirth").focus();
			return false;
		}
		
		//이메일 입력여부
		if($("#memail").val().length==0){
			alert("이메일을 입력하지 않았습니다.")
			$("#memail").focus();
			return false;
		}
		
		//이메일인증여부
		if($("#checkCODE").css('display')=='none'){
			alert("이메일 인증을 완료해 주세요.")
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
		}//전화번호 입력 여부 끝
		
		//만 14세 이상 체크 안하면 못넘어가게
    	if(document.getElementById("mem_age_chk").checked==false){
    		alert("필수항목을 체크해 해주세요")
    		return false;
		}
    	
    	//이용약관 체크
		if(document.getElementById("yaok2").checked==false){
    		alert("이용약관에 동의해 해주세요")
    		return false;
		}
		
		//개인정보수집 체크
		if(document.getElementById("privacy1").checked==false){
			alert("개인정보수집에 동의해 해주세요.")
			return false;
		}
		
		//개인정보 제3자 체크
		if(document.getElementById("privacy2").checked==false){
			alert("개인정보 제3자에 동의해 해주세요.")
			return false;
		}
		
		/*
		//숫자만 입력하게 하는 정규식
		//나는 이거대신 input에 oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" 이거로 사용
		var tel1reg = /^[0-9]+$/;
		if(!(tel1reg.test(mtel1))){
			alert("핸드폰번호는 숫자로만 입력해주세요");
			$("#mtel1").val("");
			$("#mtel1").focus();
			return false;
		}
		var tel2reg = /^[0-9]+$/;
		if(!(tel2reg.test(mtel2))){
			alert("핸드폰번호는 숫자로만 입력해주세요");
			$("#mtel2").val("");
			$("#mtel2").focus();
			return false;
		}*/
	})
});