<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.xaocauhoitracnghiem.model.AnswerModel" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang chủ</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous">
    <link rel="stylesheet"
          href="<c:url value='/template/web/css/styles.css?v=1' />">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>
<main style="min-height: calc(100vh - 56px - 80px)">
    <div class="py-5 ">
        <div class="container">
            <div class="p-4 p-lg-5 bg-light rounded-3 text-center">
                <div class="m-4 m-lg-5">
                    <h1 class="display-5 fw-bold">Trộn đề trắc nghiệm Online</h1>
                    <p class="fs-4">Tiện ích, nhanh gọn và đơn giản</p>

                    <form id="form-upload-de-goc" action="upload-de-goc" method="post"
                          enctype="multipart/form-data">
                        <label for="de-goc" class="btn btn-primary btn-lg"> <i
                                class="fa-solid fa-upload"></i> Chọn File
                        </label> <input id="de-goc" name="de-goc" type="file"
                                        style="display: none;" accept=".docx">
                    </form>
                    <p class="mt-2">Kéo thả hoặc nhấn chọn</p>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="p-4 p-lg-5 bg-light rounded-3">
            <h3 class="text-center">Hướng dẫn soạn đề gốc</h3>
            <div class="p-3 p-lg-6">
                <p>• Câu hỏi phải bắt đầu bằng chữ “Câu” và phải có kí tự chấm “.” hoặc hai
                    chấm “:” ví dụ: Câu 1:, Câu 2., Câu 3:, ... (hoặc bằng chữ “Question”, ví dụ:
                    Question 1., Question 2:, ...)</p>
                <p>• Phải <b>xuống dòng</b> (gõ <b>Enter</b>) trước khi gõ các đáp án.</p>
                <p>• Các đáp án bắt buộc phải được bắt đầu bằng các từ <b>"A.", "B.",
                    "C.", "D." ...</b></p>
                <p>• <b>Mỗi đáp án</b> phải nên nằm trên <b>một dòng</b> (bắt buộc)</p>
                <p>• Các câu hỏi và đáp án có Hình ảnh thì Hình ảnh cần để ở chế
                    độ Inline With Text (để sau này đề hoán vị, hình ảnh không bị nhảy
                    lung tung)</p>
                <p>• Đáp án đúng phải gạch chân</p>
                <p>• hiện tại chúng tôi chỉ hỗ trợ hình ảnh và biểu thức toán học, nếu câu hỏi có table, chart... thì bạn có thể chụp ảnh table và chart... đó và thêm vào đề!</p>
            </div>
        </div>
    </div>
    <div class="container mt-5">
        <div class="p-4 p-lg-5 bg-light rounded-3">
            <h3 class="text-center">Ngoài ra chúng tôi còn hỗ trợ gom nhóm</h3>
            <div class="p-3 p-lg-6">
                <p><b>• &lt;g0&gt;</b></p>
                <p>Nhóm câu hỏi không cần trộn (Ví dụ thường áp dụng cho các phần thi nghe môn tiếng anh, hay các phần câu hỏi tự luận)</p>
                <p><b>• &lt;g1&gt;</b></p>
                <p>Nhóm câu hỏi chỉ cần hoán vị các câu hỏi</p>
                <p><b>• &lt;g2&gt;</b></p>
                <p>Nhóm câu hỏi chỉ cần hoán vị các đáp án</p>
                <p><b>• &lt;g3&gt;</b></p>
                <p>Nhóm câu hỏi hoán vị cả các câu hỏi và các đáp án</p>
            </div>
        </div>
    </div>
    <div class="pt-4 mt-4">
        <div class="container px-lg-3">
            <!-- Page Features-->
            <div class="row gx-lg-5 pt-lg-0 ">

                <div class="col-lg-4 mb-5">
                    <div class="card bg-light border-0 h-100">
                        <div class="card-body text-center p-4 p-lg-5 pt-0 ">
                            <div
                                    class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4">
                                <i class="bi bi-cloud-download"></i>
                            </div>
                            <h2 class="fs-4 fw-bold">Tải xuống nhanh chóng</h2>
                            <p class="mb-0">Tải xuống nhanh, không cần nhiều thao tác.</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 mb-5">
                    <div class="card bg-light border-0 h-100">
                        <div class="card-body text-center p-4 p-lg-5 pt-0 ">
                            <div
                                    class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4">
                                <i class="fa-solid fa-bolt"></i>
                            </div>
                            <h2 class="fs-4 fw-bold">Xử lý Online</h2>
                            <p class="mb-0">Xử lý dữ liệu đám mây, nhanh chóng, đơn giản.</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 mb-5">
                    <div class="card bg-light border-0 h-100">
                        <div class="card-body text-center p-4 p-lg-5 pt-0 ">
                            <div
                                    class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4">
                                <i class="fa-solid fa-bolt"></i>
                            </div>
                            <h2 class="fs-4 fw-bold">An toàn</h2>
                            <p class="mb-0">Sử dụng an toàn, uy tín, đảm bảo chất lượng.</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</main>


<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
     data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="height: 150px">
            <div class="modal-body d-flex flex-column justify-content-center align-items-center">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <p>Vui lòng chờ...</p>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function handleFileSelect(e) {
        let form = document.querySelector('#form-upload-de-goc');

        let modal = new bootstrap.Modal(document.getElementById('staticBackdrop'));
        modal.show();
        form.submit();
    }

    let inputChonFile = document
        .querySelector('#form-upload-de-goc input[name="de-goc"]');

    window.addEventListener('load', function () {
        inputChonFile.value = ''
    })

    inputChonFile.addEventListener('change', handleFileSelect)
</script>
</body>
</html>
