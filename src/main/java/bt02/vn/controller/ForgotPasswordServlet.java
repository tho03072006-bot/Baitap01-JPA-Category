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
 * ForgotPasswordServlet: Buoc 1 cua "Quen mat khau" (yeu cau 3 cua Bai
 * tap 03) - nguoi dung nhap username HOAC email, he thong sinh ma OTP va
 * gui qua email cua tai khoan do (khong lo lieu tai khoan nao ton tai
 * bang cach hien thi loi ro rang - du an hoc tap nen uu tien de hieu hon
 * la an toan tuyet doi kieu production).
 */
@WebServlet(urlPatterns = { "/forgot-password" })
public class ForgotPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IAuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String usernameOrEmail = req.getParameter("usernameOrEmail");
        req.setAttribute("oldUsernameOrEmail", usernameOrEmail);

        try {
            authService.forgotPassword(usernameOrEmail);
        } catch (Exception e) {
            req.setAttribute("forgotError", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/reset-password?u="
                + java.net.URLEncoder.encode(usernameOrEmail, "UTF-8"));
    }
}
