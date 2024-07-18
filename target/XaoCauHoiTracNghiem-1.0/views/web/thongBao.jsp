<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.xaocauhoitracnghiem.utils.EquationToMathML"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>! Thông báo</title>

</head>
<body>
	<main style="min-height: calc(100vh - 56px - 80px)">
	<div class="py-5 ">
		<c:if test="${ daTronDe == true }">
			<form id="download-form" action="download-exam" method="get"></form>
		</c:if>
		<div class="container">
			<div class="p-4 p-lg-5 bg-light rounded-3 text-center">
				<div class="m-4 m-lg-5">
					<h1 class="display-5 fw-bold">Đề đã trộn xong, đã tải về máy!</h1>
				</div>
			</div>
		</div>
	</div>
	</main>

	<script>
		var form = document.getElementById('download-form');
		if(form != null) {
			form.submit();
		}
	</script>
</body>
</html>