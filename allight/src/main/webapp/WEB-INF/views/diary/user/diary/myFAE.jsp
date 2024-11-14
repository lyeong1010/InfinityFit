<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	// 음식 칼로리 추가
	$('#insertFoodBtn').click(function(){
		document.myForm.action='./insertMyFoodFrm.com';
		$('#myForm').submit();
	})
	
	// 운동 칼로리 추가
	$('#insertExerBtn').click(function(){
		document.myForm.action='./insertMyExerFrm.com';
		$('#myForm').submit();
	})
	
	// 뒤로가기
	$('.goBack').click(function(){
		document.location.href='./main.com';
	})
	
	// 몸무게 업데이트
	$('#dweight').change(function(){
		// 숫자만 입력하도록
		var regexp = /^[0-9]*\.?[0-9]*$/;
		var v = $(this).val();
		if( !regexp.test(v) ) {
			alert("숫자만 입력하세요");
			$(this).val(v.replace(regexp,''));
			return false;
		}
		
		if('${DATE}'!=''){
			//formData.append("ddate", "${DATE}");
			$.ajax({
	            type : 'post',
	            url : './updateDweight.com',
	            data : {"num":${num},
	            	"dweight":$('#dweight').val(),
	            	"ddate":"${DATE}"},
	            error: function(xhr, status, error){
	                alert('몸무게 저장에 실패했습니다.\n잠시후 다시 시도해주세요.');
	            },
	            success : function(data){
	                $('#num').val(Number(data));
	                if(${num}==0){
	        			location.href="./myFAE.com?num="+data;
	        		}
	            }
	        });
		}else{
			$.ajax({
	            type : 'post',
	            url : './updateDweight.com',
	            data : {"num":${num},
	            	"dweight":$('#dweight').val()},
	            error: function(xhr, status, error){
	                alert('몸무게 저장에 실패했습니다.\n잠시후 다시 시도해주세요.');
	            },
	            success : function(data){
	                $('#num').val(Number(data));
	                if(${num}==0){
	        			location.href="./myFAE.com?num="+data;
	        		}
	            }
	        });
		}
	})
	
	// 일기 업데이트
	$('#ddiary').change(function(){
		//var formData = new FormData();
		//formData.append("num", ${num});
		//formData.append("ddiary", $('#ddiary').val());
		if('${DATE}'!=''){
			//formData.append("ddate", "${DATE}");
			$.ajax({
	            type : 'post',
	            url : './updateDdiary.com',
	            data : {"num":${num},
	            	"ddiary":$('#ddiary').val(),
	            	"ddate":"${DATE}"},
	            error: function(xhr, status, error){
	                alert('일기 저장에 실패했습니다.\n잠시후 다시 시도해주세요.');
	            },
	            success : function(data){
	            	num = Number(data);
	            	if(${num}==0){
	        			location.href="./myFAE.com?num="+data;
	        		}
	            }
	        });
		}else {
			$.ajax({
	            type : 'post',
	            url : './updateDdiary.com',
	            data : {"num":${num},
	            	"ddiary":$('#ddiary').val()},
	            error: function(xhr, status, error){
	                alert('일기 저장에 실패했습니다.\n잠시후 다시 시도해주세요.');
	            },
	            success : function(data){
	            	num = Number(data);
	            	if(${num}==0){
	        			location.href="./myFAE.com?num="+data;
	        		}
	            }
	        });
		}
	})
	
	// 섭취칼로리 모두 지우기
	$('#deleteFoodAll').click(function(){
		if(${!empty FLIST}){
			if(confirm("정말 모두 삭제하시겠습니까?\n삭제하면 다시 복구할 수 없습니다.")){
				$.ajax({
		            type : 'post',
		            url : './myFoodDeleteAll.com',
		            data : {"dno":${num}},
		            error: function(xhr, status, error){
		                alert('음식 칼로리 삭제를 실패했습니다.\n잠시후 다시 시도해주세요.');
		            },
		            success : function(data){
		            	location.reload(true);
		            }
		        });
			}
		}else{
			alert('삭제할 음식 칼로리가 존재하지 않습니다.');
		}
	})
	
	// 소비칼로리 모두 지우기
	$('#deleteExerAll').click(function(){
		if(${!empty ELIST}){
			if(confirm("정말 모두 삭제하시겠습니까?\n삭제하면 다시 복구할 수 없습니다.")){
				$.ajax({
		            type : 'post',
		            url : './myExerDeleteAll.com',
		            data : {"dno":${num}},
		            error: function(xhr, status, error){
		                alert('운동 칼로리 삭제를 실패했습니다.\n잠시후 다시 시도해주세요.');
		            },
		            success : function(data){
		            	location.reload(true);
		            }
		        });
			}
		}else{
			alert('삭제할 운동 칼로리가 존재하지 않습니다.');
		}
	})
	
	// 이미지 삭제
	$('#deleteImgBtn').click(function(){
		if(${!empty DIARY.dimage}){
			if(confirm("사진을 삭제하시겠습니까?\n삭제하면 다시 복구할 수 없습니다.")){
				$.ajax({
		            type : 'post',
		            url : './myImgDelete.com',
		            data : {"dno":${num}},
		            error: function(xhr, status, error){
		                alert('사진 삭제를 실패했습니다.\n잠시후 다시 시도해주세요.');
		            },
		            success : function(data){
		            	location.reload(true);
		            }
		        });
			}
		}else {
			alert('삭제할 사진이 존재하지 않습니다.')
		}
	})
	
	// 다이어리 삭제
	$('#deleteAll').click(function(){
		
		if(${!empty DIARY.dno}){
			if(confirm("다이어리를 삭제하시겠습니까?\n삭제하면 다시 복구할 수 없습니다.")){
				document.location.href="./myDiaryDelete.com?dno="+${num};
			}
		}else{
			alert('삭제할 내용이 존재하지 않습니다.');
		}
	})
	
	// 게시판 등록 버튼 눌렀을 때
	$('#toBoard').click(function(){
		if('${num}'!='0' || '${num}'!=''){
			if($('#dweight').val()=="" && $('#ddiary').val()=="" && ${empty DIARY.dimage} && ${empty FLIST} && ${empty ELIST}){
				alert('등록할 내용이 없습니다.');
				return false;
			}else {
				if(confirm('게시글을 등록하시겠습니까?')){
					var title = "${DIARY.ddate} 기록";
					var content = "";
					var weight = $('#dweight').val();
					var breakfast = "";
					var lunch = "";
					var dinner = "";
					var snack = "";
					var exercise = "";
					var total = ${DIARY.dfoodcal - DIARY.dexercal};
					var diary = $('#ddiary').val();
					
					
					<c:forEach var="list" items="${FLIST}">
						<c:if test="${list.mftype == '아침'}">
							breakfast += "${list.mfname}(${list.mfamount}회분,${list.mfgram}g,${list.mftotalcal}kcal), "
						</c:if>
						<c:if test="${list.mftype == '점심'}">
							lunch += "${list.mfname}(${list.mfamount}회분,${list.mfgram}g,${list.mftotalcal}kcal), "
						</c:if>
						<c:if test="${list.mftype == '저녁'}">
							dinner += "${list.mfname}(${list.mfamount}회분,${list.mfgram}g,${list.mftotalcal}kcal), "
						</c:if>
						<c:if test="${list.mftype == '간식'}">
							snack += "${list.mfname}(${list.mfamount}회분,${list.mfgram}g,${list.mftotalcal}kcal), "
						</c:if>
					</c:forEach>
					
					<c:forEach var="list" items="${ELIST}">
						exercise += "${list.mename}(${list.metime}분,${list.metotalcal}kcal), "
					</c:forEach>
					
					if(weight != ""){
						content += " - 몸무게: " + weight + "kg";
					}
					if(breakfast != ""){
						content += "\n\n - 아침: " + breakfast.slice(0,-2);
					}
					if(lunch != ""){
						content += "\n - 점심: " + lunch.slice(0,-2);
					}
					if(dinner != ""){
						content += "\n - 저녁: " + dinner.slice(0,-2);
					}
					if(snack != ""){
						content += "\n - 간식: " + snack.slice(0,-2);
					}
					if(exercise != ""){
						content += "\n\n - 운동: " + exercise.slice(0,-2);
					}
					if(total.length != 0 && total!="0"){
						content += "\n\n - 오늘의 칼로리: " + numberWithCommas(total) + "kcal";
					}
					if(diary != ""){
						content += "\n\n - 일기: " + diary;
					}
					
					content = " " + content.trim();
					
				    $('#fcontent').val(content);
				    $('#ftitle').val(title);
				    
				    $('#boardWriteFrm').submit();
				}
			}
		}else {
			alert('등록할 내용이 없습니다.');
		}
	})
})
	
// 내 음식 칼로리 삭제(선택한 것)
function mfdel(dno,mfno){
	if(confirm("삭제하시겠습니까?\n삭제하면 다시 복구할 수 없습니다.")){
		document.location.href="./myFoodDelete.com?dno="+dno+"&mfno="+mfno;
	}
}

// 내 운동 칼로리 삭제(선택한 것)
function medel(dno,meno){
	if(confirm("삭제하시겠습니까?\n삭제하면 다시 복구할 수 없습니다.")){
		document.location.href="./myExerDelete.com?dno="+dno+"&meno="+meno;
	}
}

// 이미지 업데이트
function updateDimage(input){
	var form = $('#imgFrm')[0];
    var formData = new FormData(form);
    formData.append("dimageFile", $("#dimageFile")[0].files[0]);
	$.ajax({
        type : 'post',
        url : './updateDimage.com',
        data : formData,
        processData: false,
        contentType: false,
        error: function(xhr, status, error){
            alert('사진등록을 실패했습니다.\n잠시후 다시 시도해주세요.');
        },
        success : function(data){
        	alert('사진등록이 완료되었습니다.')
			location.href="./myFAE.com?num="+data+"#img";
			//location.reload(true);
        }
  	});
	
	$fdsf//va;er()
}

//천단위 콤마
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

</script>
</head>
<body>
<div id="wrap2">
	<div class="title2">다이어리
		<div class="f-right" style="margin:0 20px 0 0">
			<input type="button" value="뒤로 가기" class="goBack" style="background:white">
		</div>
	</div>
	<div class="diary-content">
		<div class="title3">몸무게
			<a>
				<c:if test="${DIARY.dweight eq 0}">
					<input type="text" name="dweight" id="dweight"/>kg
				</c:if>
				<c:if test="${DIARY.dweight ne 0}">
					<input type="text" name="dweight" id="dweight" value="${DIARY.dweight}"/>kg
				</c:if>
			</a>
		</div>
		
		<div class="title3">음식
			<div class="f-right">
				<input type="button" value="모두 지우기" id="deleteFoodAll" class="btn" style="background:white"/>
				<input type="button" value="추가" id="insertFoodBtn" class="btn4"/>
			</div>
		</div>
		<table id="myFood">
			<tr>
				<th width="10%">구분</th>
				<th width="45%">이름</th>
				<th width="15%">수량(회)</th>
				<th width="20%">칼로리</th>
				<th width="10%"></th>
			</tr>
			<c:forEach var="list" items="${FLIST}">
				<tr>
					<td class="center">${list.mftype}</td>
					<td class="center">${list.mfname}&nbsp;(${list.mfgram}g)</td>
					<td class="center">${list.mfamount}</td>
					<td class="right" style="padding:8px 65px 8px 8px"><fmt:formatNumber value="${list.mftotalcal}" pattern="#,###"/></td>
					<td class="center" onclick="mfdel(${list.dno},${list.mfno});" style="cursor:pointer;">X</td>
				</tr>
			</c:forEach>
			<c:if test="${empty FLIST}">
				<tr>
					<td colspan="5" class="center">섭취한 칼로리 정보가 없습니다.</td>
				</tr>
			</c:if>
		</table>
		<div class="right">총 섭취 칼로리: <a class="mTotal"><fmt:formatNumber value="${DIARY.dfoodcal}" pattern="#,###"/><c:if test="${empty DIARY.dfoodcal}">0</c:if></a>kcal</div>
		
		<div class="title3">운동
			<div class="f-right">
				<input type="button" value="모두 지우기" id="deleteExerAll" class="btn" style="background:white"/>
				<input type="button" value="추가" id="insertExerBtn" class="btn4"/>
			</div>
		</div>
		<table id="myExer">
			<tr>
				<th width="55%">이름</th>
				<th width="15%">시간(분)</th>
				<th width="20%">칼로리</th>
				<th width="10%"></th>
			</tr>
			<c:forEach var="list" items="${ELIST}">
				<tr>
					<td class="center">${list.mename}</td>
					<td class="center">${list.metime}</td>
					<td class="right" style="padding:8px 65px 8px 8px"><fmt:formatNumber value="${list.metotalcal}" pattern="#,###"/></td>
					<td class="center" onclick="medel(${list.dno},${list.meno});" style="cursor:pointer;">X</td>
				</tr>
			</c:forEach>
			<c:if test="${empty ELIST}">
				<tr>
					<td colspan="5" class="center">소비한 칼로리 정보가 없습니다.</td>
				</tr>
			</c:if>
		</table>
		<div class="right">총 소비 칼로리: <a class="fTotal"><fmt:formatNumber value="${DIARY.dexercal}" pattern="#,###"/><c:if test="${empty DIARY.dexercal}">0</c:if></a>kcal</div>
		
		<div class="title3">일기</div>
		<textarea name="ddiary" id="ddiary" placeholder="내용을 입력해주세요" style="width:100%; min-height:100px;">${DIARY.ddiary}</textarea>
		
		<div class="title3" id="img">변화사진
			<a style="color:gray">사진 등록은 한 장만 가능합니다.</a>
			<div class="f-right">
				<form id="imgFrm">
					<label for="dimageFile" class="btn4">사진등록</label>
					<input type="file" id="dimageFile" onchange="updateDimage(this);" class="hidden" name="dimageFile"/>
					<input type="hidden" name="num" value="${num}"/>
					<c:if test="${!empty DATE}">
						<input type="hidden" name="ddate" value="${DATE}"/>
					</c:if>
					<input type="button" value="삭제" id="deleteImgBtn" class="btn" style="background:white"/>
				</form>
			</div>
		</div>
		<div class="center">
			<img src="${DIARY.dimage}" id="imgPreview" class=""  style="height:auto;width:400px;margin:20px 0 0;">
		</div>
		<div class="center" style="margin:50px 0 0;">
			<input type="button" value="게시판등록" id="toBoard" class="btn4"/>
			<input type="button" value="모두 지우기" id="deleteAll" class="btn" style="background:white"/>
			<input type="button" value="뒤로가기" class="goBack" style="background:white"/>
		</div>
	</div>
</div>


<form method="post" id="myForm" name="myForm">
	<input type="hidden" name="num" value="${num}" id="num"/>
	<c:if test="${!empty DATE}">
		<input type="hidden" name="ddate" value="${DATE}"/>
	</c:if>
</form>

<form action="${pageContext.request.contextPath}/freeboard/diaryWriteFrm.com" method="post" id="boardWriteFrm">
	<!-- <input type="hidden" name="dimageFile" id="files"/> -->
	<input type="hidden" name="fcontent" id="fcontent"/>
	<input type="hidden" name="ftitle" id="ftitle"/>
	<input type="hidden" name="num" value="${num}"/>
	<c:if test="${!empty DIARY.dimage}">
		<input type="hidden" name="src" value="${DIARY.dimage}"/>
	</c:if>
</form>
</body>
</html>