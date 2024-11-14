<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

</head>

<body>
	<div class="container">
		<!-- 검색창  -->
		<div class="boardContent">
			<div class="boardContent-buttons">
				<input type="button" value="목록" class="btn">
				<input type="button" value="수정" class="btn" >
				<input type="button" value="삭제" class="btn">
			</div>
			
			<!-- 테이블  -->
			<table>
				<tr>
					<td class="board-title">제목(수정필요)</td>
				</tr>
				<tr>
					<td class="board-info"><a class="board-info-nick">닉네임(수정필요)</a>
						| <a class="board-info-others">조회</a> 
						<a style="color: #ff5656; font-size: 1.4rem;">조회수(수정필요)</a> 
						<a class="board-info-others"> | 업로드날짜(수정필요)</a>
					</td>
				</tr>
				<tr>
					<td class="board-content"><div class="board-content-div">내용(수정필요)</div></td>
				</tr>
			</table>
			
		
			<!-- 댓글  -->
			<div class="boardContent-Comment">
				<div class="boardContent-Comment-input">
					<form action="#" method="post">
						<input type="textarea" class="input" placeholder="댓글을 입력하세요" /> 
						<input type="submit" class="button" value="등록" />
					</form>
				</div>
		
				<div class="boardContent-Comment-comment"
					style="padding: 10px; font-size: 1.5rem;">
					댓글(<a style="color: #ff5656;">댓글수(수정필요)</a>)
				</div>
		
				<div class="boardContent-Comment-Table">
					<table width="100%" style="border-top: 1px solid gray;">
						<!-- for문으로 댓글 가져오기 -->
						<tr>
							<td colspan="100%" class="board-comment-info"><a
								class="board-info-nick">닉네임(수정필요)</a>&nbsp;&nbsp; <a
								class="board-info-others">업데이트날짜(수정필요)</a></td>
						</tr>
						<tr>
							<td width="80%">댓글내용(수정필요)</td>
							<td style="padding: 0; text-align: center;">
								<a href="#"> 
									<img class="likeandhate" src="#" />
								</a>
								<a class="aNone">좋아요수(수정필요)</a>
							</td>
							<td style="padding: 0; text-align: center;">
								<a href="#" style="color: #ff5656;">삭제</a>
							</td>
						</tr>
						<!-- for문 끝~ -->
		
					</table>
					<div class="center">
						<ul class="pagination">
							<li>
								<a href="#">«</a>
							</li>
							<!-- 현재 페이지일때 active -->
							<li class="active"> 
								<a href="#">1</a>
							</li>
							<li>
								<a href="#">2</a>
							</li>
							<li>
								<a href="#">3</a>
							</li>
							<li>
								<a href="#">4</a>
							</li>
							<li>
								<a href="#">5</a>
							</li>
							<li>
								<a href="#">»</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>