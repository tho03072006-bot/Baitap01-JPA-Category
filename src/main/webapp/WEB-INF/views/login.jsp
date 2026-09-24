<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Bài tập 02/03 (JPA)</title>
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
        .success {
            margin-bottom: 16px;
            padding: 10px;
            background: #e7f6ec;
            border: 1px solid #b7e4c7;
            color: #1e7e34;
            border-radius: 6px;
            font-size: 13px;
            text-align: center;
        }
        .links {
            margin-top: 16px;
            display: flex;
            justify-content: space-between;
            font-size: 13px;
        }
        .links a { color: #0d6efd; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Đăng nhập</h1>
        <p class="subtitle">Bài tập 02/03 &ndash; JPA/Hibernate + OTP email</p>

        <c:if test="${not empty loginError}">
            <div class="error">
                <c:out value="${loginError}"/>
                <c:if test="${not empty unverifiedUsername}">
                    <br>
                    <a href="${pageContext.request.contextPath}/verify-otp?username=${unverifiedUsername}">Xác thực OTP ngay</a>
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.verified == '1'}">
            <div class="success">Kích hoạt tài khoản thành công! Bạn có thể đăng nhập ngay.</div>
        </c:if>
        <c:if test="${param.resetSuccess == '1'}">
            <div class="success">Đổi mật khẩu thành công! Vui lòng đăng nhập bằng mật khẩu mới.</div>
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

        <div class="links">
            <a href="${pageContext.request.contextPath}/register">Đăng ký tài khoản</a>
            <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
        </div>

        <div class="demo">Tài khoản demo quản trị: <b>admin / 123456</b></div>
    </div>
</body>
</html>
