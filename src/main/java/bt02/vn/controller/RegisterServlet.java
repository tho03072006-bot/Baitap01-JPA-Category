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
 * RegisterServlet: dang ky tai khoan MOI (khach hang, ROLE_USER) kem gui
 * ma OTP kich hoat qua email (yeu cau 1 cua Bai tap 03). Tai khoan vua tao
 * o trang thai "chua kich hoat" (enabled = 0) - phai qua VerifyOtpServlet
 * nhap dung OTP thi moi dang nhap duoc, xem AuthServiceImpl.register().
 */
@WebServlet(urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IAuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");

        // Giu lai gia tri da nhap (tru mat khau, vi ly do bao mat) de nguoi
        // dung khong phai go lai tu dau neu co loi.
        req.setAttribute("oldUsername", username);
        req.setAttribute("oldFullname", fullname);
        req.setAttribute("oldEmail", email);

        if (password == null || !password.equals(confirmPassword)) {
            req.setAttribute("registerError", "Mật khẩu xác nhận không khớp!");
            forwardToForm(req, resp);
            return;
        }

        try {
            authService.register(username, password, fullname, email);
        } catch (Exception e) {
            req.setAttribute("registerError", e.getMessage());
            forwardToForm(req, resp);
            return;
        }

        // Dang ky thanh cong -> chuyen sang trang nhap OTP de kich hoat,
        // truyen san username qua query string cho do phai go lai.
        resp.sendRedirect(req.getContextPath() + "/verify-otp?username="
                + java.net.URLEncoder.encode(username, "UTF-8") + "&justRegistered=1");
    }

    private void forwardToForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/register.jsp");
        dispatcher.forward(req, resp);
    }
}
