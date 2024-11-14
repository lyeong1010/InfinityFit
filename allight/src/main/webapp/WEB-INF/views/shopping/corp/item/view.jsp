<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
 $(function(){
	 	
		//수정하기 버튼 클릭시
		$("#mBtn").click(function(){
			//수정하기 폼 보여줘 요청
			$(location).attr("href","../update/corp.com?ino=${IDTO.ino}");
		});


		//삭제하기 버튼 클릭시
		$("#dBtn").click(function(){

			$("#imsiFrm").attr("action","../delete/corp.com");
			$("#imsiFrm").submit(); 
		});
 });

 
 </script>
</head>
<body>
<div style="width:100%">
<h1>상품 상세보기</h1>

	 
	<!-- 목록출력 -->
		<table class="table">
			<tbody>
				<tr>
					<th>상품번호</th>
					<th>상품이미지</th>
					<th>상품명</th>
		  			<th>카테고리</th>
					<th>가격</th>
					<th>기업명</th>
					<th>재고</th>

					<th>기업번호</th>
					<th>판매량</th>
				</tr>
				
				<tr class="dataRow">
					<td class="no">${IDTO.ino}</td>
					<td><img src="${IDTO2.saveName}" width="100" height="100" ></td>
					<td>${IDTO.iname}</td>
		  			<td>${IDTO.icategory}</td>
					<td>${IDTO.iprice}</td>
					<td>${IDTO.icorp}</td>
					<td>${IDTO.istock}</td>

					<td>${IDTO.cono}</td>
					<td>${IDTO.isellamount}</td>
				</tr>
					
					
					<tr>
					<th colspan="10">상세내용</th>
					</tr>
					<tr>
					<th colspan="10">${IDTO.idetail}</th>
					</tr>
				<th colspan="10" class="center">
						<input type="button" value="리스트보기" onClick="history.back()">
					<input type="button" id="mBtn" value="상품수정">
					<input type="button" id="dBtn" value="상품삭제">
				</th>
				

			</tbody>
							
				
					
			
				
				
		</table>

	
	<%-- 삭제를 위한 임시폼 --%>
	<form id="imsiFrm"	method="POST">
		<input type="hidden" name="no" value="${IDTO.ino}"/>
		<input type="hidden" name="pw" id="imsipw"/>
	</form>

</div>
</body>
</html>