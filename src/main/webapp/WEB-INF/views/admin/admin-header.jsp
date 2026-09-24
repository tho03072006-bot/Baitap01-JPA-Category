<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${param.pageTitle}"/> - Trang quản trị (JPA)</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            min-height: 100vh;
            background: #f0f2f5;
            font-family: "Segoe UI", Arial, sans-serif;
        }
        .sidebar {
            position: fixed;
            top: 0; left: 0; bottom: 0;
            width: 220px;
            background: #1a1a2e;
            color: #fff;
            display: flex;
            flex-direction: column;
            padding: 24px 0;
            overflow-y: auto;
        }
        .sidebar .avatar {
            width: 56px; height: 56px;
            border-radius: 50%;
            background: #0d6efd;
            color: #fff;
            font-size: 22px;
            display: flex; align-items: center; justify-content: center;
            margin: 0 auto 10px;
        }
        .sidebar .who { text-align: center; font-size: 13px; color: #cbd5e1; margin-bottom: 24px; padding: 0 12px; word-break: break-word; }
        .sidebar .who b { color: #fff; display: block; margin-bottom: 2px; }
        .sidebar nav { display: flex; flex-direction: column; }
        .sidebar a.menu-item {
            display: flex; align-items: center; gap: 10px;
            padding: 12px 24px;
            color: #cbd5e1;
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
        }
        .sidebar a.menu-item:hover { background: rgba(255,255,255,0.08); color: #fff; }
        .sidebar a.menu-item.active { background: #0d6efd; color: #fff; }
        .sidebar .menu-divider { border-top: 1px solid rgba(255,255,255,0.12); margin: 10px 20px; }
        .main-area { margin-left: 220px; min-height: 100vh; }
        .topbar {
            background: #fff;
            padding: 14px 28px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
        }
        .topbar .hello { font-size: 14px; color: #333; }
        .topbar .hello b { color: #1a1a2e; }
        .topbar .links { display: flex; align-items: center; gap: 14px; }
        .topbar a.view-site { color: #0d6efd; text-decoration: none; font-size: 13px; font-weight: 600; }
        .topbar a.view-site:hover { text-decoration: underline; }
        .topbar a.logout {
            background: #dc3545;
            color: #fff;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
            padding: 8px 16px;
            border-radius: 6px;
        }
        .topbar a.logout:hover { background: #bb2d3b; }
        .content { padding: 28px; }
    </style>
</head>
<body>
    <div class="sidebar">
        <div class="avatar">&#128100;</div>
        <div class="who"><b>Bạn là Admin</b><c:out value="${sessionScope.username}"/></div>
        <nav>
            <a class="menu-item ${param.activeMenu == 'dashboard' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/home">&#127968;&nbsp; Dashboard</a>
            <a class="menu-item ${param.activeMenu == 'category' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/categories">&#128193;&nbsp; Quản lý Danh mục</a>
            <a class="menu-item ${param.activeMenu == 'product' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/products">&#128230;&nbsp; Quản lý Sản phẩm</a>
            <div class="menu-divider"></div>
            <a class="menu-item" href="${pageContext.request.contextPath}/">&#127760;&nbsp; Xem trang khách</a>
        </nav>
    </div>
    <div class="main-area">
        <div class="topbar">
            <span class="hello">Xin chào, <b><c:out value="${sessionScope.username}"/></b></span>
            <div class="links">
                <a class="view-site" href="${pageContext.request.contextPath}/" target="_blank">Xem trang khách &#8599;</a>
                <a class="logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </div>
        <div class="content">
