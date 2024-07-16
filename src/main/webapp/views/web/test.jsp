<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.xaocauhoitracnghiem.utils.EquationToMathML"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trộn đề</title>

</head>
<body>
	<main style="min-height: calc(100vh - 56px - 56px)">
	<div class="py-5 ">
		<div class="container">
			<div class="p-4 p-lg-5 bg-light rounded-3 text-center">
				<div class="m-4 m-lg-5">
					<h1 class="display-5 fw-bold">Đề đã trộn xong</h1>
					<p class="fs-4">Hãy tải đề xuống để xem!</p>

					<!--  <form id="form-upload-de-goc" action="upload-de-goc" method="post"
						enctype="multipart/form-data">
						<label for="de-goc" class="btn btn-primary btn-lg"> <i
							class="fa-solid fa-upload"></i> Chọn File
						</label> <input id="de-goc" name="de-goc" type="file"
							style="display: none;" accept=".docx">
					</form>
					-->
					<form action="download-exam" method="get">
						<button class="btn btn-primary">Tải xuống</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	</main>

	<script></script>
</body>
</html>