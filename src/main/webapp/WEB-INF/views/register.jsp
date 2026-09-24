<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản - Bài tập 03 (OTP email)</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0; min-height: 100vh; display: flex; align-items: center; justify-content: center;
            background: #f0f2f5; font-family: "Segoe UI", Arial, sans-serif; padding: 24px 0;
        }
        .card { background: #fff; padding: 40px 36px; border-radius: 10px; box-shadow: 0 4px 16px rgba(0,0,0,0.08); width: 380px; }
        h1 { margin: 0 0 4px; color: #1a1a2e; font-size: 24px; text-align: center; }
        .subtitle { text-align: center; font-size: 13px; color: #888; margin: 0 0 24px; }
        .field { margin-bottom: 16px; }
        label { display: block; margin-bottom: 6px; font-size: 14px; color: #333; font-weight: 600; }
        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%; padding: 10px 12px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px;
        }
        input:focus { outline: none; border-color: #0d6efd; }
        button {
            width: 100%; padding: 11px; background: #0d6efd; color: #fff; border: none; border-radius: 6px;
            font-size: 15px; font-weight: 600; cursor: pointer; margin-top: 4px;
        }
        button:hover { background: #0b5ed7; }
        .error {
            margin-bottom: 16px; padding: 10px; background: #fef2f2; border: 1px solid #fecaca;
            color: #b91c1c; border-radius: 6px; font-size: 13px; text-align: center;
        }
        .links { margin-top: 16px; text-align: center; font-size: 13px; }
        .links a { color: #0d6efd; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
        .hint { font-size: 12px; color: #888; margin-top: 6px; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Đăng ký tài khoản</h1>
        <p class="subtitle">Sau khi đăng ký, mã OTP sẽ được gửi qua email để kích hoạt</p>

        <c:if test="${not empty registerError}">
            <div class="error"><c:out value="${registerError}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="field">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" required value="${oldUsername}">
            </div>
            <div class="field">
                <label for="fullname">Họ và tên</label>
                <input type="text" id="fullname" name="fullname" value="${oldFullname}">
            </div>
            <div class="field">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required value="${oldEmail}">
                <div class="hint">Mã OTP kích hoạt sẽ được gửi tới email này</div>
            </div>
            <div class="field">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="field">
                <label for="confirmPassword">Xác nhận mật khẩu</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <button type="submit">Đăng ký</button>
        </form>

        <div class="links">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></div>
    </div>
</body>
</html>
