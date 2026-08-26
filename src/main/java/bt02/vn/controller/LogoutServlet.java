package bt02.vn.controller;

import bt02.vn.config.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * LogoutServlet: dang xuat - huy Session VA xoa Cookie "remember_username".
 * Phai xoa ca 2, neu chi huy Session ma quen xoa Cookie thi bam "Dang xuat"
 * xong quay lai "/login" se tu dong dang nhap lai ngay.
 */
@WebServlet(urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie(Constants.COOKIE_REMEMBER_USERNAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
