<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Responsive navbar-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
<div class="container px-lg-5">
	<a class="navbar-brand" href="#!">LOGO</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
			<li class="nav-item"><a class="nav-link active"
				aria-current="page" href="#!"><i class="fa-solid fa-house"></i>
					Home</a></li>

		</ul>
	</div>
</div>
</nav>
<!-- Header-->
<header class="py-5 ">
<div class="container  ">
	<div class="p-4 p-lg-5 bg-light rounded-3 text-center">
		<div class="m-4 m-lg-5">
			<h1 class="display-5 fw-bold">Trộn đề trắc nghiêm Online</h1>
			<p class="fs-4">Làm khó học sinh, giáo viên dễ chấm</p>
			<label for="fileInput" class="btn btn-primary btn-lg"> <i
				class="fa-solid fa-upload"></i> Chọn File
			</label> <input id="fileInput" type="file" style="display: none;"
				accept=".docx" onchange="handleFileSelect(event)">
			<p>Kéo thả hoặc nhấn chọn</p>
		</div>
	</div>
</div>
</header>