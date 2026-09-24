<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${param.pageTitle}"/> - Cửa hàng (Bài tập 03)</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0; min-height: 100vh; background: #f5f6fa;
            font-family: "Segoe UI", Arial, sans-serif; color: #222;
        }
        .topnav {
            background: #1a1a2e; color: #fff; padding: 0 28px;
            display: flex; align-items: center; justify-content: space-between; height: 64px;
        }
        .topnav .brand { font-size: 18px; font-weight: 700; color: #fff; text-decoration: none; }
        .topnav .brand span { color: #0d6efd; }
        .topnav nav { display: flex; gap: 22px; }
        .topnav nav a { color: #cbd5e1; text-decoration: none; font-size: 14px; font-weight: 600; }
        .topnav nav a:hover, .topnav nav a.active { color: #fff; }
        .topnav .right { display: flex; align-items: center; gap: 14px; font-size: 13px; }
        .topnav .right a { color: #cbd5e1; text-decoration: none; font-weight: 600; }
        .topnav .right a:hover { color: #fff; }
        .topnav .right a.btn-outline { border: 1px solid #0d6efd; color: #0d6efd; padding: 7px 14px; border-radius: 6px; }
        .topnav .right a.btn-outline:hover { background: #0d6efd; color: #fff; }
        .topnav .right a.logout-link { color: #f77; }
        .wrap { max-width: 1100px; margin: 0 auto; padding: 28px 20px 60px; }
    </style>
</head>
<body>
    <div class="topnav">
        <a class="brand" href="${pageContext.request.contextPath}/">Baitap<span>02/03</span> Shop</a>
        <nav>
            <a class="${param.activeMenu == 'home' ? 'active' : ''}" href="${pageContext.request.contextPath}/">Trang chủ</a>
            <a class="${param.activeMenu == 'products' ? 'active' : ''}" href="${pageContext.request.contextPath}/product">Sản phẩm</a>
        </nav>
        <div class="right">
            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <span>Xin chào, <b><c:out value="${sessionScope.username}"/></b></span>
                    <c:if test="${sessionScope.roleid == 1}">
                        <a href="${pageContext.request.contextPath}/admin/home">Trang quản trị</a>
                    </c:if>
                    <a class="logout-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    <a class="btn-outline" href="${pageContext.request.contextPath}/register">Đăng ký</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="wrap">
