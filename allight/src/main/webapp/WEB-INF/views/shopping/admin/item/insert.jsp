<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Light</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
 $(function(){
		var cnt = 1; //첨부파일수를 저장하는 변수
			
		//추가버튼 클릭시  첨부파일 동적으로 (최대 5개)추가 
		$("#aBtn").click(function(){
			cnt++;
			if( cnt==6 ){
				alert("첨부파일은 최대 5개까지 가능합니다");
				cnt=5;
				return;
			}
			
			//추가할 대상 
			var tr = '<tr>';
			    tr+= 	'<th>첨부파일</th>';
			    tr+= 	'<th><input type="file" name="imgimages" id="imgimages'+cnt+'"/> </th>';
			    tr+= '</tr>';
			
			//원하는 위치에 붙인다
			$("#copy").before(tr);
			
		});
		
		//삭제버튼 클릭시   첨부파일 동적으로 삭제  (최소1개남기고)
		$("#dtn").click(function(){
			if( cnt==1 ){
				alert("첨부파일은 최소 1개가 있어야 합니다");
				return;
			}
			
			//제거
			var tr = $("#imgimages"+cnt).parents("tr");
			tr.remove();
			
			cnt--;
		});
		
		$("#sBtn").click(function(){
			//무결성검사하고.. 여러분이 하시고..
			
			$("#iteminsert").submit();  //폼객체.submit();
		});

 });
 
 $(function(){
	   $('#Page').children().each(function(){
	      if($(this).children('a').text()==${PINFO.nowPage}){
	         $(this).attr('class','active');
	      }
	   })
	})
 </script>

</head>
<body>

<div style="width:100%">
<h1>상품추가 페이지</h1>
<form id="iteminsert" action="../insert/corp.com" method="post" encType="multipart/form-data">
	<table class="table" border="1">
		<tbody>
		<tr>
			<th><label for="iname">상품명: </label></th>
			<th><input type="text" id="iname" name="iname" placeholder="상품명을 입력해주세요" style="width:300px;" required="required"></th>
		</tr>
		<tr>
			<th><label for="icategory">카테고리: </label></th>
			<th>
			<select id="icategory" name="icategory">
    			<option value="">선택</option>
	   			<option value="음식">음식</option>
	  			<option value="간식">간식</option>
				<option value="기타">기타</option>
			</select>
			</th>
		</tr>
		<tr>
			<th><label for="iprice">가격: </label></th>
			<th><input type="text" id="iprice" name="iprice" placeholder="가격을 입력해주세요" style="width:300px;" required="required"></hd>
		</tr>
		<tr>
			<th><label for="icorp">기업명: </label></th>
			<th><input type="text" id="icorp" name="icorp" value="<%= session.getAttribute("CONAME") %>" style="width:300px;" required="required" readonly ></td>
		</tr>
		<tr>
			<th><label for="istock">재고: </label></th>
			<th><input type="text" id="istock" name="istock" placeholder="재고를 입력해주세요" style="width:300px;" required="required"></td>
		</tr>
		<tr>
			<th><label for="idetail">상세설명: </label></th>
			<th><input type="text" id="idetail" name="idetail" style="width:300px;" placeholder="제품에 대한 설명을 입력해주세요" ></td>
		</tr>

		<tr>
			<th><label for="">대표이미지: </label></th>
			<th>
				<input type="file" name="imgimages" id="imgimages"/>
	 			<input type="button" value="추가" id="aBtn"/>
	 			<input type="button" value="삭제" id="dtn"/>
	 		</th>
		</tr>
		<tr id="copy">
			<th colspan="2" class="center">
				<input type="submit" value="상품등록">
				<input type="button" value="등록취소" onClick="history.back()">
			</th>
		</tr>

		</tbody>
	</table>
</form> 
</div>

</body>

</html>