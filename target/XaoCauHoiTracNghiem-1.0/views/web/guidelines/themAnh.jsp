<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 7/21/2024
  Time: 9:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<html>
<head>
    <title>Hướng dẫn thêm ảnh</title>
</head>
<body>
<main style="min-height: calc(100vh - 56px - 80px)">
    <div class="container my-5">
        <div class="bg-light rounded-3 py-3">
            <h3 class="text-center">Hướng dẫn thêm ảnh</h3>
            <div class="p-3 p-lg-5">
                <p>• Hiện tại chúng tôi chỉ hỗ trợ thêm hình ảnh và Equation vào trong đề. Nên nếu bạn muốn thêm table, chart, picture, shape, drawing... thì hãy chụp màn hình ảnh lại và thêm vào đề mẫu</p>
                <p class="mt-5">• B1: Đầu tiên bạn hãy thêm table, chart, shape, drawing... vào đề mẫu như bình thường</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/them-picture/them-table-binh-thuong.png' />"></div>
                <p class="mt-5">• B2: Sau đó dùng snipping tool(được cài sẵn trên window) để cắt ảnh màn hình</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/them-picture/snipping-tool.png' />"></div>
                <br />
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/them-picture/chup-anh-man-hinh.png' />"></div>
                <p class="mt-5">• B3: Sau đó thêm ảnh vào đề gốc</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/them-picture/paste-hinh.png' />"></div>
                <p class="mt-5">• B4: Cuối cùng xóa table, chart, shape, drawing... trước đó là xong!</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/them-picture/xoa-table.png' />"></div>

                <p class="mt-5">• Đôi lúc word kiểm tra chính tả sẽ xuất hiện dấu gạch chân màu đỏ như hình dưới đây, nếu chụp hình table lại sẽ xuất hiện gạch chân màu đỏ gây mất thẩm mỹ. Hãy tắt kiểm tra chính tả trong word để hiển thị table đẹp hơn rồi chụp màn hình lại.</p>
                <div class="d-flex justify-content-center"><img class="huong-dan w-75" src="<c:url value='/assets/images/them-picture/gach-chan-do.png' />"></div>
                <a class="d-block" href="https://www.thegioididong.com/hoi-dap/cach-tat-dau-gach-chan-do-kiem-tra-chinh-ta-trong-word-1391850#:~:text=C%C3%A1ch%20th%E1%BB%B1c%20hi%E1%BB%87n%20c%C5%A9ng%20t%C6%B0%C6%A1ng,%3A%20Ch%E1%BB%8Dn%20File%20%3E%20Ch%E1%BB%8Dn%20Options.&text=B%C6%B0%E1%BB%9Bc%202%3A%20Ch%E1%BB%8Dn%20Proofing%20%3E%20T%C3%ADch,spelling%20and%20grammar%20in%20Word.&text=B%C6%B0%E1%BB%9Bc%203%3A%20Nh%E1%BA%A5n%20OK%20%C4%91%E1%BB%83%20ho%C3%A0n%20t%E1%BA%A5t.">• Cách tắt dấu gạch chân đỏ kiểm tra chính tả trong word</a>
                <a class="d-block" href="https://dienthoaivui.com.vn/cach-bo-gach-do-trong-word">• Cách tắt dấu gạch chân đỏ kiểm tra chính tả trong word</a>
            </div>

        </div>
    </div>
</main>
</body>
</html>
