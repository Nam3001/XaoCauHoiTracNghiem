<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<c:set var="count" value="1" scope="page"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tùy chỉnh trộn đề</title>

</head>
<body>
<main class="container-fluid d-lg-flex flex-row m-0 p-0 ">
    <div id="sidebar" class="bg-dark text-white col-12 py-3">

        <div class="sidebar-body px-3">
            <form id="form-upload-de-goc" action="upload-de-goc" method="post" enctype="multipart/form-data">
                <div class="d-flex justify-content-center mb-3">
                    <label for="de-goc" class="btn btn-light w-50">
                        <i class="fa-solid fa-upload"></i>
                        <span>Đề cần trộn</span>
                    </label>
                    <input id="de-goc" name="de-goc" type="file" style="display: none;" accept=".docx">
                </div>
            </form>
            <form action="tron-de" method="post" accept-charset="utf-8">
                <div class="section mt-3">
                    <h2>Thông tin file gốc</h2>
                    <ul>
                        <c:choose>
                            <c:when test="${deGocFileName != ''}">
                                <li>Tên File: <span>${ deGocFileName }</span></li>
                            </c:when>
                            <c:otherwise>
                                <li>Tên File: <span>Bạn chưa tải đề gốc!</span></li>
                            </c:otherwise>
                        </c:choose>
                        <li>Số lượng câu hỏi: <span>${ questionAmount }</span></li>
                        <li>Số nhóm: <span>${ groupAmount }</span></li>
                    </ul>

                </div>
                <div class="section mt-3">
                    <h2>Tùy chọn trộn đề</h2>
                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label for="so-luong-de" class="col-form-label">Số lượng
                                đề:</label>
                        </div>
                        <div class="col-3">
                            <select class="form-select" id="so-luong-de"
                                    name="so-luong-de">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option selected value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                            </select>
                        </div>
                    </div>
                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label for="tien-to-cau-hoi" class="col-form-label">Tiền
                                tố câu hỏi:</label>
                        </div>
                        <div class="col-4">
                            <select class="form-select" id="tien-to-cau-hoi"
                                    name="tien-to-cau-hoi">
                                <option value=":">Câu 1:</option>
                                <option value=".">Câu 1.</option>
                            </select>
                        </div>
                    </div>

                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto form-check-label">
                            <label for="de-tieng-anh" class="col-form-label">Đề tiếng
                                anh?</label>
                        </div>
                        <div
                                class="col-3 form-check form-switch d-flex justify-content-end">
                            <input type="checkbox" name="de-tieng-anh" id="de-tieng-anh"
                                   class="form-check-input" style="height: 22px; width: 44px;">
                        </div>
                    </div>


                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto form-check-label">
                            <label for="co-dinh-nhom" class="col-form-label">Cố định
                                nhóm?</label>
                        </div>
                        <div
                                class="col-3 form-check form-switch d-flex justify-content-end">
                            <input type="checkbox" name="co-dinh-nhom" id="co-dinh-nhom"
                                   class="form-check-input" checked
                                   style="height: 22px; width: 44px;">
                        </div>
                    </div>
                </div>
                <div class="section mt-3">
                    <h2>Thông tin chung</h2>
                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label class="form-control-label" for="so-gd">Sở/Phòng GD:</label>
                        </div>
                        <div class="col-8">
                            <input class="form-control" type="text" name="so-gd" id="so-gd"
                                   value="Sở GD THỪA THIÊN HUẾ">
                        </div>
                    </div>

                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label class="form-control-label" for="ten-truong">Trường:</label>
                        </div>
                        <div class="col-8">
                            <input class="form-control" type="text" name="ten-truong"
                                   id="ten-truong" value="TRƯỜNG THPT ...">
                        </div>
                    </div>

                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label class="form-control-label" for="nam-hoc">Năm học:</label>
                        </div>
                        <div class="col-8">
                            <input class="form-control" type="text" name="nam-hoc"
                                   id="nam-hoc" value="2024-2025">
                        </div>
                    </div>

                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label class="form-control-label" for="ky-kiem-tra">Kỳ
                                kiểm tra:</label>
                        </div>
                        <div class="col-8">
                            <input class="form-control" type="text" name="ky-kiem-tra"
                                   id="ky-kiem-tra" value="HỌC KỲ I">
                        </div>
                    </div>

                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label class="form-control-label" for="mon-thi">Môn:</label>
                        </div>
                        <div class="col-8">
                            <input class="form-control" type="text" name="mon-thi"
                                   id="mon-thi" value="TOÁN">
                        </div>
                    </div>
                    <div class="py-1 row g-3 align-items-center justify-content-between">
                        <div class="col-auto">
                            <label class="form-control-label" for="thoi-gian">Thời
                                gian:</label>
                        </div>
                        <div class="col-8">
                            <input class="form-control" type="text" name="thoi-gian"
                                   id="thoi-gian" value="90 phút">
                        </div>
                    </div>

                </div>
            </form>

        </div>
        <div class="sidebar-footer">
            <div class="d-flex justify-content-center mt-2">
                <button id="tron-de" class="btn btn-light w-50 ">
                    <i class="fa-solid fa-download"></i> <span>Trộn đề</span>
                </button>
            </div>
        </div>
    </div>

    <div id="content" class="col-12">
        <div class="bg-light min-vh-100 px-4 pt-3 pb-4">
            <c:forEach items="${ exam.groupList }" var="questionGroup">
                <hr class="bg-dark"/>
                <p class="h5 text-danger">Nhóm ${ count }</p>
                <c:set var="count" value="${ count + 1 }" scope="page"/>
                <c:forEach items="${questionGroup.groupInfoList }" var="groupInfo">
<%--                    <span class="fw-bold">${EquationToMathML.getTextAndFormulas(groupInfo) }</span>--%>
                    <span class="fw-bold">${ groupInfo.getText() }</span>
                </c:forEach>
                <c:forEach items="${ questionGroup.questionList }" var="question">
                    <p class="mb-2 pt-2">
                        <c:forEach items="${ question.questionContentList }" var="content">
<%--                            <span>${ EquationToMathML.getTextAndFormulas(content) }</span>--%>
                            <span>${ content.getText() }</span>
                        </c:forEach>
                    </p>
                    <c:forEach items="${ question.answerList }" var="answer">
                        <p class="small mb-2">
                            <c:choose>
                                <c:when test="${answer.isRightAnswer == true}">
<%--									<span class="text-primary">${ EquationToMathML.getTextAndFormulas(answer.content) }--%>
<%--										<i class="bi bi-check2"></i>--%>
<%--									</span>--%>
                                    <span class="text-primary">${ answer.getContent().getText() }
										<i class="bi bi-check2"></i>
									</span>
                                </c:when>
                                <c:otherwise>
<%--                                    <span>${ EquationToMathML.getTextAndFormulas(answer.content) }</span>--%>
                                    <span>${ answer.getContent().getText() }</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </c:forEach>
                </c:forEach>
            </c:forEach>
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

    let modal = new bootstrap.Modal(document.getElementById('staticBackdrop'));

    window.onload = function () {
        console.log('load')
        modal.hide();
    }
    console.log('da tron xong' + '${daTronXong}')
    var form = document.querySelector('form[action="tron-de"]');
    var tronDeBtn = document.getElementById('tron-de');
    tronDeBtn.addEventListener('click', function (e) {
        e.preventDefault();
        if ('${deGocFileName}' === '') {
            alert('Bạn chưa tải đề gốc lên, hãy tải đề gốc!')
        } else {
            let intervalID = setInterval(function() {
                console.log('interval')
                var cookie = document.cookie.split('; ').find(row => row.startsWith('fileDownload=true'));
                if (cookie) {
                    clearInterval(intervalID);
                    document.cookie = 'fileDownload=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
                    window.location.href = '${contextPath}' + '/thong-bao';
                }
            }, 500);

            modal.show();
            form.submit();
        }
    })

    function handleFileSelect(e) {
        let form = document.querySelector('#form-upload-de-goc');

        console.log("change")
        let modal = new bootstrap.Modal(document.getElementById('staticBackdrop'));
        modal.show();
        form.submit();
    }

    let inputChonFile = document.querySelector('#form-upload-de-goc input[name="de-goc"]');
    inputChonFile.addEventListener('change', handleFileSelect)
</script>
</body>
</html>