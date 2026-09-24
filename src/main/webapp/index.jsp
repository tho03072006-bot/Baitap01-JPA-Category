<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // (Bai tap 03) TRUOC DAY: trang chu chi don gian sendRedirect() sang
    // Dashboard quan tri "/admin/home" (vi luc do Baitap02 CHI co CRUD
    // Category cho admin, chua co mang khach hang nao).
    //
    // GIO DAY: trang chu CONG KHAI phai hien "10 san pham moi nhat" cho
    // KHACH HANG xem (yeu cau 4.2), nen forward (server-side, giu nguyen
    // URL "/" tren thanh dia chi) toi HomeServlet ("/home") thay vi redirect
    // toi khu quan tri. Admin muon vao Dashboard van bam "/admin/home" hoac
    // dang nhap roi se duoc LoginServlet tu chuyen huong toi do nhu cu.
    request.getRequestDispatcher("/home").forward(request, response);
%>
