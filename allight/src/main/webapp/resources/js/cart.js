// 천단위 콤마
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
$(function(){
	//모두체크
	$("#allCheck").click(function(){
		var check = $('#allCheck').prop("checked");
		if(check){
			//alert($('.chkBox').length);
			//체크박스 선택시 결제금액 변경
			$(".chkBox").prop("checked", true);
			var chk = 0;
			$(".chkBox:checked").each(function(){
				var temp = $(this).closest('td').next('td').next('td').next('td').next('td').next('td')
				chk += Number(temp.text().split("원")[0].replace(',',''));
			})
			$('.sum').children().text(numberWithCommas(chk)+"원");
		} else{
			//alert($('.chkBox').length);
			$(".chkBox").prop("checked", false);
			//체크박스 선택시 결제금액 0원으로 변경
			$('.sum').children().text("0원");
		}
	});//end 모두체크
	
	//개별 체크박스누르면 전체선택 해제되게
	$(".chkBox").click(function(){//개별에서 class이름
		//alert("ㄷㅇㅇㄴ");
		$("#allCheck").prop("checked", false);//전체선택에서 id
		
	});
	
	//체크된 item만 합계내기
	$(".chkBox").change(function(){
		var chk = 0;
		$(".chkBox:checked").each(function(){
			var temp = $(this).closest('td').next('td').next('td').next('td').next('td').next('td')
			chk += Number(temp.text().split("원")[0].replace(',',''));
			//alert(chk)
		})
		$('.sum').children().text(numberWithCommas(chk)+"원");
	})
	
	//선택삭제
	$(".selectDelete_btn").click(function(){
//		var confirm_val = confirm("정말 삭제하시겠습니까?");
//		alert(confirm_val)
		if(confirm("정말 삭제하시겠습니까?")){
			var checkArr = new Array();
			$("input[class='chkBox']:checked").each(function(){
				checkArr.push($(this).attr("data-cartNo"));
			});//종료 input선택자

			$.ajax({
				url : "deleteCart.com",
				type : "get",
				dataType: "text",
				data : {"chkBox" : checkArr},
				success : function(result){
					if(result == "success"){
						alert("삭제되었습니다")
						location.href = "./cart.com";	
					} else{
						alert(result);
						alert("삭제 실패");
					}
				},
				error: function(request,status,error){
					alert("????"+"code = "+ request.status + " message = " + request.responseText + " error = " + error)
				}
			});
		}
	});//end of 선택삭제
	
	// 수량 선택시 총 상품가격 변동
	// - 버튼 눌렀을때
	$('.minus').click(function(){
		$target = $(event.target);
		if ($target.next('input').val() != 1 ){//수량을 1보다 적게 선택할수 없게
			var $total = $target.next('input').val($target.next('input').val()-1);
		}
		// minus button에서 합계 caprice로 찾아가기
		var minus = $target.closest('td').next('td').children('div').children('p');
		//alert(minus.text())
		var val = Number($target.next('input').val());	//수량 input값 변수선언
		var price = $target.closest('td').prev('td').children('div').children('.price');
		price = Number(price.text().split("원")[0].replace(',',''));//변수 price reformat
		minus.text(numberWithCommas(val*price)+"원");
		
		var chk = 0;
		$(".chkBox:checked").each(function(){
			var temp = $(this).closest('td').next('td').next('td').next('td').next('td').next('td')
			chk += Number(temp.text().split("원")[0].replace(',',''));
			//alert("요기?"+chk)
		})
		$('.sum').children().text(numberWithCommas(chk)+"원");
		
		//-버튼 클릭시 변경된 상품수량 db에 저장하기
		var caamount = $(this).next('.numBox').val()
		var caprice = $(this).closest('td').next('td').text()
		caprice = Number(caprice.split("원")[0].replace(',',''));
		var cano = $(this).closest('tr').prev('#cano').val()
		$.ajax({
			url : "updateAmt.com",
			type : "get",
			dataType: "text",
			data : {
				"caamount" : caamount,
				"caprice" : caprice,
				"cano" : cano
			},
			success : function(result){
			},
			error: function(request,status,error){
				alert("????"+"code = "+ request.status + " message = " + request.responseText + " error = " + error)
			}
		});
	})
	
	// + 버튼 눌렀을때
	$('.plus').click(function(){
		$target = $(event.target);
		var $total = $target.prev('input').val(Number($target.prev('input').val())+1);
		
		// plus button에서 합계 caprice로 찾아가기
		var plus = $target.closest('td').next('td').children('div').children('p');
		var val = Number($target.prev('input').val());
		var price = $target.closest('td').prev('td').children('div').children('p');
		price = Number(price.text().split("원")[0].replace(',',''));
		//alert(val*price)
		plus.text(numberWithCommas(val*price)+"원");
		
		var chk = 0;
		$(".chkBox:checked").each(function(){
			var temp = $(this).closest('td').next('td').next('td').next('td').next('td').next('td')
			chk += Number(temp.text().split("원")[0].replace(',',''));
			//alert(chk)
		})
		$('.sum').children().text(numberWithCommas(chk)+"원");
		
		//+버튼 클릭시 변경된 상품수량 db에 저장하기
		var caamount = $(this).prev('.numBox').val()
		var caprice = $(this).closest('td').next('td').text()
		caprice = Number(caprice.split("원")[0].replace(',',''));
		var cano = $(this).closest('tr').prev('#cano').val()
		$.ajax({
			url : "updateAmt.com",
			type : "get",
			dataType: "text",
			data : {
				"caamount" : caamount,
				"caprice" : caprice,
				"cano" : cano
			},
			success : function(result){
				if(result == "1"){
					//if(confirm("#caamount")==1){
					//	alert("최소 수량은 1입니다.");
					//}
				} else{
				}
			},
			error: function(request,status,error){
				alert("????"+"code = "+ request.status + " message = " + request.responseText + " error = " + error)
			}
		});
	})
	
	//결제하기 버튼 눌렀을때
	$('#pay').click(function(){
		//$target = $(event.target);
		//alert("결제")
		var canoList = [];
		$(".chkBox:checked").each(function(){
			var cano = $(this).closest('tr').prev('input').val()
			//alert(temp)
			canoList.push(cano);
		})
		if(canoList.length!=0){
			$('#canoList').val(canoList);
			$('#cart').submit();
		}else{
			alert("결제할 상품을 선택해 주세요")
		}
	})
}); // end of function()