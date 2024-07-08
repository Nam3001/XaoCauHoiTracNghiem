<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><dec:title default="Trang chủ" /></title>

<!-- css -->
<link href="<c:url value='/template/web/css/tuyChinhXaoCauHoi.css' />"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/template/web/css/style.css"
	rel="stylesheet">
<link rel="icon" type="image/x-icon"
	href="/template/web/assets/favicon.ico" />
<!-- Bootstrap icons-->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/template/web/css/styles.css' />">
<link rel="stylesheet"
	href="<c:url value='/template/web/css/tuyChinhXaoCauHoi.css' />">
<!-- Core theme CSS (includes Bootstrap)-->
</head>
<body class="p-7">
	<!-- header -->
	<%@ include file="/common/web/header.jsp"%>
	<!-- header -->

	<!-- body -->
	<dec:body />

	<!-- footer -->
	<%@ include file="/common/web/footer.jsp"%>
	<!-- footer -->


	<!-- Bootstrap core JS-->
	<script type="text/javascript"
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script type="text/javascript" src="/template/web/js/scripts.js"></script>
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.7.1.min.js"
		integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
		crossorigin="anonymous"></script>
</body>
</html>
