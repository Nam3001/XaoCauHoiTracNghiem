<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
	<!-- Header-->
	<header class="py-5 ">
	<div class="container  ">
		<div class="p-4 p-lg-5 bg-light rounded-3 text-center">
			<div class="m-4 m-lg-5">
				<h1 class="display-5 fw-bold">Trộn đề trắc nghiêm Online</h1>
				<p class="fs-4">Làm khó học sinh, giáo viên dễ chấm</p>
				<form action="upload-de-goc" method="post"
					enctype="multipart/form-data">
					<label for="de-goc" class="btn btn-primary btn-lg"> <i
						class="fa-solid fa-upload"></i> Chọn File
					</label> <input id="de-goc" name="de-goc" type="file"
						style="display: none;" accept=".docx"
						onchange="handleFileSelect(event)"> <input type="submit">
				</form>
				<p>Kéo thả hoặc nhấn chọn</p>
			</div>
		</div>
	</div>
	</header>
	<div class="p-4 p-lg-5 bg-light rounded-3  ">
		<div>
			<h3 class="text-center">Lưu ý khi tải File lên</h3>
			<div class="p-6">
				<p>• Câu hỏi phải bắt đầu bằng chữ "Câu", ví dụ: Câu 1, Câu
					2,.... (Hoặc chữ "Question", ví dụ: Question 1, Question 2,...).</p>
				<p>• Phải xuống dòng (gỗ Enter) trước khi gõ các đáp án.</p>
				<p>• Các đáp án bắt buộc phải được bắt đầu bằng các từ "A", "B",
					"C.", "D.".</p>
				<p>• Mỗi đáp án nên nằm trên một dòng (không bắt buộc, nhưng sẽ
					nhận dạng chính xác hơn)</p>
				<p>• Các câu hỏi và đáp án có Hình ảnh thì Hình ảnh cần để ở chế
					độ Inline With Text (để sau này đề hoán vị, hình ảnh không bị nhảy
					lung tung)</p>
			</div>
		</div>
	</div>
	<!-- Page Content-->
	<div class="pt-4">
		<div class="container px-lg-5">
			<!-- Page Features-->
			<div class="row gx-lg-5 pt-lg-0 ">

				<div class="col-lg-6 col-xxl-4 mb-5">
					<div class="card bg-light border-0 h-100">
						<div class="card-body text-center p-4 p-lg-5 pt-0 ">
							<div
								class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4">
								<i class="bi bi-cloud-download"></i>
							</div>
							<h2 class="fs-4 fw-bold">Tải xuống nhanh chống</h2>
							<p class="mb-0">tiện lợi, nhanh gọn, dễ dàng sử dụng</p>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-xxl-4 mb-5">
					<div class="card bg-light border-0 h-100">
						<div class="card-body text-center p-4 p-lg-5 pt-0 ">
							<div
								class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4">
								<i class="fa-solid fa-bolt"></i>
							</div>
							<h2 class="fs-4 fw-bold">Xử lý Online</h2>
							<p class="mb-0">tiện lợi, nhanh gọn, dễ dàng sử dụng</p>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-xxl-4 mb-5">
					<div class="card bg-light border-0 h-100">
						<div class="card-body text-center p-4 p-lg-5 pt-0 ">
							<div
								class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4">
								<i class="fa-solid fa-bolt"></i>
							</div>
							<h2 class="fs-4 fw-bold">An toàn</h2>
							<p class="mb-0">tiện lợi, nhanh gọn, dễ dàng sử dụng</p>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>