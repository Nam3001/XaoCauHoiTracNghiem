<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 7/21/2024
  Time: 9:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<html>
<head>
    <title>Hướng dẫn chuyển MathType sang Equation</title>
</head>
<body>
<main style="min-height: calc(100vh - 56px - 80px)">
    <div class="container my-5">
        <div class="bg-light rounded-3 py-3">
            <h3 class="text-center">Hướng dẫn chuyển MathType sang Equation</h3>
            <div class="p-3 p-lg-5">
                <h4>#1. Các bước thực hiện chuyển công thức MathType sang Equation</h4>
                <p class="mt-5"><b>+ Bước 1:</b> Tắt hoàn toàn MathType nếu bạn đang mở và đóng luôn MathType chạy ngầm trong Taskbar nhé các bạn.</p>
                <p>Trên thanh Taskbar, bạn hãy mở rộng các icon đang ẩn trên thanh Taskbar ra => nháy chuột phải vào icon biểu tượng MathType => và chọn Exit</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/tat-mathtype.png' />" /></div>
                <p class="mt-5"><b>+ Bước 2:</b> Tùy chỉnh hiển thị phần mở rộng của tệp tin.</p>
                <p>Thực hiện: - Bạn mở File Explorer ra (nhấn tổ hợp Windows + E) => chọn View => đánh dấu chọn vào dòng File name extensions..</p>
                <p> - Hoặc trên Windows 11 bạn chọn View => Show(ở dòng cuối cùng) => đánh dấu chọn vào dòng File name extensions</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/show-extension.png' />" /></div>
                <p class="mt-5"><b>+ Bước 3:</b> Tìm đến file thực thi MathType.exe</p>
                <p>Tìm theo đường dẫn sau: This PC hoặc Computer (Windows + E) => System (C:) => Program Files (x86) => MathType =>..</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/tim-mathtype.png' />" /></div>
                <p class="fw-bold">Trong đó thì:</p>
                <ul>
                    <li>System ở đây là tên ô C – ổ chứa hệ điều hành của mình.</li>
                    <li>Nếu không tìm thấy thư mục Program Files (x86) thì bạn hãy chọn thư mục Program File</li>
                </ul>
                <p class="mt-5"><b>+ Bước 4:</b> Nháy chuột phải vào file MathType.exe => chọn Rename => thay đuôi exe thành txt như hình bên dưới.</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/themtxt.png' />" /></div>
                <p class="mt-5"><b>+ Bước 5:</b> Nhấn phím Enter trên bàn phím => hộp thoại Rename xuất hiện với giao diện như hình bên dưới

                    Nội dung chính của hộp thoại là: Nếu bạn thay đổi phần mở rộng tên tệp, tệp có thể không sử dụng được. Bạn có chắc chắn muốn thay đổi nó không?

                    => Nhấn chọn Yes để xác nhận việc thay đổi.</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/confirm-change-name.png' />" /></div>
                <p class="mt-5">
                    Khởi động tệp tin *.docx có công thức mà bạn muốn chuyển đổi => nháy đúp chuột vào một công thức bất kì => hộp thoại Convert Equation to Office Math xuất hiện, khi đó bạn hãy đánh dấu chọn vào dòng Apply to all equations => chọn Yes là xong.
                </p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/convert-mathtype.png' />" /></div>
                <p class="mt-5">Quá trình chuyển đổi sẽ tự động diễn ra, nhanh hay chậm thì sẽ phụ thuộc vào số lượng công thức và cấu hình máy tính của bạn. Đây kết quả sau khi chuyển vẫn đẹp và vẫn đúng theo các quy ước Toán học quốc tế.</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/result-convert.png' />" /></div>
                <h4 class="mt-5">#2. Khôi phục lại MathType về mặc định</h4>
                <p>
                    MathType hiện tại đã bị vô hiệu hóa (vì chúng ta đã đổi file *.exe thành *.txt), nên chúng ta không thể sử dụng nó để soạn thảo công thức được. Muốn sử dụng được bạn bạn khôi phục lại trước đã.

                    Cách thực hiện rất đơn giản bạn thôi, chỉ cần tìm đến tệp tin MathType.exe.txt => nháy chuột phải chọn Rename => và đổi lại thành định dạng *.exe là xong.
                </p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/doi-mathtype/restore-mathtype.png' />" /></div>
            </div>

        </div>
    </div>
</main>
</body>
</html>
