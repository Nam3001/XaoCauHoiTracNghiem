<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<main class="container-fluid d-lg-flex flex-row m-0 p-0 ">
	<div id="sidebar" class="bg-dark text-white col-12 p-3">
		<div class="d-flex justify-content-center mb-3">
			<button class="btn btn-light w-50">
				<i class="fa-solid fa-upload"></i> <span>Đề cần trộn</span>
			</button>
		</div>
		<div class="section mt-3">
			<h2>Thông tin file gốc</h2>
			<ul>
				<li>Tên File: <span>đề toán.docx</span></li>
				<li>Số lượng câu hỏi: <span>50</span></li>
				<li>Số nhóm: <span>1</span></li>
			</ul>

		</div>
		<form method="post">
			<div class="section mt-3">
				<h2>Tùy chọn trộn đề</h2>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label for="so-luong-de" class="col-form-label">Số lượng
							đề:</label>
					</div>
					<div class="col-3">
						<input type="number" name="so-luong-de" id="so-luong-de"
							class="form-control" aria-describedby="passwordHelpInline"
							value="4">
					</div>
				</div>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label for="sap-xep-dap-an-ngan" class="col-form-label">Sắp
							xếp đáp án ngắn: </label>
					</div>
					<div class="col-3">
						<select class="form-select" id="sap-xep-dap-an-ngan"
							name="sap-xep-dap-an-ngan">
							<option value="Tab">Tab</option>
							<option value="FourRows">4 hàng</option>
						</select>
					</div>
				</div>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label for="kieu-cau" class="col-form-label">Kiểu Câu 1.
							hay Câu 1: </label>
					</div>
					<div class="col-4">
						<select class="form-select" id="kieu-cau" name="kieu-cau">
							<option value=":">Câu 1:</option>
							<option value=".">Câu 1.</option>
						</select>
					</div>
				</div>

			</div>
			<div class="section mt-3">
				<h2>Thông tin chung</h2>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label class="form-control-label" for="ten-file">Sở/Phòng
							GD:</label>
					</div>
					<div class="col-8">
						<input class="form-control" type="text" id="ten-file"
							name="ten-file" value="Example.pdf" readonly>
					</div>
				</div>

				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label class="form-control-label" for="ten-file">Trường:</label>
					</div>
					<div class="col-8">
						<input class="form-control" type="text" id="ten-file"
							name="ten-file" value="Example.pdf" readonly>
					</div>
				</div>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label class="form-control-label" for="ten-file">Kỳ kiểm
							tra:</label>
					</div>
					<div class="col-8">
						<input class="form-control" type="text" id="ten-file"
							name="ten-file" value="Example.pdf" readonly>
					</div>
				</div>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label class="form-control-label" for="ten-file">Môn:</label>
					</div>
					<div class="col-8">
						<input class="form-control" type="text" id="ten-file"
							name="ten-file" value="Example.pdf" readonly>
					</div>
				</div>
				<div class="py-1 row g-3 align-items-center justify-content-between">
					<div class="col-auto">
						<label class="form-control-label" for="ten-file">Thời
							gian:</label>
					</div>
					<div class="col-8">
						<input class="form-control" type="text" id="ten-file"
							name="ten-file" value="Example.pdf" readonly>
					</div>
				</div>

			</div>
			<div class="d-flex justify-content-center mt-3">
				<button class="btn btn-light w-50 ">
					<i class="fa-solid fa-download"></i> <span>Trộn đề</span>
				</button>
			</div>
		</form>
	</div>

	<div id="content" class="col-12">
		<div class="bg-primary"></div>
	</div>
	</main>
</body>
</html>