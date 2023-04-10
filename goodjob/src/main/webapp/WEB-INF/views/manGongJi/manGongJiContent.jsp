<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<%@include file="/WEB-INF/views/header.jsp"%>
		<section>
			<article>
			<table border="1" width="550" height="600">
				<thead>
					<tr>
						<th>글번호</th>
						<td>${dto.idx }</td>
						<th>작성일</th>
						<td>${dto.writedate}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="3">${dto.subject}</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4">
						${dto.content }
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
						<div style="text-align: center;">
						<input type="button" value="수정하기" onclick="javascript:location.href='manGongjiUpdateForm.do?idx=${dto.idx}'">
						<input type="button" value="삭제하기" onclick="javascript:location.href='manGongjiDel.do?idx=${dto.idx}'">
						</div>
						</td>
					</tr>
				</tfoot>
			</table>
			</article>
		</section>
	<%@include file="/WEB-INF/views/footer.jsp"%>
</div>
</body>
</html>












