<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title></title>
	<!-- jQuery CDN -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	
	<script>
	$(function(){
		//결제버튼 누르면
    	$("#paybtn").click(function(){
			//주문동의 체크박스 체크 안하면 못넘어가게
	    	if(document.getElementById("aggchk").checked==false){
	    		alert("주문 동의를 하셔야 주문하실 수 있습니다.")
	    		return false;
    		}
			//결제부분 시작
			//alert(name)
			var name="${name}"
			var amount = '${olist.osum}';
			var bemail = '${sessionScope.MEMAIL}';
			var bname ='${olist.oreceiver}';
			var btel = '${olist.otel}';
			var baddr = '${olist.oaddress1}';
			var bpc = '${olist.oaddno}';
			var payment = $("input[name=opayment]:checked").val();
			
			//alert(name+"//"+amount+"//"+bemail+"//"+bname+"//"+btel+"//"+baddr+"//"+bpc+"//"+payment)
			//결제방법 선택
			//alert($("input[name=opayment]:checked").val())
			if(payment=="신용카드"){
				inicis(name,amount,bemail,bname,btel,baddr,bpc);
			}else if(payment=="실시간 계좌이체"){
				danal_trans(name,amount,bemail,bname,btel,baddr,bpc);
			}else if(payment=="가상계좌"){
				danal_vbank(name,amount,bemail,bname,btel,baddr,bpc);
			}else if(payment=="휴대폰 소액결제"){
				danal_phone(name,amount,bemail,bname,btel,baddr,bpc);
			}else if(payment=="카카오페이"){
				kakaopay(name,amount,bemail,bname,btel,baddr,bpc);
			}else{
				alert("결제방식을 선택해주세요");
				return false;
			}
    	});
	});
	</script>
	
	<!-- 여기서부터는 결제방식에 따른 각각의 코드 -->	
	<!-- 아임포트 신용카드 간편결제 구동 코드 -->
	<script>
	function inicis(name,amount,bemail,bname,btel,baddr,bpc) {
		var IMP = window.IMP;
		IMP.init('imp33827871'); 
        IMP.request_pay({
            pg: 'html5_inicis',
            pay_method: 'card',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: name,//상품이름
            amount: amount, //가격
            buyer_email: bemail,
            buyer_name: bname,
            buyer_tel: btel,
            buyer_addr: baddr,
            buyer_postcode: bpc,
            m_redirect_url: '${pageContext.request.contextPath}/mypage/paySuccess.com'
            /*  
		                모바일 결제시,
		                결제가 끝나고 랜딩되는 URL을 지정 
               (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐) 
            */
        }, function (rsp) {
            console.log(rsp);
            if (rsp.success) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                msg += '\n결제 금액 : ' + rsp.paid_amount;
                msg += '\n카드 승인번호 : ' + rsp.apply_num;
                location.href="${pageContext.request.contextPath}/mypage/paySuccess.com";
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '\n에러내용 : ' + rsp.error_msg;
            	alert(msg);
                location.href="${pageContext.request.contextPath}/mypage/cart.com";
            }
        });
	}
	</script>
	
	<!-- 실시간계좌이체, 가상계좌는 테스트결제 지원되지 않음  -->
	<!-- 아임포트 다날 실시간 계좌이체 구현 코드 -->
	<script>
	function danal_trans (name,amount,bemail,bname,btel,baddr,bpc) {
		var IMP = window.IMP;
		
		IMP.init('imp33827871'); 
        IMP.request_pay({
            pg: 'danal',
            pay_method: 'trans',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: name,
            amount: amount, 
            buyer_email: bemail,
            buyer_name: bname,
            buyer_tel: btel,
            buyer_addr: baddr,
            buyer_postcode: bpc,
            //m_redirect_url: '${pageContext.request.contextPath}/paySuccess.com'
        }, function (rsp) {
            console.log(rsp);
            if (rsp.success) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
                location.href="${pageContext.request.contextPath}/paySuccess.com?type=timeBankTransfer";
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '\n에러내용 : ' + rsp.error_msg;
                alert(msg)
                location.href="${pageContext.request.contextPath}/mypage/cart.com";
            }
        });
    }
	</script>
	
	<!-- 아임포트 다날 가상계좌 구현 코드 -->
	<script>
	function danal_vbank (name,amount,bemail,bname,btel,baddr,bpc) {
		var IMP = window.IMP;
        IMP.init('imp33827871'); 
        IMP.request_pay({
            pg: 'danal',
            pay_method: 'vbank',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: name,
            amount: amount, 
            buyer_email: bemail,
            buyer_name: bname,
            buyer_tel: btel,
            buyer_addr: baddr,
            buyer_postcode: bpc,
            //m_redirect_url: '${pageContext.request.contextPath}/paySuccess.com'
        }, function (rsp) {
            console.log(rsp);
            if (rsp.success) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
                location.href="${pageContext.request.contextPath}/paySuccess.com?type=vbank";
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '\n에러내용 : ' + rsp.error_msg;
                alert(msg)
                location.href="${pageContext.request.contextPath}/mypage/cart.com";
            }
        });
    }
	</script>
	
	<!-- 아임포트 다날 휴대폰 소액결제 구현 코드 -->
	<script>
	function danal_phone (name,amount,bemail,bname,btel,baddr,bpc) {
		var IMP = window.IMP;
        IMP.init('imp33827871'); 
        IMP.request_pay({
            pg: 'danal',
            pay_method: 'phone',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: name,
            amount: amount, 
            buyer_email: bemail,
            buyer_name: bname,
            buyer_tel: btel,
            buyer_addr: baddr,
            buyer_postcode: bpc,
            //m_redirect_url: '${pageContext.request.contextPath}/paySuccess.com'
        }, function (rsp) {
            console.log(rsp);
            if (rsp.success) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
                location.href="${pageContext.request.contextPath}/paySuccess.com?type=phone";
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '\n에러내용 : ' + rsp.error_msg;
                alert(msg)
                location.href="${pageContext.request.contextPath}/mypage/cart.com";
            }
        });
    }
	</script>
	
	<!-- 아임포트 카카오페이 카드 결제 구현 -->
	<script>
	function kakaopay (name,amount,bemail,bname,btel,baddr,bpc) {
        var IMP = window.IMP; // 생략가능
        IMP.init('imp33827871'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
        var msg;
        
        IMP.request_pay({
            pg : 'kakaopay',
            pay_method : 'card',
            merchant_uid : 'merchant_' + new Date().getTime(),
            name: name,
            amount: amount, 
            buyer_email: bemail,
            buyer_name: bname,
            buyer_tel: btel,
            buyer_addr: baddr,
            buyer_postcode: bpc,
            //m_redirect_url : 'http://www.naver.com'
        }, function(rsp) {
            if ( rsp.success ) {
                //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
                jQuery.ajax({
                    url: "/payments/complete", //cross-domain error가 발생하지 않도록 주의해주세요
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        imp_uid : rsp.imp_uid
                        //기타 필요한 데이터가 있으면 추가 전달
                    }
                }).done(function(data) {
                    //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                    if ( everythings_fine ) {
                        msg = '결제가 완료되었습니다.';
                        msg += '\n고유ID : ' + rsp.imp_uid;
                        msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                        msg += '\결제 금액 : ' + rsp.paid_amount;
                        msg += '카드 승인번호 : ' + rsp.apply_num;
                        alert(msg);
                    } else {
                        //[3] 아직 제대로 결제가 되지 않았습니다.
                        //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                    }
                });
                //성공시 이동할 페이지
               location.href="${pageContext.request.contextPath}/paySuccess.com?type=kakaopay&address=${maddress}&requests=${requests}";
            } else {
                msg = '결제에 실패하였습니다.';
                msg += '\n에러내용 : ' + rsp.error_msg;
                alert(msg);
                location.href="${pageContext.request.contextPath}/mypage/cart.com";
            }
        });
    }
	</script> 
	
</head>
<body>
<%-- ${name}
${olist} --%>
	<h3></h3>
	<!-- 결제수단  -->
	<div>
		<h3>결제수단</h3>
		<input type="radio" name="opayment" class="pmt" id="check_creditCard" value="신용카드">신용카드
		<input type="radio" name="opayment" class="pmt" id="check_real-timeBankTransfer" type="button" value="실시간 계좌이체">실시간 계좌이체
		<input type="radio" name="opayment" class="pmt" id="check_vbank" type="button" value="가상계좌">가상계좌
		<input type="radio" name="opayment" class="pmt" id="check_phone" type="button" value="휴대폰 소액결제">휴대폰 소액결제	
		<!-- <input type="radio" name="paym" id="check_payco" type="button" value="6">페이코 -->
		<input type="radio" name="opayment" class="pmt" id="check_kakaopay" type="button" style="margin-top: 10px;" value="카카오페이"><img src="${pageContext.request.contextPath}/resources/img/payment_text_small.png">
	</div>
	<!--   주문동의 영역 시작 -->
	<div>
		<h3>주문동의 </h3>
		<label>
			<input type="checkbox" id="aggchk" value="Y">
		</label><em>주문할 상품의 상품, 가격, 배송정보 등을 최종 확인하였으며, 개인정보 제3자 제공 동의에 관한 내용을 모두 이해하였으며, 이에 동의합니다.</em>
			<div></div><div></div><hr/>
	</div>
	<div></div>
	<div></div>
	<input type="button" id="paybtn" value="결제하기">
</body>
</html>