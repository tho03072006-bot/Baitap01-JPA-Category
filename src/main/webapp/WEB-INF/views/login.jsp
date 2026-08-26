<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Bài tập 01 (JPA)</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #f0f2f5;
            font-family: "Segoe UI", Arial, sans-serif;
        }
        .card {
            background: #fff;
            padding: 40px 36px;
            border-radius: 10px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.08);
            width: 360px;
        }
        h1 {
            margin: 0 0 4px;
            color: #1a1a2e;
            font-size: 24px;
            text-align: center;
        }
        .subtitle {
            text-align: center;
            font-size: 13px;
            color: #888;
            margin: 0 0 24px;
        }
        .field { margin-bottom: 16px; }
        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #333;
            font-weight: 600;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            outline: none;
            border-color: #0d6efd;
        }
        .remember {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 16px;
            font-size: 13px;
            color: #444;
        }
        .remember input { width: auto; }
        button {
            width: 100%;
            padding: 11px;
            background: #0d6efd;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 4px;
        }
        button:hover { background: #0b5ed7; }
        .demo {
            margin-top: 18px;
            padding: 10px;
            background: #f5f7fa;
            border-radius: 6px;
            font-size: 13px;
            color: #555;
            text-align: center;
        }
        .error {
            margin-bottom: 16px;
            padding: 10px;
            background: #fef2f2;
            border: 1px solid #fecaca;
            color: #b91c1c;
            border-radius: 6px;
            font-size: 13px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="card">
        <h1>Đăng nhập</h1>
        <p class="subtitle">Bài tập 01 &ndash; CRUD Category (JPA/Hibernate)</p>

        <c:if test="${not empty loginError}">
            <div class="error"><c:out value="${loginError}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="field">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="field">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" required>
            </div>
            <label class="remember">
                <input type="checkbox" name="remember">
                Ghi nhớ đăng nhập (Cookie 30 ngày)
            </label>
            <button type="submit">Đăng nhập</button>
        </form>

        <div class="demo">Tài khoản demo: <b>admin / 123456</b></div>
    </div>
</body>
</html>
