<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
<script 
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
	crossorigin="anonymous"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
</head>
<style>
.container{
	width: 960px;
}
</style>
<body>
	<div class="container">
		<%@include file="/WEB-INF/views/header.jsp"%>
		<section>
			<article>
				<form name="manPlanAdd" action="manPlanAdd.do" method="post">
					<div class="row">
						<div class="col-sm-6 offset-sm-3">
							<h2 align="center">요금제 관리</h2>
						</div>
					</div>
					<div class="row">
						<div class="offset-sm-3 col-sm-6" align="center">
							<a href="manPlanPage.do">추가</a> | <a href="manPlanUpdatePage.do">수정</a>
							| <a href="manPlanDelPage.do">삭제</a>
						</div>
					</div>
					<hr>
					<br>
					<br>
					<div class="row">
						<table class="table">
							<tr >
								<td><label for="plan_name" class="form-label" >요금제
										이름</label> <input type="text" name="plan_name" class="form-control"
									id="plan_name"></td>
								<td>
									<div class="form-check">
										<input type="radio" name="plan_type" id="plan_up"
											class="form-check-input" value="up요금제"> <label for="plan_up"
											class="form-check-label"> up 요금제 </label>
									</div>
									<div class="form-check">
										<input type="radio" name="plan_type" id="plan_vip"
											class="form-check-input" value="vip요금제"> <label for="plan_vip"
											class="form-check-label"> vip 요금제 </label>
									</div>
								</td>
								<td class="plan-option">
									<div  style="display: none;" class="vip-option">
										<select class="form-select" name="vip_floor">
											<option value="다이아">다이아</option>
											<option value="골드">골드</option>
											<option value="실버">실버</option>
										</select>
										<select class="form-select" name="plan_period">
											<option value="1">1일</option>
											<option value="3">3일</option>
											<option value="7">7일</option>
											<option value="30">30일</option>
										</select>
									</div>
									<div style="display: none;" class="up-option">
										<label for="up_count" class="form-label">업 횟수</label>
										<input type="text" name="up_count" class="form-control" id="up_count">
									</div>
								</td>
							</tr>
							<tr>
								<td style="width: 40%"><label for="plan_price" class="form-label">요금제
										가격</label> <input type="text" name="plan_price" class="form-control"
									id="plan_price">
								</td>
							</tr>
						</table>
							<div class="row">
								<div class="offset-sm-8 col-sm-4" align="right">
									<input type="submit" value="추가하기" class="btn btn-primary">
								</div>
							</div>
						<script>
						
								// VIP 요금제 라디오 버튼을 선택하면 "vip-option" 클래스를 가진 tr 요소를 보여줌
								$('#plan_vip').change(function() {
									$('.vip-option').show();
									$('.up-option').hide();
								});

								// UP 요금제 라디오 버튼을 선택하면 "vip-option" 클래스를 가진 tr 요소를 숨김
								$('#plan_up').change(function() {
									$('.vip-option').hide();
									$('.up-option').show();
								});
						</script>
					</div>
				</form>
			</article>
		</section>
		<%@include file="/WEB-INF/views/footer.jsp"%>
	</div>
</body>
</html>