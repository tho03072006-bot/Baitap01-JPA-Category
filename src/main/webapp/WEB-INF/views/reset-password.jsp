<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu - Bài tập 03</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0; min-height: 100vh; display: flex; align-items: center; justify-content: center;
            background: #f0f2f5; font-family: "Segoe UI", Arial, sans-serif;
        }
        .card { background: #fff; padding: 40px 36px; border-radius: 10px; box-shadow: 0 4px 16px rgba(0,0,0,0.08); width: 360px; }
        h1 { margin: 0 0 4px; color: #1a1a2e; font-size: 24px; text-align: center; }
        .subtitle { text-align: center; font-size: 13px; color: #888; margin: 0 0 24px; }
        .field { margin-bottom: 16px; }
        label { display: block; margin-bottom: 6px; font-size: 14px; color: #333; font-weight: 600; }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 10px 12px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px;
        }
        input#otp { font-size: 18px; letter-spacing: 4px; text-align: center; font-weight: 700; }
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
        .demo-note {
            margin-top: 18px; padding: 10px; background: #fff8e6; border: 1px solid #ffe8a3;
            border-radius: 6px; font-size: 12px; color: #7a5c00; text-align: center;
        }
        .links { margin-top: 16px; text-align: center; font-size: 13px; }
        .links a { color: #0d6efd; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Đặt lại mật khẩu</h1>
        <p class="subtitle">Nhập mã OTP đã nhận qua email kèm mật khẩu mới</p>

        <c:if test="${not empty resetError}">
            <div class="error"><c:out value="${resetError}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <div class="field">
                <label for="usernameOrEmail">Tên đăng nhập hoặc Email</label>
                <input type="text" id="usernameOrEmail" name="usernameOrEmail" required value="${usernameOrEmail}">
            </div>
            <div class="field">
                <label for="otp">Mã OTP (6 số)</label>
                <input type="text" id="otp" name="otp" required maxlength="6" pattern="\d{6}" inputmode="numeric">
            </div>
            <div class="field">
                <label for="newPassword">Mật khẩu mới</label>
                <input type="password" id="newPassword" name="newPassword" required>
            </div>
            <div class="field">
                <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <button type="submit">Đặt lại mật khẩu</button>
        </form>

        <div class="demo-note">
            Nếu chưa cấu hình email thật (mail-secrets.properties), mã OTP sẽ được in ra
            console log của Tomcat thay vì gửi email - kiểm tra console để lấy mã.
        </div>

        <div class="links"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></div>
    </div>
</body>
</html>
