package bt02.vn.controller;

import java.io.IOException;

import bt02.vn.service.AuthServiceImpl;
import bt02.vn.service.IAuthService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * VerifyOtpServlet: xac nhan ma OTP da gui qua email luc dang ky de kich
 * hoat tai khoan (enabled 0 -> 1). Cung xu ly luon nut "Gui lai OTP" khi
 * nguoi dung khong nhan duoc email hoac ma da het han (Constants.OTP_EXPIRY_MINUTES).
 */
@WebServlet(urlPatterns = { "/verify-otp" })
public class VerifyOtpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IAuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("justRegistered", req.getParameter("justRegistered"));
        req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String action = req.getParameter("action"); // "verify" hoac "resend"
        req.setAttribute("username", username);

        if ("resend".equals(action)) {
            try {
                authService.resendOtp(username);
                req.setAttribute("otpMessage", "Đã gửi lại mã OTP mới, vui lòng kiểm tra email.");
            } catch (Exception e) {
                req.setAttribute("otpError", e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        String otp = req.getParameter("otp");
        boolean ok = authService.verifyRegisterOtp(username, otp);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/login?verified=1");
        } else {
            req.setAttribute("otpError", "Mã OTP không đúng hoặc đã hết hạn. Vui lòng bấm \"Gửi lại OTP\" để lấy mã mới.");
            req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
        }
    }
}
