<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác thực OTP - Bài tập 03</title>
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
        input[type="text"] {
            width: 100%; padding: 10px 12px; border: 1px solid #ccc; border-radius: 6px; font-size: 18px;
            letter-spacing: 4px; text-align: center; font-weight: 700;
        }
        input:focus { outline: none; border-color: #0d6efd; }
        button {
            width: 100%; padding: 11px; background: #0d6efd; color: #fff; border: none; border-radius: 6px;
            font-size: 15px; font-weight: 600; cursor: pointer; margin-top: 4px;
        }
        button:hover { background: #0b5ed7; }
        button.secondary { background: #fff; color: #0d6efd; border: 1px solid #0d6efd; margin-top: 10px; }
        button.secondary:hover { background: #f0f6ff; }
        .error {
            margin-bottom: 16px; padding: 10px; background: #fef2f2; border: 1px solid #fecaca;
            color: #b91c1c; border-radius: 6px; font-size: 13px; text-align: center;
        }
        .success {
            margin-bottom: 16px; padding: 10px; background: #e7f6ec; border: 1px solid #b7e4c7;
            color: #1e7e34; border-radius: 6px; font-size: 13px; text-align: center;
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
        <h1>Xác thực OTP</h1>
        <p class="subtitle">Nhập mã 6 chữ số vừa gửi tới email của bạn</p>

        <c:if test="${param.justRegistered == '1'}">
            <div class="success">Đăng ký thành công! Vui lòng kiểm tra email để lấy mã OTP kích hoạt.</div>
        </c:if>
        <c:if test="${not empty otpError}">
            <div class="error"><c:out value="${otpError}"/></div>
        </c:if>
        <c:if test="${not empty otpMessage}">
            <div class="success"><c:out value="${otpMessage}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/verify-otp" method="post">
            <input type="hidden" name="action" value="verify">
            <div class="field">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" required value="${username}"
                       style="letter-spacing:normal; text-align:left; font-weight:normal; font-size:14px;">
            </div>
            <div class="field">
                <label for="otp">Mã OTP (6 số)</label>
                <input type="text" id="otp" name="otp" required maxlength="6" pattern="\d{6}" inputmode="numeric" autofocus>
            </div>
            <button type="submit">Xác nhận</button>
        </form>

        <form action="${pageContext.request.contextPath}/verify-otp" method="post">
            <input type="hidden" name="action" value="resend">
            <input type="hidden" name="username" value="${username}">
            <button type="submit" class="secondary">Gửi lại mã OTP</button>
        </form>

        <div class="demo-note">
            Nếu chưa cấu hình email thật (mail-secrets.properties), mã OTP sẽ được in ra
            console log của Tomcat thay vì gửi email - kiểm tra console để lấy mã.
        </div>

        <div class="links"><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></div>
    </div>
</body>
</html>
