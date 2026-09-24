package bt02.vn.controller;

import java.io.IOException;

import bt02.vn.service.AuthServiceImpl;
import bt02.vn.service.IAuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ResetPasswordServlet: Buoc 2 cua "Quen mat khau" - nguoi dung nhap ma
 * OTP vua nhan qua email (gui boi ForgotPasswordServlet) kem mat khau
 * moi, xac nhan dung + con hieu luc thi doi mat khau (xem
 * AuthServiceImpl.resetPassword()).
 */
@WebServlet(urlPatterns = { "/reset-password" })
public class ResetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IAuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("usernameOrEmail", req.getParameter("u"));
        req.getRequestDispatcher("/WEB-INF/views/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String usernameOrEmail = req.getParameter("usernameOrEmail");
        String otp = req.getParameter("otp");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        req.setAttribute("usernameOrEmail", usernameOrEmail);

        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            req.setAttribute("resetError", "Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("/WEB-INF/views/reset-password.jsp").forward(req, resp);
            return;
        }

        boolean ok = authService.resetPassword(usernameOrEmail, otp, newPassword);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/login?resetSuccess=1");
        } else {
            req.setAttribute("resetError", "Mã OTP không đúng hoặc đã hết hạn. Vui lòng yêu cầu gửi lại OTP.");
            req.getRequestDispatcher("/WEB-INF/views/reset-password.jsp").forward(req, resp);
        }
    }
}
